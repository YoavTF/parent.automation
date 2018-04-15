package com.cedex.browser;

import org.junit.Assert;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeBrowser extends BrowserBase {

    private String iexplorerDriverShortName="MicrosoftWebDriver.exe";

    public void init() throws Exception {
        driverShortName=iexplorerDriverShortName;
        super.init();
        Assert.assertNotNull("Please define the Browser URL in the SUT file", url);

        String fullPathToDriver = super.getFullPathSeleniumToDriver();

        System.setProperty("webdriver.edge.driver", fullPathToDriver);



    }

    @Override
    protected void openBrowser() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }
}
