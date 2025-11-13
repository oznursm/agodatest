package com.web.base.pageElement;

import com.web.base.backend.WebAutomationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BeinSelect extends PageElementModel {
    private static final Log log = LogFactory.getLog(BeinSelect.class);

    public BeinSelect(selectorNames selectorName, String selectorValue) {
        super(selectorName, selectorValue);
    }

    public void selectByText(String text){
        log.info("ABOUT TO SELECT: " + getLoggingName());
        WebElement we = null;
        try {
            we = getWebElement();
        } catch (NoSuchElementException e){
            String error = "ELEMENT NOT FOUND: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
        Select select = new Select(we);
        try {
            select.selectByVisibleText(text);
        } catch (Exception e) {
            String error = "COULD NOT SELECT WITH TEXT: " + text + " FROM: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
    }

    public void selectByValue(String value){
        log.info("ABOUT TO SELECT: " + getLoggingName());
        WebElement we = null;
        try {
            we = getWebElement();
        } catch (NoSuchElementException e){
            String error = "ELEMENT NOT FOUND: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
        Select select = new Select(we);
        try {
            select.selectByValue(value);
        } catch (Exception e) {
            String error = "COULD NOT SELECT WITH VALUE: " + value + " FROM: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
    }

    public void waitUntilAndSelectByText(String text){
        log.info("WAITING FOR SELECT: " + getLoggingName());
        waitUntilVisible();
        selectByText(text);
    }

    public void waitUntilAndSelectByValue(String value){
        log.info("WAITING FOR SELECT: " + getLoggingName());
        waitUntilVisible();
        selectByValue(value);
    }
}
