package com.cedex.automation.src.wrappers;

import com.cedex.automation.src.Lab;
import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.text.StringUtils;
import com.cedex.text.StringUtils.StringOperator;
import com.cedex.time.Sleeper;
import jsystem.framework.report.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GmailWrapperBrowser extends BrowserBaseWrapper {

    public GmailWrapperBrowser(Lab lab, int browserIndex) {
        super(lab, browserIndex);
    }

    /**
     * Login to gmail
     *
     * @throws Exception
     */
    public void gMailLogin(String userName, String userPassword) throws Exception {

        //String loginUrl="https://accounts.google.com/ServiceLogin/signinchooser?flowName=GlifWebSignIn&flowEntry=ServiceLogin";
        String loginUrl = "https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
        lab.browser[browserIndex].navigate(loginUrl);
        lab.browser[browserIndex].waitForLoad();
        report.report("Fill gmail user: " + userName + " ...");
        lab.browser[browserIndex].sendKeys(RecognizeBy.ID, "identifierId", userName);
//        report.report("Click on next button ...");
//        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CLASS, "RveJvd snByac");
//        driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
//        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
//        Sleeper.sleepForNSeconds("Waiting 3 sec for password textbox element", 3);
        Sleeper.sleepForNTime("Waiting 3 sec for password textbox element", 3, TimeUnit.SECONDS);
        lab.browser[browserIndex].waitForLoad();
        report.report("Fill gmail password: " + userPassword + " ...");
        lab.browser[browserIndex].sendKeys(RecognizeBy.CLASS, "whsOnd zHQkBf", userPassword);
        lab.browser[browserIndex].waitForLoad();
//        Sleeper.sleepForNSeconds("Waiting 6 sec for google mail loading", 6);
        Sleeper.sleepForNTime("Waiting 6 sec for google mail loading", 6, TimeUnit.SECONDS);
        boolean isTitleFound = lab.browser[browserIndex].getValidator().isTitleExists(userName, StringOperator.CONTAINS);
        if (isTitleFound)
            report.report("GMail page loaded successfully");
        else
            report.report("GMail page NOT loaded ", Reporter.FAIL);


    }

    /**
     * the index og mail to remove (set -1 to remove all mails)
     * (index started from 1 - for the latest mail)
     *
     * @param mailIndexToRemove
     */
    public void gMailRemoveMails(int mailIndexToRemove) {
        report.report("Deleting GMail emails ...");
        ArrayList<WebElement> allMailCheckboxes = lab.browser[browserIndex].getWebElements(RecognizeBy.XPATH, "//*[@role='checkbox']"); //.get(1).click();
        int currMailIndex = 0;
        for (WebElement currCheckbox : allMailCheckboxes) {
            if (mailIndexToRemove == -1) {
                if (currMailIndex == 0) {
                    currCheckbox.click();
                    break;
                }
            } else if (currMailIndex == mailIndexToRemove) {
                currCheckbox.click();
                break;
            }
            currMailIndex++;
        }
        ArrayList<WebElement> allMailBattons = lab.browser[browserIndex].getWebElements(RecognizeBy.XPATH, "//*[@role='button']");
        for (WebElement currButton : allMailBattons) {
            String label = currButton.getAttribute("aria-label");
            if (label != null) {
                if (label.equalsIgnoreCase("delete")) {
//                    lab.browser[browserIndex].javaScriptClickOnElement(currButton);
                    currButton.click();
                }
            }

        }

    }

    /**
     * click on indox in gmail
     */
    public void gMailBackToInbox() {
        report.report("Click to Inbox");
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CLASS, "J-Ke n0");

    }

    /**
     * open specific mail by mail's index
     *
     * @param subjectToLookFor the mail's subject to look for (staring to look from the latest one)
     * @param stringOperator   - equals, contains , start at , and other
     */
    public void gMailOpenMailBySubject(String subjectToLookFor, StringOperator stringOperator) {

        WebElement table_element = lab.browser[browserIndex].getWebElement(RecognizeBy.ID, ":29");
        ArrayList<WebElement> allGmails = (ArrayList<WebElement>) table_element.findElements(By.tagName("tr"));

        int currentIndex = 0;
        for (WebElement currMail : allGmails) {
            String currMailSubject = currMail.getText();
            report.report("Found mail: " + currMailSubject);
            if (StringUtils.stringCompare(currMailSubject, subjectToLookFor, stringOperator)) {
                report.report("opening mail indexed: " + currentIndex);
                lab.browser[browserIndex].javaScriptClickOnElement(currMail);
                break;
            }
            currentIndex++;
        }
    }

    /**
     * open specific mail by mail's index
     *
     * @param mailIndex the mail's index (started from 0)
     */
    public void gMailOpenMailByIndex(int mailIndex) {

        ArrayList<WebElement> allGmails = lab.browser[browserIndex].getElementByAndHaveAttrute(RecognizeBy.TAG_NAME, "tr", "draggable","true" );
        if (allGmails.size()>0) {
            int currentIndex = 0;
            for (WebElement currMail : allGmails) {
                report.report("Found mail: " + currMail.getText());
                if (currentIndex == mailIndex) {
                    report.report("opening mail indexed: " + currentIndex);
                    lab.browser[browserIndex].javaScriptClickOnElement(currMail);
                    break;
                }
                currentIndex++;
            }
        } else {
            report.report("Couldnt found GMail mail index !!!");
        }
    }

    public void gMailLogout() {
        report.report("Loffing out from Gmail ...");
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.CLASS, "gb_db gbii");
        lab.browser[browserIndex].clickOnElementBy(RecognizeBy.ID, "gb_71");
    }
}
