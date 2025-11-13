package com.web.base.pageElement;

import com.web.base.backend.WebAutomationException;
import com.web.base.utils.driver.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public abstract class PageElementModel {
    String xPath;
    String id;
    String name;
    String className;
    String css;
    String loggingName;
    static WebDriver webdriver = Driver.webDriver;
    public enum selectorNames {XPATH, ID, NAME, cssSelector, CLASS_NAME};

    public PageElementModel(selectorNames selectorName, String selectorValue) {
        switch (selectorName) {
            case ID:
                id = selectorValue;
                loggingName = "ID: " + selectorValue;
                break;
            case NAME:
                name = selectorValue;
                loggingName = "NAME: " + selectorValue;
                break;
            case XPATH:
                xPath = selectorValue;
                loggingName = "XPATH: " + selectorValue;
                break;
            case CLASS_NAME:
                className = selectorValue;
                loggingName = "CLASS: " + selectorValue;
                break;
            case cssSelector:
                css = selectorValue;
                loggingName = "CSS: " + selectorValue;
                break;
            default:
                className = id;
                break;
        }
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public String getCssSelector() {
        return css;
    }



    public void setClassName(String className) {
        this.className = className;
    }

    protected String getLoggingName() {
        return loggingName;
    }

    public WebDriver getDriver(){
        return webdriver;
    }

    public WebElement getWebElement() throws NoSuchElementException {
        if(getId() != null)
            return webdriver.findElement(By.id(getId()));
        else if(getName() != null)
            return webdriver.findElement(By.name(getName()));
        else if(getxPath() != null)
            return webdriver.findElement(By.xpath(getxPath()));
        else if(getClassName() != null)
            return webdriver.findElement(By.className(getClassName()));
        else if(getCssSelector() != null)                      // ✔ artık doğru
            return webdriver.findElement(By.cssSelector(getCssSelector()));
        return null;
    }

    public void waitUntilVisible(int... timeOut){
        int timeOutI = 1;
        if (timeOut.length != 0){
            timeOutI = timeOut[0];
        }
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        boolean isElementFound = false;
        int retryCount = timeOutI * 30;
        while(!isElementFound && retryCount > 0) {
            try {
                wait.until(ExpectedConditions.visibilityOf(getWebElement()));
                isElementFound = true;
            } catch (NoSuchElementException|TimeoutException e) {
                retryCount--;
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(!isElementFound && retryCount == 0){
            throw new WebAutomationException("Could not find element in expected time");
        }
    }

    public void waitFor(int... timeOut){
        int timeOutI = 2;
        if (timeOut.length != 0){
            timeOutI = timeOut[0];
        }
        try {
            Thread.sleep(timeOutI * 1000L);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public void waitUntilInvisible(int... timeOut){
        int timeOutI = 30;
        if (timeOut.length != 0){
            timeOutI = timeOut[0];
        }
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutI));
        try {
            wait.until(ExpectedConditions.invisibilityOf(getWebElement()));
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public void hoverOn(){
        Actions builder = new Actions(getDriver());
        builder.moveToElement(getWebElement()).build().perform();
    }

    public boolean isDisplayed(){
        try {
            return getWebElement().isDisplayed();
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public void scrollToElement(){
        Actions actions = new Actions(webdriver);
        actions.moveToElement(getWebElement());
        actions.perform();
    }
}
