package com.web.base.pageElement;

import com.web.base.backend.WebAutomationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class BeinCheckBox extends PageElementModel {
    private static final Log log = LogFactory.getLog(BeinCheckBox.class);

    public BeinCheckBox(selectorNames selectorName, String selectorValue) {
        super(selectorName, selectorValue);
    }

    public void check(){
        log.info("ABOUT TO CHECK CHECKBOX: " + getLoggingName());
        WebElement we = null;
        try {
            we = getWebElement();
        } catch (NoSuchElementException e) {
            String error = "ELEMENT NOT FOUND: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
        if (!we.isSelected()){
            try {
                getLabelElement().click();
            } catch (Exception e) {
                String error = "COULD NOT CLICK CHECKBOX LABEL: " + getLoggingName();
                log.error(error);
                throw new WebAutomationException(error);
            }
        }
    }

    public void waitAndCheck(int... timeOut){
        int timeOutI = 5;
        if (timeOut.length != 0){
            timeOutI = timeOut[0];
        }
        try {
            Thread.sleep(timeOutI * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("WAITING BEFORE CHECK CHECKBOX: " + getLoggingName() + " WAIT TIME IS: " + timeOutI);
        check();
    }

    private WebElement getLabelElement() throws NoSuchElementException {
        return webdriver.findElement(By.xpath("//label[@for='" + getId() + "']"));
    }
}
