package com.web.base.utils.reporting;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExcelUtil {

    private static ExcelUtil instance;
    private enum ORDER {USER, PROJECT, ENVIRONMENT, DOMAIN, CASE, RUNS, STATUS, REASON, LINK, LOGINDATA, STARTTIME, DURATION}

    public static synchronized ExcelUtil getInstance(){
        if (instance == null)
            instance = new ExcelUtil();
        return instance;
    }

    //EXCEL RAPORLARININ OLUŞTURULMASI İÇİN  REPORTS KLASORUNE "reports/excel" DİZİNİN AÇILMASI GEREKİYOR.
    private String currentFileName(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return "reports/excel/WebAutomationReport-" + sdf.format(Calendar.getInstance().getTime()) + ".xlsx";
    }

    public void createReportSheet(String domainName, String testCaseName, Boolean isFailed, String failMessage, String ssLink, String loginData, String startTime, String duration){
        try {
            XSSFWorkbook wb;
            if (isTodaysReportExists()) {
                FileInputStream inputStream = new FileInputStream(currentFileName());
                wb = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = wb.getSheet(domainName);
                if(sheet == null){
                    sheet = wb.createSheet(domainName);
                    setHeaderRow(sheet);
                }
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                setDomainNameCell(row, domainName);
                setTestCaseNameCell(row, testCaseName);
                setRunCountCell(row, testCaseName);
                setStatusCell(row, isFailed);
                setMessageCell(row, isFailed, failMessage);
                setSSLinkCell(row, isFailed, ssLink);
                setUserCell(row);
                setProjectCell(row);
                setEnvironmentCell(row);
                setLoginDataCell(row, loginData);
                setStartTimeCell(row, startTime);
                setDurationCell(row, duration);
                FileOutputStream outputStream = new FileOutputStream(currentFileName());
                wb.write(outputStream);
                wb.close();
                outputStream.close();
            } else {
                wb = new XSSFWorkbook();
                FileOutputStream outputStream = new FileOutputStream(currentFileName());
                XSSFSheet sheet = wb.createSheet(domainName);
                setHeaderRow(sheet);
                Row row = sheet.createRow(1);
                setDomainNameCell(row, domainName);
                setTestCaseNameCell(row, testCaseName);
                setRunCountCell(row, testCaseName);
                setStatusCell(row, isFailed);
                setMessageCell(row, isFailed, failMessage);
                setSSLinkCell(row, isFailed, ssLink);
                setUserCell(row);
                setProjectCell(row);
                setEnvironmentCell(row);
                setLoginDataCell(row, loginData);
                setStartTimeCell(row, startTime);
                setDurationCell(row, duration);
                wb.write(outputStream);
                wb.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isTodaysReportExists(){
        File excelReport = new File(currentFileName());
        return excelReport.exists();
    }

    private void setHeaderRow(Sheet sheet){
        Row row = sheet.createRow(0);
        Cell domainHeaderCell = row.createCell(ORDER.DOMAIN.ordinal());
        domainHeaderCell.setCellValue("Domain Name");
        Cell testCaseHeaderCell = row.createCell(ORDER.CASE.ordinal());
        testCaseHeaderCell.setCellValue("Test Case Name");
        Cell runCountHeaderCell = row.createCell(ORDER.RUNS.ordinal());
        runCountHeaderCell.setCellValue("Runs");
        Cell statusHeaderCell = row.createCell(ORDER.STATUS.ordinal());
        statusHeaderCell.setCellValue("Status");
        Cell failReasonHeaderCell = row.createCell(ORDER.REASON.ordinal());
        failReasonHeaderCell.setCellValue("Fail Reason");
        Cell ssLinkHeaderCell = row.createCell(ORDER.LINK.ordinal());
        ssLinkHeaderCell.setCellValue("Screenshot Link");
        Cell userHeaderCell = row.createCell(ORDER.USER.ordinal());
        userHeaderCell.setCellValue("User");
        Cell projectHeaderCell = row.createCell(ORDER.PROJECT.ordinal());
        projectHeaderCell.setCellValue("Project");
        Cell environmentHeaderCell = row.createCell(ORDER.ENVIRONMENT.ordinal());
        environmentHeaderCell.setCellValue("Environment");
        Cell loginDataHeaderCell = row.createCell(ORDER.LOGINDATA.ordinal());
        loginDataHeaderCell.setCellValue("Login Data");
        Cell startTimeHeaderCell = row.createCell(ORDER.STARTTIME.ordinal());
        startTimeHeaderCell.setCellValue("Test Started At");
        Cell durationHeaderCell = row.createCell(ORDER.DURATION.ordinal());
        durationHeaderCell.setCellValue("Duration");
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        domainHeaderCell.setCellStyle(headerCellStyle);
        testCaseHeaderCell.setCellStyle(headerCellStyle);
        runCountHeaderCell.setCellStyle(headerCellStyle);
        statusHeaderCell.setCellStyle(headerCellStyle);
        failReasonHeaderCell.setCellStyle(headerCellStyle);
        ssLinkHeaderCell.setCellStyle(headerCellStyle);
        userHeaderCell.setCellStyle(headerCellStyle);
        projectHeaderCell.setCellStyle(headerCellStyle);
        environmentHeaderCell.setCellStyle(headerCellStyle);
        loginDataHeaderCell.setCellStyle(headerCellStyle);
        startTimeHeaderCell.setCellStyle(headerCellStyle);
        durationHeaderCell.setCellStyle(headerCellStyle);
    }

    private void setDomainNameCell(Row row, String domainName){
        Cell domainNameCell = row.createCell(ORDER.DOMAIN.ordinal());
        domainNameCell.setCellValue(domainName);
        row.getSheet().autoSizeColumn(ORDER.DOMAIN.ordinal());
    }

    private void setTestCaseNameCell(Row row, String testCaseName){
        Cell testCaseNameCell = row.createCell(ORDER.CASE.ordinal());
        testCaseNameCell.setCellValue(testCaseName);
        row.getSheet().autoSizeColumn(ORDER.CASE.ordinal());
    }
    
    private void setRunCountCell(Row row, String testCaseName){
        Cell runCountCell = row.createCell(ORDER.RUNS.ordinal());
        int occurence = 0;
        for (Row findRow: row.getSheet()) {
            Cell findCell = findRow.getCell(ORDER.CASE.ordinal());
            String findCellValue = findCell.getStringCellValue();
            if(testCaseName.equals(findCellValue)){
                occurence++;
            }
        }
        runCountCell.setCellValue(occurence);
        row.getSheet().autoSizeColumn(ORDER.RUNS.ordinal());
    }

    private void setStatusCell(Row row, boolean isFailed){
        Cell statusCell = row.createCell(ORDER.STATUS.ordinal());
        if(!isFailed){
            statusCell.setCellValue("PASSED");
            setPassedStatusStyle(statusCell);
        } else {
            statusCell.setCellValue("FAILED");
            setFailedStatusStyle(statusCell);
        }
        row.getSheet().autoSizeColumn(ORDER.STATUS.ordinal());
    }

    private void setMessageCell(Row row, boolean isFailed, String message){
        Cell messageCell = row.createCell(ORDER.REASON.ordinal());
        if(!isFailed){
            messageCell.setCellValue("-");
        } else {
            messageCell.setCellValue(message);
        }
        Font messageCellFont = row.getSheet().getWorkbook().createFont();
        messageCellFont.setFontHeight((short)160);
        CellStyle messageCellStyle = row.getSheet().getWorkbook().createCellStyle();
        messageCellStyle.setFont(messageCellFont);
        messageCell.setCellStyle(messageCellStyle);
        row.getSheet().autoSizeColumn(ORDER.REASON.ordinal());
    }

    private void setSSLinkCell(Row row, boolean isFailed, String ssLink){
        Cell ssLinkCell = row.createCell(ORDER.LINK.ordinal());
        if(!isFailed){
            ssLinkCell.setCellValue("-");
        } else {
            Hyperlink ssHyperLink = row.getSheet().getWorkbook().getCreationHelper().createHyperlink(Hyperlink.LINK_FILE);
            ssHyperLink.setAddress(new File(ssLink).toURI().toString());
            ssLinkCell.setCellValue("Screenshot");
            ssLinkCell.setHyperlink(ssHyperLink);
            setHyperLinkStyle(ssLinkCell);
        }
        row.getSheet().autoSizeColumn(ORDER.LINK.ordinal());
    }

    private void setUserCell(Row row){
        Cell userCell = row.createCell(ORDER.USER.ordinal());
        userCell.setCellValue(System.getProperty("user.name"));
        row.getSheet().autoSizeColumn(ORDER.USER.ordinal());
    }

    //"BASE" PROJECT NAME
    private void setProjectCell(Row row){
        Cell userCell = row.createCell(ORDER.PROJECT.ordinal());
        userCell.setCellValue("BASE");
        row.getSheet().autoSizeColumn(ORDER.PROJECT.ordinal());
    }

    private void setEnvironmentCell(Row row){
        Cell userCell = row.createCell(ORDER.ENVIRONMENT.ordinal());
        userCell.setCellValue(System.getenv("ENVIRONMENT"));
        row.getSheet().autoSizeColumn(ORDER.ENVIRONMENT.ordinal());
    }

    private void setLoginDataCell(Row row, String loginData){
        Cell userCell = row.createCell(ORDER.LOGINDATA.ordinal());
        userCell.setCellValue(loginData);
        row.getSheet().autoSizeColumn(ORDER.LOGINDATA.ordinal());
    }

    private void setStartTimeCell(Row row, String startTime){
        Cell userCell = row.createCell(ORDER.STARTTIME.ordinal());
        userCell.setCellValue(startTime);
        row.getSheet().autoSizeColumn(ORDER.STARTTIME.ordinal());
    }

    private void setDurationCell(Row row, String duration){
        Cell userCell = row.createCell(ORDER.DURATION.ordinal());
        userCell.setCellValue(duration);
        row.getSheet().autoSizeColumn(ORDER.DURATION.ordinal());
    }

    private void setPassedStatusStyle(Cell statusCell){
        CellStyle statusCellStyle = statusCell.getSheet().getWorkbook().createCellStyle();
        statusCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        statusCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font statusCellFont = statusCell.getSheet().getWorkbook().createFont();
        statusCellFont.setColor(IndexedColors.WHITE.getIndex());
        statusCellStyle.setFont(statusCellFont);
        statusCell.setCellStyle(statusCellStyle);
    }

    private void setFailedStatusStyle(Cell statusCell){
        CellStyle statusCellStyle = statusCell.getSheet().getWorkbook().createCellStyle();
        statusCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        statusCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font statusCellFont = statusCell.getSheet().getWorkbook().createFont();
        statusCellFont.setColor(IndexedColors.WHITE.getIndex());
        statusCellStyle.setFont(statusCellFont);
        statusCell.setCellStyle(statusCellStyle);
    }

    private void setHyperLinkStyle(Cell hyperLinkCell){
        CellStyle hyperLinkCellStyle = hyperLinkCell.getSheet().getWorkbook().createCellStyle();
        Font hyperLinkFont = hyperLinkCell.getSheet().getWorkbook().createFont();
        hyperLinkFont.setUnderline(Font.U_SINGLE);
        hyperLinkFont.setColor(IndexedColors.BLUE.getIndex());
        hyperLinkCellStyle.setFont(hyperLinkFont);
        hyperLinkCell.setCellStyle(hyperLinkCellStyle);
    }
}
