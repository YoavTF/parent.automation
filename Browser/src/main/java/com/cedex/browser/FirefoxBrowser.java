package com.cedex.browser;

import com.cedex.StationUtils;
import com.cedex.os.OS;
import org.junit.Assert;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxBrowser extends BrowserBase {

    private String firefoxDriverShortName="geckodriver.exe";

    public void init() throws Exception {
        driverShortName=firefoxDriverShortName;
        super.init();
        Assert.assertNotNull("Please define the Browser URL in the SUT file", url);


    }

    @Override
    protected void openBrowser() throws Exception {
        String fullPathToDriver = super.getFullPathSeleniumToDriver();
        System.setProperty("webdriver.gecko.driver", fullPathToDriver);
        driver = new FirefoxDriver();
//        driver.manage().window().fullscreen();
        driver.manage().window().maximize();
    }


}
