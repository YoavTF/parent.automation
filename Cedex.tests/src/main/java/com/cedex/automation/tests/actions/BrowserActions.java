package com.cedex.automation.tests.actions;

import com.cedex.automation.tests.base.AbstractBaseTest;
import com.cedex.browser.BrowserBase.RecognizeBy;
import jsystem.framework.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class BrowserActions extends AbstractBaseTest {

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    private String defaultUrl = "baseUrl";
    private String url = defaultUrl;
//    private String urlNavigate;
    private RecognizeBy recognizeBy;
    private String identifier;
    private String keysToSend;

    private String dropDownValueToSelect = "";
    private int dropDownIndexToSelect = -1;
    private int maxTimeToWaitInSec = 120;
    private String attributeName;
    private String expectedAttributeValue;

    private String browserTitle;



    private String emailAddress;
    private String password;

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    /**
     * For full URL use: http://[you url]
     * for relative from baseUrl (defined in SUT sule) use /[you url]
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: navigate to page ", paramsExclude = {"attributeName","expectedAttributeValue","urlNavigate","keysToSend", "recognizeBy", "identifier", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "maxTimeToWaitInSec"}, returnParam = {})
    public void navigateBB() throws Exception {
        if (url.equals(defaultUrl)) {
            lab.browser[browserIndex].navigate();
        } else {
            url = super.loadStoredParameter(url);
            lab.browser[browserIndex].navigate(url);
        }
    }


    /**
     * Returns current browsers title to variable: browserTitle
     * to use this variable -> ${browserTitle}
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: return current browser title ", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "recognizeBy", "identifier", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {"browserTitle"})
    public void returnBrowserTitleBB() throws Exception {
        browserTitle = lab.browser[browserIndex].getDriver().getTitle();
    }

    /**
     * For full URL use: http://[you url]
     * for relative from baseUrl (defined in SUT sule) use /[you url]
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: navigate in New Tab to page ", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "recognizeBy", "identifier", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "maxTimeToWaitInSec"}, returnParam = {})
    public void navigateNewTab() throws Exception {
        lab.browser[browserIndex].get();
        String url = this.url.equals(defaultUrl) ? lab.browser[browserIndex].getUrl() : this.url;
        lab.browser[browserIndex].getInNewTab(url);
    }

    /**
     * click on google new recaptch stage 1 (im not a robot)
     * if you got stage 2 google recaptcha (choose pictures like) this  cannot be automated !!
     *
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    @TestProperties(name = "BB: CLICK on Google Recaptcha checkbox", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "recognizeBy", "identifier", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {})
    public void clickOnGoogleRecaptcha() throws InterruptedException, IOException {
        lab.browser[browserIndex].clickOnGoogleRecaptcha();
    }

    /**
     * click on image URL (src) :
     * element like:
     * <img src="./img/editable.png" class="w1 dib pointer">
     * in this case set parameter url= ./img/editable.png
     */
    @Test
    @TestProperties(name = "BB: CLICK on Image SRC", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "recognizeBy", "identifier", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "maxTimeToWaitInSec"}, returnParam = {})
    public void clickOnImageSrc() {
        lab.browser[browserIndex].clickOnUrlImage(url);
    }

    @Test
    @TestProperties(name = "BB: CLICK on URL", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "recognizeBy", "identifier", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "maxTimeToWaitInSec"}, returnParam = {})
    public void clickOnUrl() {
        lab.browser[browserIndex].clickOnUrl(url);
    }

    @Test
    @TestProperties(name = "BB: CLICK on web element: ${identifier} by: ${recognizeBy}", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {})
    public void clickOnElementBy() {
        Assert.assertNotNull("Please define the mandatory  Recognize by element parameter", recognizeBy);
        Assert.assertNotNull("Please define the mandatory Identifier  parameter", identifier);
        lab.browser[browserIndex].clickOnElementBy(recognizeBy, identifier);
    }

    /**
     * click on element only if RecognizeBY + idetifier and attribute have expected value
     * (some objectes have the same RecognizeBY + idetifier and to distinguish between added attibute and its value
     */
    @Test
    @TestProperties(name = "BB: CLICK on web element: ${identifier} by: ${recognizeBy} with attibute ${attributeName}", paramsExclude = {"keysToSend", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {})
    public void clickOnElementByAndHaveAttrute() {
        Assert.assertNotNull("Please define the mandatory  Recognize by element parameter", recognizeBy);
        Assert.assertNotNull("Please define the mandatory Identifier  parameter", identifier);
        lab.browser[browserIndex].clickOnElementByAndHaveAttrute(recognizeBy, identifier,attributeName,expectedAttributeValue);
    }


    /**
     * in some cases you can't click on visible elements or click on element do nothing
     * for those cases you can use this bb to click on elemeny by executing the java script
     */
    @Test
    @TestProperties(name = "BB: CLICK on web element using JS:  ${identifier} by: ${recognizeBy}", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {})
    public void clickOnElementByJS() {
        Assert.assertNotNull("Please define the mandatory  Recognize by element parameter", recognizeBy);
        Assert.assertNotNull("Please define the mandatory Identifier  parameter", identifier);
        lab.browser[browserIndex].javaScriptClickOnElement(recognizeBy, identifier);
    }


    /**
     * fill textboxes , textareas or other textual web elemeents with keyboard charachters
     * to load from stored parameter use: # sign before stored parameter name
     */
    @Test
    @TestProperties(name = "BB: Scroll to element: ${identifier} by: ${recognizeBy}", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend","dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {})
    public void scrollToElement() throws IOException {

        lab.browser[browserIndex].scrollToElementBy(recognizeBy, identifier);
    }

    /**
     * fill textboxes , textareas or other textual web elemeents with keyboard charachters
     * to load from stored parameter use: # sign before stored parameter name
     */
    @Test
    @TestProperties(name = "BB: Send keys :${keysToSend} to element: ${identifier} by: ${recognizeBy}", paramsExclude = {"attributeName","expectedAttributeValue","dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {})
    public void sendKeys() throws Exception {
        Assert.assertNotNull("Please define the mandatory  Recognize by element parameter", recognizeBy);
        Assert.assertNotNull("Please define the mandatory Identifier  parameter", identifier);
        keysToSend = super.loadStoredParameter(keysToSend);
        lab.browser[browserIndex].sendKeys(recognizeBy, identifier, keysToSend);
    }

    /**
     * Settting focus on any Element
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Set focus on element: ${identifier} by: ${recognizeBy}", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url", "maxTimeToWaitInSec"}, returnParam = {})
    public void setFocus() throws Exception {
        Assert.assertNotNull("Please define the mandatory  Recognize by element parameter", recognizeBy);
        Assert.assertNotNull("Please define the mandatory Identifier  parameter", identifier);
        lab.browser[browserIndex].setFocusOnElementBy(recognizeBy, identifier);
    }

    /**
     * Settting focus on any Element
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Wait for element: ${identifier} by: ${recognizeBy} up to: ${maxTimeToWaitInSec} sec", paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "dropDownValueToSelect", "dropDownIndexToSelect", "browserTitle", "url"}, returnParam = {})
    public void waitForElement() throws Exception {
        Assert.assertNotNull("Please define the mandatory  Recognize by element parameter", recognizeBy);
        Assert.assertNotNull("Please define the mandatory Identifier  parameter", identifier);
        lab.browser[browserIndex].waitForElementPresent(recognizeBy, identifier, maxTimeToWaitInSec);
    }

    /**
     * Selecting a value from dropdown/combobox list by its value or by value index
     * RELEVANT FOR DROPDAWN USING select TAB
     * use parameters: dropDownValueToSelect or dropDownIndexToSelect both of them can't be an empty
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Select a value ${dropDownValueToSelect} or index: ${dropDownIndexToSelect} of dropdown list of element: ${identifier} by: ${recognizeBy}",
            paramsExclude = {"attributeName","expectedAttributeValue","keysToSend", "browserTitle", "url", "maxTimeToWaitInSec"},
            returnParam = {})
    public void selectDropDownElementValue() throws Exception {


        Assert.assertNotNull("Please define the mandatory  Recognize by element parameter", recognizeBy);
        Assert.assertNotNull("Please define the mandatory Identifier  parameter", identifier);

        if (!dropDownValueToSelect.equals(""))
            lab.browser[browserIndex].selectDropDownListElement(recognizeBy, identifier, dropDownValueToSelect);
        else if (dropDownIndexToSelect > -1) {
            lab.browser[browserIndex].selectDropDownListElement(recognizeBy, identifier, dropDownIndexToSelect);
        } else {
            throw new Exception("No one of value and index of dropdown list not selected");
        }
    }

    /**
     * Login to cedex webpage
     *
     */
    @Test
    @TestProperties(name = "Login to Cedex" ,paramsInclude = {"password","emailAddress"})
    public void logIn(){
        String URL = "https://cedex.com/";

        try {
            lab.browser[browserIndex].navigate(URL);
            lab.browser[browserIndex].clickOnElementBy(RecognizeBy.XPATH,"//*[@id=\"header\"]/div/div/div[1]/div/ul/li[8]/a/span");
            lab.browser[browserIndex].waitForLoad();
            lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "email" ,emailAddress);
            lab.browser[browserIndex].sendKeys(RecognizeBy.NAME, "password" ,password);
            lab.browser[browserIndex].clickOnGoogleRecaptcha();
                lab.browser[browserIndex].clickOnElementBy(RecognizeBy.XPATH, "//*[@id=\"login-form\"]/fieldset/div[4]/button");
            report.report("fddfsadsfaafdsa");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------

    public String getKeysToSend() {
        return keysToSend;
    }

    public void setKeysToSend(String keysToSend) {
        this.keysToSend = keysToSend;
    }

    public RecognizeBy getRecognizeBy() {
        return recognizeBy;
    }

    /**
     * this parameter is mandatory (identification by id,class name , text etc)
     *
     * @param recognizeBy
     */
    public void setRecognizeBy(RecognizeBy recognizeBy) {
        this.recognizeBy = recognizeBy;
    }

    public String getIdentifier() {
        return identifier;
    }

    /**
     * this parameter is mandatory (idetify an element by this string)
     *
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDropDownValueToSelect() {
        return dropDownValueToSelect;
    }

    public void setDropDownValueToSelect(String dropDownValueToSelect) {
        this.dropDownValueToSelect = dropDownValueToSelect;
    }

    public int getDropDownIndexToSelect() {
        return dropDownIndexToSelect;
    }

    public void setDropDownIndexToSelect(int dropDownIndexToSelect) {
        this.dropDownIndexToSelect = dropDownIndexToSelect;
    }

    public String getBrowserTitle() {
        return browserTitle;
    }

    public void setBrowserTitle(String browserTitle) {
        this.browserTitle = browserTitle;
    }

    public String getUrl() {
        return url;
    }


    /**
     * url address example: http://cedex.com
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
     }


    public int getMaxTimeToWaitInSec() {
        return maxTimeToWaitInSec;
    }



    /**
     * maximut time to wait in seconds
     *
     * @param maxTimeToWaitInSec
     */
    public void setMaxTimeToWaitInSec(int maxTimeToWaitInSec) {
        this.maxTimeToWaitInSec = maxTimeToWaitInSec;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setEmailAddress(String emailAddress){
        this.emailAddress=emailAddress;
    }

    public String getPassword(){
        return this.password;
    }
    public String getEmailAddress(){
        return this.emailAddress;
    }

}

