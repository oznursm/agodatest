package com.web.base.pageElement;

import com.web.base.backend.WebAutomationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class BeinTextBox extends PageElementModel {
    private static final Log log = LogFactory.getLog(BeinTextBox.class);

    public BeinTextBox(selectorNames selectorName, String selectorValue) {
        super(selectorName, selectorValue);
    }

    public void type(String inputText){
        log.info("ABOUT TO TYPE TEXTBOX " + getLoggingName());
        WebElement we = null;
        try {
            we = getWebElement();
        } catch (NoSuchElementException e){
            String error = "ELEMENT NOT FOUND: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
        try {
            we.sendKeys(inputText);
        } catch (Exception e) {
            String error = "COULD NOT TYPE TEXTBOX: " + getLoggingName() + " TEXT IS: " + inputText;
            log.error(error);
            throw new WebAutomationException(error);
        }
    }

    public void waitUntilVisibleAndType(String inputText, int... timeOut){
        log.info("WAITING FOR TEXTBOX: " + getLoggingName());
        waitUntilVisible(timeOut);
        type(inputText);
    }

    public void clearText(){
        log.info("ABOUT TO CLEAR TEXTBOX " + getLoggingName());
        WebElement we = null;
        try {
            we = getWebElement();
        } catch (NoSuchElementException e){
            String error = "ELEMENT NOT FOUND: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
        we.clear();
    }
    public void pressEnter() {
        log.info("ABOUT TO PRESS ENTER ON TEXTBOX " + getLoggingName());
        WebElement we = null;
        try {
            we = getWebElement();
        } catch (NoSuchElementException e) {
            String error = "ELEMENT NOT FOUND: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
        try {
            we.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            String error = "COULD NOT PRESS ENTER ON TEXTBOX: " + getLoggingName();
            log.error(error);
            throw new WebAutomationException(error);
        }
    }
}
