package com.cedex.browser;

import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.jsystem.ReporterLight;
import com.cedex.text.StringUtils;
import com.cedex.text.StringUtils.StringOperator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserValidator implements ReporterLight {
    BrowserBase browserBase;

    public BrowserValidator(BrowserBase browserBase) {
        this.browserBase = browserBase;
    }

    /**
     * validation if current browser's tab have (equels,contains , startAt and other) some title
     *
     * @param titleToLookFor
     * @return
     */
    public boolean isTitleExists(String titleToLookFor, StringOperator stringOperator) {

        String currentTitle = browserBase.getDriver().getTitle();


        report.report("Validating tilte: " + titleToLookFor);
        boolean isTitleFound = StringUtils.stringCompare(currentTitle, titleToLookFor, stringOperator);

        report.report("Title existence: " + isTitleFound);
        return isTitleFound;
    }

    public boolean isClickable(WebElement el) {
        try {
            report.report("Waiting for elelement to be clickable");
            WebDriverWait wait = new WebDriverWait(browserBase.getDriver(), 15);
            wait.until(ExpectedConditions.elementToBeClickable(el));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * validating if an element located on current page
     *
     * @param recognizeBy
     * @param identifier
     * @return
     */
    public boolean isElementPresent(RecognizeBy recognizeBy, String identifier) {
        report.report("Validating if element: " + identifier + " by: " + recognizeBy + " is presenting on the page");
        boolean isElementFound = false;
        try {
            WebElement foundWebElement = browserBase.getWebElement(recognizeBy, identifier);
            if (foundWebElement != null) {
                isElementFound = true;
            }
        } catch (NoSuchElementException e) {
            isElementFound = false;
        } finally {
            report.report("Is element found?" + isElementFound);
        }

        return isElementFound;
    }

    /**
     * validating if An Element has an attribute and its value equals to expected string
     *
     * @param recognizeBy
     * @param identifier
     * @param attributeToGet         - name of the attribute
     * @param expectedAttributeValue - expected value of the attribute
     * @param stringOperator         - comparison string action like , equals,contains startwith , etc
     * @return
     */
    public boolean isElementAttributeEqualsTo(RecognizeBy recognizeBy, String identifier, String attributeToGet, String expectedAttributeValue, StringOperator stringOperator) {
        boolean isFound = false;
        report.report("Validating if attrigute: " + attributeToGet + " of element: " + identifier + " by: " + recognizeBy + " equals to: " + expectedAttributeValue);

        WebElement foundElement = browserBase.getWebElement(recognizeBy, identifier);
        if (foundElement != null) {
            String foundAttributeValue = foundElement.getAttribute(attributeToGet);


            if (StringUtils.stringCompare(foundAttributeValue, expectedAttributeValue, stringOperator)) {

//            if (foundAttributeValue.equals(expectedAttributeValue)) {
                isFound = true;
            }
        }
        report.report("Is attribute has a value: " + isFound);
        return isFound;
    }

    /**
     * validating if An Elements Text equals/contains or other string comparison to expected text
     *
     * @param recognizeBy
     * @param identifier
     * @param expectedText
     * @param stringOperator equals.not_equals,contains ,startAt ...
     * @return
     */
    public boolean isElementTextEqualsToExpected(RecognizeBy recognizeBy, String identifier, String expectedText, StringOperator stringOperator) {
        report.report("Validating if element: " + identifier + " by: " + recognizeBy + " " + stringOperator + " to text: " + expectedText);
        boolean isAsExpected = false;
        String foundText = browserBase.getElementText(recognizeBy, identifier);
        if (StringUtils.stringCompare(foundText, expectedText, stringOperator)) {
            isAsExpected = true;
        }
        report.report("validating result is: " + isAsExpected);
        return isAsExpected;
    }

    /**
     * validation if wb alert is present over the web page
     *
     * @return
     */
    public boolean isAlertPresent() {
        try {
            browserBase.getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
