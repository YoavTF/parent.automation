package com.cedex.automation.tests;

import com.cedex.automation.src.wrappers.CedexRegistrationWrapperBrowser;
import com.cedex.automation.tests.base.AbstractBrowserBaseTest;
import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.numbers.Randomalizator;
import com.cedex.text.StringUtils.StringOperator;
import com.cedex.time.Sleeper;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;


public class AccessTests extends AbstractBrowserBaseTest {

    private StringBuffer verificationErrors = new StringBuffer();
    private CedexRegistrationWrapperBrowser cedexRegistrationWrapper;


    @Before
    public void before() {

        cedexRegistrationWrapper = new CedexRegistrationWrapperBrowser(lab,browserIndex);
    }

    @Test
    public void testLoginLogout() throws Exception {
        lab.browser[browserIndex].get(); // open base url
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "a.logIn > span");// click login button
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "button.popUpButton"); // click LOG IN button
        try {
            boolean isErrorMailExists = lab.browser[browserIndex].getValidator().isElementPresent(RecognizeBy.ID, "email-error");
            addSubScenarioResult("Validation email error ", isErrorMailExists);
//            assertTrue(isElementPresent(By.id("email-error"))); // verify error present
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        try {
            boolean isPasswordErrorExists = lab.browser[browserIndex].getValidator().isElementPresent(RecognizeBy.ID, "password-error");
            addSubScenarioResult("Validatinig password error ", isPasswordErrorExists);
//            assertTrue(isElementPresent(By.id("password-error"))); // verify error present
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "email", "testforautomation123@qwe.uf"); // type email
        lab.browser[browserIndex].sendKeys(RecognizeBy.ID, "password", "123123"); //type password
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "button.popUpButton"); // click LOG IN button

        try {
            String mainPageTitle = "CEDEX - Certified BlockChain Based Diamond Exchange | Create Contributor";
//            assertEquals("CEDEX - Certified BlockChain Based Diamond Exchange | Create Contributor", lab.browser[browserIndex].getDriver().getTitle()); //verify login successful
            addSubScenarioResult("Main page title", lab.browser[browserIndex].getValidator().isTitleExists(mainPageTitle, StringOperator.EQUALS));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "a.logOut > span"); // click logout button
//        assertTrue(isElementPresent(By.cssSelector("a.logIn > span"))); // verify logout successful
        boolean isloginExists = lab.browser[browserIndex].getValidator().isElementPresent(RecognizeBy.CSS_SELECTOR, "a.logIn > span");
        super.addSubScenarioResult("login button existence", isloginExists);
    }

    @Test
    public void testRegistration() throws Exception {
        lab.browser[browserIndex].get(); // open base url
        String cedexTabTitle = lab.browser[browserIndex].getDriver().getTitle();
        lab.browser[browserIndex].navigate("https://temp-mail.org/en/option/change");

        String emailTabTitle = lab.browser[browserIndex].getDriver().getTitle();
        // mailer page
        String username = "testuserforautotests" + Randomalizator.getRandomNumber(10000); // random # from 10000
        String domain = "@carbtc.net";
        String email = username + domain;
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "mail", username); //fill mail field

        Sleeper.sleepForNTime(2, TimeUnit.SECONDS);
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.ID, "postbut"); //click
//        Sleeper.sleepForRandomNTime(1000, 7000);
        Sleeper.sleepForRandomNTime(2, 7,TimeUnit.SECONDS );
        boolean isMailCreated = lab.browser[browserIndex].getValidator().isElementPresent(RecognizeBy.CLASS, "alert alert-success alert-dismissable");
        addSubScenarioResult("validation temp mail creation", isMailCreated);
//        Sleeper.sleepForRandomNTime(1000, 7000); // sleep for x seconds
        Sleeper.sleepForRandomNTime(3, 8,TimeUnit.SECONDS );
        lab.browser[browserIndex].selectTabByTitle(cedexTabTitle,StringOperator.CONTAINS);
        lab.browser[browserIndex].get();
        lab.browser[browserIndex].clickOnUrl("/registration"); // open cedex page

        //first registration page
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "first_name", "test"); // type firstname
//        Sleeper.sleepForRandomNTime(1000, 7000); // sleep for x seconds
        Sleeper.sleepForRandomNTime(2, 7,TimeUnit.SECONDS );
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "last_name", "test"); // type lastname
//        Sleeper.sleepForRandomNTime(1000, 7000); // sleep for x seconds
        Sleeper.sleepForRandomNTime(2, 7,TimeUnit.SECONDS );
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "email", email); // type email
        Sleeper.sleepForRandomNTime(2, 7,TimeUnit.SECONDS );
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "password", "123123");
        Sleeper.sleepForRandomNTime(2, 7,TimeUnit.SECONDS );
        //SELECT RUSSIA
        lab.browser[browserIndex].selectDropdownListValue(RecognizeBy.NAME, "country", "RU");
        Sleeper.sleepForRandomNTime(2, 7,TimeUnit.SECONDS );
        // type password
        lab.browser[browserIndex].javaScriptClickOnElement(RecognizeBy.ID, "checkbox_terms");
        Sleeper.sleepForRandomNTime(2, 7,TimeUnit.SECONDS );
//        lab.browser[browserIndex].clickOnElementBy(BrowserBase.RecognizeBy.XPATH,"//form[@id='register-form']/fieldset/div[6]/div/div/div/label"); //click checbox
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.ID, "checkbox"); //click checbox
//        Sleeper.sleepForNSeconds("wait for captcha", 3); // sleep for x seconds
        Sleeper.sleepForNTime("wait for captcha", 3, TimeUnit.SECONDS );
        lab.browser[browserIndex].clickOnGoogleRecaptcha(); //click on captcha
//        Sleeper.sleepForRandomNTime( 10); // sleep for x seconds
//        Sleeper.sleepForNSeconds("wait for captcha", 10); // sleep for x seconds
        Sleeper.sleepForNTime("wait for captcha", 10, TimeUnit.SECONDS );

        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "button.popUpButton"); //click REGISTER button

        try {
            lab.browser[browserIndex].scrollInElementBy(RecognizeBy.ID,"popup_terms",100000);
//            Sleeper.sleepForNSeconds(4);
            Sleeper.sleepForNTime(4,TimeUnit.SECONDS );
            lab.browser[browserIndex].javaScriptClickOnElement(RecognizeBy.ID,"btn_accept");
//            lab.browser[browserIndex].javaScriptClickOnElement(RecognizeBy.ID, "cont_btn");
        } catch (Exception e) {
            report.report("Terms of condition pop up wasnt");
        }


        //second registration page
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "tokens_amount", "500"); // type amount
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "wallet", "0x34Cfc546Bef39C924732B37Fa2213EEe2DEEe914"); // type wallet
//        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "button.popUpButton"); //click  COMPLETE REGISTRATION button

        Sleeper.sleepForNTime( "wait for complete registration", 3,TimeUnit.SECONDS);
        // go to mailer

//        lab.browser[browserIndex].selectTabByTitle(emailTabTitle);
        lab.browser[browserIndex].navigate("https://temp-mail.org/en/option/change/"); // open url
         Sleeper.sleepForNTime( "wait for email to come", 3,TimeUnit.SECONDS);
//        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "span.icon-control.control-refresh"); //click on REFRESH
        // Sleeper.sleepForNSeconds("wait for complete registration", 3); // sleep for x seconds
//        lab.browser[browserIndex].waitForElementPresent(RecognizeBy.CSS_SELECTOR, "span.glyphicon.glyphicon-chevron-right", 60);

        WebElement refresh = lab.browser[browserIndex].getWebElement(RecognizeBy.ID, "click-to-refresh");
        cedexRegistrationWrapper.waitForEmailReceiving(browserIndex, RecognizeBy.CSS_SELECTOR, "span.glyphicon.glyphicon-chevron-right", refresh);


//        lab.browser[browserIndex].closeOtherTabs();
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "span.glyphicon.glyphicon-chevron-right"); //click on chevron
//        Sleeper.sleepForNSeconds("wait for email to load", 5); // sleep for x seconds
        Sleeper.sleepForNTime( "wait for email to load", 5,TimeUnit.SECONDS);
//        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.PATIAL_LINK_TEXT, "activate");
        lab.browser[browserIndex].clickOnUrl("http://adf.ly/");
        Sleeper.sleepForNTime(  10,TimeUnit.SECONDS);
        lab.browser[browserIndex].clickOnUrlInTempMail("http://links.cedex.com");

        Sleeper.sleepForNTime( "wait for email to load", 3,TimeUnit.SECONDS);

        try {
            lab.browser[browserIndex].selectTabByTitle(cedexTabTitle,StringOperator.CONTAINS);
//            boolean resistrationVerificationResult = lab.browser[browserIndex].getValidator().isElementPresent(RecognizeBy.CSS_SELECTOR, "div.success-holder-title");
//            super.addSubScenarioResult("Registration verification: ", resistrationVerificationResult);


        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CSS_SELECTOR, "a.logOut > span");


    }

    @Test
    public void testCloseWindow() throws Exception {

        report.report("closing the window");
        lab.browser[browserIndex].getDriver().quit(); //closing the window


    }

    // CAPTCHA CLICKER
//    public void clickOnGoogleRecaptcha() throws InterruptedException {
//
//
//        ArrayList<WebElement> divs = lab.browser[browserIndex].getWebElements(RecognizeBy.TAG_NAME, "div");
//        for (WebElement currDiv : divs) {
//            String currClassName = currDiv.getAttribute("class");
//            report.report("div class: " + currClassName);
//            if (currClassName.contains("recaptcha")) {
//                currDiv.click();
//                break;
//            }
//        }
//    }


//    private boolean isElementPresent(By by) {
//        try {
//            lab.browser[browserIndex].getDriver().findElement(by);
//            return true;
//        } catch (NoSuchElementException e) {
//            return false;
//        }
//    }


}
