package com.web.base.backend;

import com.web.base.utils.driver.Driver;
import com.web.base.utils.jira.JiraUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class WebAutomationException extends RuntimeException {
    public WebAutomationException (String message){
        super(message);
        File scrFile = ((TakesScreenshot)Driver.webDriver).getScreenshotAs(OutputType.FILE);
        String filePath = "screenshots\\screenshot " + BaseAutomationContext.getContextValue(ContextKeys.CASENAME) + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String description = "BASE WEB AUTOMATION FAILED AT STEP: " + BaseAutomationContext.getContextValue(ContextKeys.STEPNAME) + " WITH EXCEPTION MESSAGE: " + message;
        BaseAutomationContext.addContext(ContextKeys.EXCEPTION, description);
        BaseAutomationContext.addContext(ContextKeys.SSLINK, filePath);
        String environment = System.getenv("ENVIRONMENT");
        if(false && environment.equalsIgnoreCase("PROD")) {
            if(!JiraUtil.createIssue("RA", BaseAutomationContext.getContextValue(ContextKeys.CASENAME), description, filePath, "ringadmin")){
                System.out.println("COULD NOT OPEN JIRA ISSUE");
            }
        }
    }
}
