package com.cedex.automation.tests;

import com.cedex.automation.tests.base.AbstractBrowserBaseTest;
import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.files.FilesManipulations;
import com.cedex.numbers.Randomalizator;
import com.cedex.text.RegExpr;
import com.cedex.time.Sleeper;
import jsystem.framework.TestProperties;
import jsystem.framework.report.Reporter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BasicTests extends AbstractBrowserBaseTest {

    private int videoIndex = 0;
    private int timeToPlayEveryVideo = 10;

    private String merchantId = "1";
    private String credentialsFile = "c:\\tmp\\Users.csv";
//    private String email="romang@cedex.com";
//    private String password="123456";

    @Rule
    public TestName testName = new TestName();

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(final Description description) {
            String methodName = description.getMethodName();
            String className = description.getClassName();
            className = className.substring(className.lastIndexOf('.') + 1);
            System.err.println("Starting JUnit-test: " + className + " " + methodName);
        }
    };

    @Test
    public void firstTest() throws Exception {
//        String testName=getCurrentTestName();
        browserIndex = 0;
        report.report("Hello world:" + testName.getMethodName());
        lab.browser[browserIndex].get();
        lab.browser[browserIndex].clickOnUrl("/registration");

//        lab.browser[browserIndex].clickOnElementBy(BrowserBase.RecognizeBy.CLASS, "checkbox-label");
//        lab.browser[browserIndex].clickOnGoogleRecaptcha();
//        lab.browser[browserIndex].clickOnElementBy(BrowserBase.RecognizeBy.CLASS, "recaptcha-checkbox-checkmark");

//        lab.browser[browserIndex].getDriver().findElement(By.cssSelector("div.recaptcha-checkbox-checkmark")).click();

//        driver.findElement(By.xpath(".recaptcha-checkbox-checkmark")).click();

//        lab.browser[browserIndex].clickOnUrl("https://cedex.com/img/Whitepaper.pdf");



//        lab.browser[browserIndex].clickOnElementsBy(BrowserBase.RecognizeBy.ID,"checkbox_terms");
        lab.browser[browserIndex].javaScriptClickOnElement(RecognizeBy.ID, "checkbox_terms");

        ArrayList<WebElement> elems = lab.browser[browserIndex].getWebElements(RecognizeBy.TAG_NAME, "div");
        for (WebElement currElement : elems) {
            if (currElement.getAttribute("class").equals("single-checkbox")) {
                currElement.click();
            }
        }

//        lab.browser[browserIndex].clickOnElementsBy(BrowserBase.RecognizeBy.ID,"single-checkbox" );

//        lab.browser[browserIndex].clickOnElementsBy(BrowserBase.RecognizeBy.CLASS, "formInputsRow checkboxRow");
        ArrayList<WebElement> elems1 = lab.browser[browserIndex].getWebElements(RecognizeBy.CLASS, "checkbox-label");
        for (WebElement elem : elems1) {
            elem.click();
        }

        lab.browser[browserIndex].clickOnUrl("/#about");
//        lab.browser[browserIndex].clickOnUrl("/registration");
    }

    @Test
    @TestProperties(name = "TST: Play all videos on page", paramsInclude = {"browserIndex", "jiraIssueList", "timeToPlayEveryVideo"}, returnParam = {})
    public void playAllVideos() throws Exception {
        lab.browser[browserIndex].get();
        lab.browser[browserIndex].clickOnUrl("/videos");
        ArrayList<WebElement> frames = lab.browser[browserIndex].getWebElements(RecognizeBy.TAG_NAME, "iframe");
        for (WebElement currElem : frames) {
            currElem.click();
//            Sleeper.sleepForNSeconds("playing a video for: " + timeToPlayEveryVideo + " seconds ...", timeToPlayEveryVideo);
            Sleeper.sleepForNTime("playing a video for: " + timeToPlayEveryVideo + " seconds ...",2,TimeUnit.SECONDS);
        }
    }

    @Test
    @TestProperties(name = "TST: Play video index: ${} ", paramsInclude = {"browserIndex", "jiraIssueList", "timeToPlayEveryVideo", "videoIndex"}, returnParam = {})
    public void playVideo() throws Exception {
        lab.browser[browserIndex].get();
        lab.browser[browserIndex].clickOnUrl("/videos");
        ArrayList<WebElement> frames = lab.browser[browserIndex].getWebElements(RecognizeBy.TAG_NAME, "iframe");
//        for (WebElement currElem : frames) {
        if (videoIndex <= frames.size()) {
            WebElement currElem = frames.get(videoIndex);
            currElem.click();
//            Sleeper.sleepForNSeconds("playing a video for: " + timeToPlayEveryVideo + " seconds ...", timeToPlayEveryVideo);
            Sleeper.sleepForNTime("playing a video for: " + timeToPlayEveryVideo + " seconds ...",2,TimeUnit.SECONDS);
        }

//        }
    }

    @Test
    public void testtesttest() throws Exception {
        lab.browser[browserIndex].navigate("http://cedex.com");
        WebElement elem = lab.browser[browserIndex].getWebElement(RecognizeBy.CLASS, "contribution_info_item_amount contribution_info_item_sold_amount");
        String text = elem.getText();

    }

    @Test
    @TestProperties(name = "TST: Wallet validation test", paramsInclude = {"browserIndex", "jiraIssueList", "credentialsFile", "merchantId"}, returnParam = {})
    public void walletValidation() throws Exception {
        String credentionsFileContent = FilesManipulations.readFileAsString(credentialsFile);
        String[] credentionsFileContentAsArr = credentionsFileContent.split("\n");
//        int randomLineIndex = 1;
        int randomLineIndex = Randomalizator.getRandomNumber(credentionsFileContentAsArr.length - 1);
        randomLineIndex = randomLineIndex == 0 ? 1 : randomLineIndex;

        String randomLine = credentionsFileContentAsArr[randomLineIndex];
        String email = randomLine.split(",")[0];
        String password = randomLine.split(",")[1];
        lab.browser[browserIndex].get();
        lab.browser[browserIndex].deleteAllCookies();
        report.report("Login with user: " + email, Reporter.ReportAttribute.BOLD);
        lab.browser[browserIndex].clickOnUrl("/login");
//        Sleeper.sleepForRandomNTime(2000, 9000);
        Sleeper.sleepForRandomNTime(2, 9, TimeUnit.SECONDS);
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "email", email);
//        Sleeper.sleepForRandomNTime(3000, 13000);
        Sleeper.sleepForRandomNTime(3, 13, TimeUnit.SECONDS);
        lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "password", password);
//        Sleeper.sleepForRandomNTime(3000, 10000);
        Sleeper.sleepForRandomNTime(3, 10, TimeUnit.SECONDS);
        lab.browser[browserIndex].clickOnGoogleRecaptcha();
//        Sleeper.sleepForRandomNTime(1000, 6000);
        Sleeper.sleepForRandomNTime(1, 6, TimeUnit.SECONDS);
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CLASS, "popUpButton");
        try {
            lab.browser[browserIndex].scrollInElementBy(RecognizeBy.ID, "popup_terms", 100000);
//            Sleeper.sleepForNSeconds(4);
            Sleeper.sleepForNTime(4, TimeUnit.SECONDS);
            lab.browser[browserIndex].javaScriptClickOnElement(RecognizeBy.ID, "btn_accept");
        } catch (Exception e) {
            report.report("Terms of condition pop up wasnt");
        }

        lab.browser[browserIndex].javaScriptClickOnElement(RecognizeBy.ID, "cont_btn");
        report.report("Getting wallet number from cedex.com ", Reporter.ReportAttribute.BOLD);
//        Sleeper.sleepForNSeconds(2 );
        Sleeper.sleepForNTime(2, TimeUnit.SECONDS);
        String wallerNumber = lab.browser[browserIndex].getWebElement(RecognizeBy.ID, "address").getAttribute("value");
        report.report("Wallet is: " + wallerNumber);
        lab.browser[browserIndex].getDriver().quit();
//        String wallerNumber="0x762e426d698e51797d6b7e61c9d1e8e647b5b145";

        String reqAsStr = lab.restApi.getRequestSender().sendWithParameters("", "address", wallerNumber, "merchantId", merchantId + "").toString();
        report.report("Validating wallet: " + wallerNumber + " vs wallet validation API", Reporter.ReportAttribute.BOLD);
        RegExpr regExpr = new RegExpr(reqAsStr, "result\":(true|false)");
        if (regExpr.isFound()) {
            String foundResult = regExpr.getGroupText(2);
            if (foundResult.equalsIgnoreCase("true")) {
                report.report("OK : found wallet id: " + wallerNumber + " belongs to Cedex");
            } else {
                throw new Exception("EXCEPTION - found wallet id: " + wallerNumber + " OUT OF Cedex !!!!!");
            }
        }


    }

    public String getCredentialsFile() {
        return credentialsFile;
    }

    public void setCredentialsFile(String credentialsFile) {
        this.credentialsFile = credentialsFile;
    }

    public int getVideoIndex() {
        return videoIndex;
    }

    /**
     * video idex to play (in order as it shown on /vedeos page) started from 0
     *
     * @param videoIndex
     */
    public void setVideoIndex(int videoIndex) {
        this.videoIndex = videoIndex;
    }

    public int getTimeToPlayEveryVideo() {
        return timeToPlayEveryVideo;
    }

    /**
     * time to play video in seconds
     *
     * @param timeToPlayEveryVideo
     */
    public void setTimeToPlayEveryVideo(int timeToPlayEveryVideo) {
        this.timeToPlayEveryVideo = timeToPlayEveryVideo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
