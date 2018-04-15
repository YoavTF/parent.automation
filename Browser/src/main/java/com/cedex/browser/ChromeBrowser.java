package com.cedex.browser;

import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class ChromeBrowser extends BrowserBase {
    //    String driverPath = "C:\\Program Files (x86)\\Google\\Chrome\\Application";
//    private String driverPath = "C:\\tmp";
    private String chromeDriverShortName ="chromedriver.exe";

    public void init() throws Exception {
        driverShortName= chromeDriverShortName;
        super.init();
        Assert.assertNotNull("Please define the Browser URL in the SUT file", url);

        String fullPathToDriver = super.getFullPathSeleniumToDriver();
        System.setProperty("webdriver.chrome.driver", fullPathToDriver);

    }

    @Override
    protected void openBrowser() {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        driver = new ChromeDriver(options);
//        driver.manage().window().fullscreen();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);

    }

//    public String getDriverPath() {
//        return driverPath;
//    }
//
//    public void setDriverPath(String driverPath) {
//        this.driverPath = driverPath;
//    }
}
