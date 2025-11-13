package com.web.base.backend;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

public class OracleConnection {
    private static OracleConnection instance;
    private static final Log log = LogFactory.getLog(OracleConnection.class);

    public static synchronized OracleConnection getInstance(){
        if (instance == null)
            instance = new OracleConnection();
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        String jdbcDriverName = System.getenv("JDBC-DRIVER");

        try {
            Class.forName(jdbcDriverName);
            conn = DriverManager.getConnection(System.getenv("DB-URL"), System.getenv("DB-USER-NAME"), System.getenv("DB-PASSWORD"));
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
/*
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
    }*/
/*
    private Object[] fetchResultAllColumns(ResultSet resultSet) throws SQLException {

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();

        Object[] row = new Object[columnCount];
        for (int i = 1; i <= columnCount; ++i) {
            row[i - 1] = resultSet.getObject(i);
            if(row[i - 1] instanceof CLOB)
                row[i - 1] = clobToString((CLOB)row[i - 1]);
        }
        return row;
    }*/

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
/*
    private Object clobToString(CLOB clob){
        try {
            BufferedReader bufferRead = new BufferedReader(clob.getCharacterStream());
            StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = bufferRead.readLine())!=null)
                buffer.append(str);
            return buffer.toString();
        } catch (SQLException|IOException e) {
            log.info(e.getMessage());
            throw new WebAutomationException(e.getMessage());
        }
    }*/
}
