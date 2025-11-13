package com.web.base.pages;

import com.web.base.utils.driver.Driver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.web.base.pagesteps.BaseSteps.APP_URL;

public class ExamplePage extends BaseMasterPage {
    private static final Log log = LogFactory.getLog(ExamplePage.class);
    private static ExamplePage instance;


    public static synchronized ExamplePage getInstance() {
        if (instance == null)
            instance = new ExamplePage();
        return instance;
    }

    public void exampleMethod() {
        Driver.webDriver.get(APP_URL);
        Driver.webDriver.manage().window().maximize();
        log.info("ENTERING clickCreateAccount");
       // clickWatchLive();

    }



}
