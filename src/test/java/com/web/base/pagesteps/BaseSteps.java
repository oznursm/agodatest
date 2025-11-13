package com.web.base.pagesteps;

import com.web.base.pages.BaseMasterPage;
import com.web.base.pages.ExamplePage;
import com.thoughtworks.gauge.Step;
import com.web.base.utils.driver.Driver;

public class BaseSteps {

    public static String APP_URL = System.getenv("APP_URL");
    private static ExamplePage denemetestPage = ExamplePage.getInstance();

    @Step("Go to project site")
    public void goURL() {
        Driver.webDriver.get(APP_URL);
        Driver.webDriver.manage().window().maximize();
    }

    @Step("Pop-up kapat")
    public void exampleStep() throws InterruptedException {
        Thread.sleep(4000);
        BaseMasterPage a1 = new BaseMasterPage() {
            @Override
            public void ClickPopUp() {
                super.ClickPopUp();
            }
        };

        a1.ClickPopUp();
    }

}

