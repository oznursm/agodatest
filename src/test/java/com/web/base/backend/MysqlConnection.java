package com.web.base.backend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

public class MysqlConnection {
    private static MysqlConnection instance;
    private static final Log log = LogFactory.getLog(MysqlConnection.class);

    public static synchronized MysqlConnection getInstance(){
        if (instance == null)
            instance = new MysqlConnection();
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        String jdbcDriverName = System.getenv("AUTOMATION-JDBC-DRIVER");

        try {
            Class.forName(jdbcDriverName);
            conn = DriverManager.getConnection(System.getenv("AUTOMATION-DB-URL"), System.getenv("AUTOMATION-DB-USER-NAME"), System.getenv("AUTOMATION-DB-PASSWORD"));
            return conn;
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Failed to connect to db " + e.getMessage());
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException var2) {
            //log.error("Failed to close database connection with error " + var2.getMessage());
        } catch (Exception exc) {
            //log.error("Failed to close database connection with error " + exc.getMessage());
        }
    }

    public Object[] executeSingleResultQuery(String query) {
        Connection conn = null;
        Object[] result = null;

        try {
            conn = getConnection();
            PreparedStatement stat = conn.prepareStatement(query);
            log.debug("About to execute query " + query);
            ResultSet resultSet = stat.executeQuery();

            for (int i = 0; resultSet.next(); result = this.fetchResultAllColumns(resultSet)) {
                if (i >= 1) {
                    throw new RuntimeException("Multiple result returned for query " + query);
                }
                ++i;
            }
        } catch (SQLException var11) {
            throw new RuntimeException("Failed to execute query with error: " + var11.getMessage() + "\n" + query, var11);
        } finally {
            closeConnection(conn);
        }
        return result;
    }

    public Object executeSingleSimpleResultQuery(String query, Class returnType) {
        Connection conn = null;
        Object result = null;

        try {
            conn = this.getConnection();
            PreparedStatement stat = conn.prepareStatement(query);
            //log.debug("About to execute query " + query);
            ResultSet resultSet = stat.executeQuery();

            for(int i = 0; resultSet.next(); result = this.fetchResult(returnType, resultSet)) {
                if (i >= 1) {
                    throw new RuntimeException("Multiple result returned for query " + query);
                }

                ++i;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query with error: " + e.getMessage() + "\n" + query, e);
        } finally {
            closeConnection(conn);
        }

        //log.debug("Return value " + result.toString());
        return result;
    }

    public int executeSQLScript(String query) {
        Connection conn = null;
        PreparedStatement stat;
        int rowCnt = 0;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            stat = conn.prepareStatement(query);
            //conn.setSchema(EnvironmentUtils.getDbSchemaName());
            //stat.execute("alter session set current_schema=" + EnvironmentUtils.getDbSchemaName());
            log.info("About to execute update script:\n" + query);
            rowCnt = stat.executeUpdate();

            if (rowCnt > 3000) {
                log.warn("Since the affected row number is: " + rowCnt + " transaction will be rollbacked.");
                conn.rollback();
            } else {
                log.info("Transaction will be committed. Affected row count is: " + rowCnt);
                conn.commit();
            }
        } catch (SQLException e) {
            log.error("Failed to execute query with error: " + e.getMessage() + "\n" + query, e);
        } finally {
            closeConnection(conn);
        }
        log.info("Return value: " + rowCnt);
        return rowCnt;
    }

    private Object[] fetchResultAllColumns(ResultSet resultSet) throws SQLException {

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        Object[] row = new Object[columnCount];
        for (int i = 1; i <= columnCount; ++i) {
            row[i - 1] = resultSet.getObject(i);
        }
        return row;
    }

    private Object fetchResult(Class returnType, ResultSet resultSet) throws SQLException {
        Object result;
        if (returnType.getName().equalsIgnoreCase(Boolean.class.getSimpleName())) {
            result = resultSet.getBoolean(1);
        } else if (returnType.getName().equalsIgnoreCase(Long.class.getSimpleName())) {
            result = resultSet.getLong(1);
        } else if (returnType.getName().equalsIgnoreCase(Integer.class.getSimpleName())) {
            result = resultSet.getInt(1);
        } else if (returnType.getName().equalsIgnoreCase(String.class.getSimpleName())) {
            result = resultSet.getString(1);
        } else if (returnType.getName().equalsIgnoreCase(BigDecimal.class.getSimpleName())) {
            result = resultSet.getBigDecimal(1);
        } else if (returnType.getName().equalsIgnoreCase(Date.class.getSimpleName())) {
            result = resultSet.getTimestamp(1);
        } else {
            result = resultSet.getObject(1);
        }
        return result;
    }
}
