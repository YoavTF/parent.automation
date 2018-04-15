package com.cedex.browser;

import com.cedex.GlobalParameters;
import com.cedex.StationUtils;
import com.cedex.browser.waiter.WebElementPresentWaiter;
import com.cedex.os.OS;
import com.cedex.text.StringUtils;
import com.cedex.text.StringUtils.StringOperator;
import com.cedex.time.CurrentTime;
import jsystem.framework.report.Reporter;
import jsystem.framework.report.ReporterHelper;
import jsystem.framework.system.SystemObjectImpl;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class BrowserBase extends SystemObjectImpl {


    private String driverFullPath = "C:\\tmp";
    protected String url;
    protected WebDriver driver;
    protected String driverShortName;
    protected boolean isOpened = false;

    protected BrowserValidator validator;

    public static enum RecognizeBy {
        ID, CLASS, XPATH, TEXT, TAG_NAME, NAME, CSS_SELECTOR, LINK_TEST, PATIAL_LINK_TEXT,
        //Input type <button type="submit">Login</button>
        TYPE;
    }

    /**
     * The init() method will be called by JSystem after the instantiation of
     * the system object. <br>
     * This can be a good place to assert that all the members that we need were
     * defined in the SUT file.
     */
    public void init() throws Exception {
        super.init();
        Assert.assertNotNull("Please define the Browser URL in the SUT file", url);
        validator = new BrowserValidator(this);

    }


    protected abstract void openBrowser() throws Exception;

    protected String getFullPathSeleniumToDriver() throws Exception {
        String workingDir = System.getProperty("user.dir");
        String parentFolder = StationUtils.getParentFolder(workingDir);
        String fullPath = StationUtils.appendFullChildName(parentFolder, "runner");
        fullPath = StationUtils.appendFullChildName(fullPath, "tools");
        fullPath = StationUtils.appendFullChildName(fullPath, "webdriver");
        if (OS.isWin()) {
            fullPath = StationUtils.appendFullChildName(fullPath, "win");
            fullPath = StationUtils.appendFullChildName(fullPath, driverShortName);
        } else {
            fullPath = StationUtils.appendFullChildName(fullPath, "linux");
            //TODO: NOT COMPLETED GECO DRIVER FOR LINUX
            fullPath = StationUtils.appendFullChildName(fullPath, driverShortName);
        }
        return fullPath;
    }

    /**
     * close browser window
     *
     * @throws Exception
     */
    public void closeBrowser() throws Exception {
        report.report("Closing browser ...");
        this.isOpened = false;
        this.getDriver().close();
    }

    public void sendKeys(RecognizeBy recognizeBy, String identifier, String keysToSend) {
        WebElement foundWebElement = this.getWebElement(recognizeBy, identifier);
        report.report("sending keys: " + keysToSend + " element: " + identifier + " by: " + recognizeBy);
        if (foundWebElement != null)
            foundWebElement.sendKeys(keysToSend + Keys.RETURN);
        else {
            report.report("Element: " + identifier + " by: " + recognizeBy + " not found");
        }
    }

    public void get() throws Exception {
        get(url);
    }

    /**
     * navigate to base url page
     *
     * @throws Exception
     */
    public void navigate() throws Exception {
        this.get();
    }

    private void get(String urlToNavigate) throws Exception {
        report.report("Navigate to page: " + urlToNavigate);
        if (!isOpened) {
            openBrowser();
            isOpened = true;
        }
        driver.get(urlToNavigate);
    }

    public void navigate(String url) throws Exception {
        this.get(url);
    }

    /**
     * Click on Google recaptcha checkbox (support only the simple one without the image)
     *
     * @throws InterruptedException
     */
    public void clickOnGoogleRecaptcha() throws InterruptedException, IOException {
        report.startLevel("Click on google reCaptcha");
        try {
            waitForLoad();
            ArrayList<WebElement> divs = getWebElements(BrowserBase.RecognizeBy.TAG_NAME, "div");
            for (WebElement currDiv : divs) {
                String currClassName = currDiv.getAttribute("class");
                report.report("div class: " + currClassName);
                if (currClassName.contains("recaptcha")) {
                    currDiv.click();
                    break;
                }
            }
        } catch (Exception e) {
            report.report("got exception: " + e.getMessage() + " " + e.getStackTrace());
        } finally {
            report.stopLevel();
        }
    }

    /**
     * refreshe a page
     *
     * @param waitForPageReload wait for page reloading if true
     */
    public void refresh(boolean waitForPageReload) {
        report.report("Refreshing a page ...");
        this.getDriver().navigate().refresh();
        if (waitForPageReload) {
            report.report("Waiting for page reloading ...");
            waitForLoad();
        }
    }


    /**
     * this function closing or accepring and alert and returns its test
     *
     * @param acceptNextAlert
     * @return
     */
    public String closeAlertAndGetItsText(boolean acceptNextAlert) {
        try {
            Alert alert = getDriver().switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }


    /**
     * get Text from table's Cell (relevant only if there is only one table par page)
     *
     * @param rowIndex  - index of the row (started from 0)
     * @param collIndex - index of the Column (started from 0)
     * @return
     */
    public String getTableCellValue(int rowIndex, int collIndex) {
        String foundText = GlobalParameters.notFound;
        report.report("Looking for text in Cell (" + rowIndex + ":" + collIndex + ")");
//        WebElement table_element = driver.findElement(By.xpath("//table//tbody"));
        WebElement table_element = getWebElement(RecognizeBy.XPATH, "//table//tbody");
        ArrayList<WebElement> rows = (ArrayList<WebElement>) table_element.findElements(By.tagName("tr"));
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            ArrayList<WebElement> cells = (ArrayList<WebElement>) row.findElements(By.tagName("th"));
            if (cells.size() == 0) {
                cells = (ArrayList<WebElement>) row.findElements(By.tagName("td"));
            }
            for (int j = 0; j < cells.size(); j++) {
                WebElement cell = cells.get(j);
                System.out.println(cell.getText());
                if (i == rowIndex && j == collIndex) {
                    foundText = cell.getText();
                    break;
                }
            }
        }
        report.report("Found text is: " + foundText);
        return foundText;
    }


    /**
     * Found WebElement with multiple class names:
     * example: <div class="stepIcon stepIncrease" ></div>
     *
     * @param doubleClassIdentifier
     * @return
     */
    private ArrayList<WebElement> getElementByClassDoubleName(String doubleClassIdentifier) {
        String[] doubleClassIdentifierArr = doubleClassIdentifier.split(" ");
        ArrayList<WebElement> founds = getWebElements(BrowserBase.RecognizeBy.CLASS, doubleClassIdentifierArr[0]);
//        WebElement foundWebElement = null;
        ArrayList<WebElement> listOfFoundElements = new ArrayList<>();
        for (WebElement currFound : founds) {
            String currClassName = currFound.getAttribute("class");
            if (currClassName.equals(doubleClassIdentifier)) {
//                foundWebElement = currFound;
                listOfFoundElements.add(currFound);
//                break;

            }
        }
        return listOfFoundElements;
    }

    /**
     * click on image URl :
     * element like:
     * <img src="./img/editable.png" class="w1 dib pointer">
     *
     * @param relativeImageUrl = value of src attrigute
     */
    public void clickOnUrlImage(String relativeImageUrl) {
        report.report("Click on Image Url ...");
        boolean isImageFound = false;
        ArrayList<WebElement> listOfImages = getWebElements(RecognizeBy.TAG_NAME, "img");
        for (WebElement currImage : listOfImages) {
            if (currImage.getAttribute("src").equals(relativeImageUrl)) {
                currImage.click();
                isImageFound = true;
                break;
            }
        }
        report.report("Click on image: " + isImageFound);
    }

    /**
     * click on url by its text (if url is dynamic)
     *
     * @param urlText - urls text may be equals or contains
     */
    public void clickOnUrlByText(String urlText) {
        List<WebElement> anchors = driver.findElements(By.tagName("a"));
        Iterator<WebElement> i = anchors.iterator();

        while (i.hasNext()) {
            WebElement anchor = i.next();
            String attribute = anchor.getAttribute("href");
            String elementText = anchor.getText();
            if (attribute != null && elementText.contains(elementText)) {
                anchor.click();
                break;
            }
        }
        waitForLoad();
    }


    public void clickOnUrlInTempMail(String relativeUrl) throws Exception {
        List<WebElement> anchors = driver.findElements(By.tagName("a"));
        Iterator<WebElement> i = anchors.iterator();

        while (i.hasNext()) {
            WebElement anchor = i.next();
            String attributeUrl = anchor.getAttribute("href");
            report.report("href=" + attributeUrl);
            if (attributeUrl != null && attributeUrl.contains(relativeUrl)) {
                int foundIdex = attributeUrl.indexOf(relativeUrl);
                String subUrl = attributeUrl.substring(foundIdex, attributeUrl.length());
                navigate(subUrl);
                break;
            }
        }
        waitForLoad();
    }


    /**
     * click on URL if url is static and known
     * you can put full url: http://cedex.com
     * , relative url : /registration
     * or partial url http://cedex.com/reg
     *
     * @param relativeUrl
     */
    public void clickOnUrl(String relativeUrl) {
        if (!relativeUrl.startsWith("/") &&
                !relativeUrl.startsWith("http://") &&
                !relativeUrl.startsWith("https://")) {
            relativeUrl = "/" + relativeUrl;
        }

        String xpath = "//a[@href='" + relativeUrl + "']";
        WebElement foundWebElement = getWebElement(RecognizeBy.XPATH, xpath);
        if (foundWebElement != null) {
            foundWebElement.click();
        } else {
            List<WebElement> anchors = driver.findElements(By.tagName("a"));
            Iterator<WebElement> i = anchors.iterator();

            while (i.hasNext()) {
                WebElement anchor = i.next();
                String attribute = anchor.getAttribute("href");
                report.report("href=" + attribute);
                if (attribute != null && attribute.contains(relativeUrl)) {
                    anchor.click();
                    break;
                }
            }
        }
        waitForLoad();
    }

    /**
     * return sub (child) WebElement of the main (parent) WebElement
     * like Return Element b:
     * <p class="stock_price">Last Trade Price:
     * <b>16.5000</b>
     * </p>
     *
     * @param parentRecognizeBy
     * @param parentIdentifier
     * @param childRecognizeBy
     * @param childIdentifier
     * @return
     */
    public WebElement getChildElement(RecognizeBy parentRecognizeBy, String parentIdentifier, RecognizeBy childRecognizeBy, String childIdentifier) {
        WebElement parentElement = getWebElement(parentRecognizeBy, parentIdentifier);
        WebElement child;
        report.report("looking for Child's " + childIdentifier + " by : " + childRecognizeBy + " text");
        switch (childRecognizeBy) {
            case ID: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
                child = (WebElement) parentElement.findElement(By.id(childIdentifier));
                break;
            }
            case XPATH: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
                child = (WebElement) parentElement.findElement(By.xpath(childIdentifier));
                break;
            }
            case TEXT: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.linkText(identifier)));
                child = (WebElement) parentElement.findElement(By.linkText(childIdentifier));
                break;
            }
            case NAME: {
                child = (WebElement) parentElement.findElement(By.name(childIdentifier));
                break;
            }
            case CSS_SELECTOR: {
                child = (WebElement) parentElement.findElement(By.cssSelector(childIdentifier));
                break;
            }
            case LINK_TEST: {
                child = (WebElement) parentElement.findElement(By.linkText(childIdentifier));
                break;
            }
            case TAG_NAME: {
                child = (WebElement) parentElement.findElement(By.tagName(childIdentifier));
                break;
            }
            case PATIAL_LINK_TEXT: {
                child = (WebElement) parentElement.findElement(By.partialLinkText(childIdentifier));
                break;
            }
            case TYPE: {
                child = (WebElement) parentElement.findElement(By.xpath("//input[@type='" + childIdentifier + "']"));
                break;
            }
            default: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.className(identifier)));
//                if (identifier.contains(" ")) {
//                    foundElements = getElementByClassDoubleName(childIdentifier);
//                } else {
                child = (WebElement) parentElement.findElement(By.className(childIdentifier));
//                }


            }
        }

//        WebElement child = parentElement.findElement(By.tagName("b"));
        String childText = child.getText();
        report.report("found child's text: " + childText);
        return child;
    }

    /**
     * get list of webElements - good for elements from tables
     *
     * @param recognizeBy
     * @param identifier
     * @return
     */
    public ArrayList<WebElement> getWebElements(RecognizeBy recognizeBy, String identifier) {
        ArrayList<WebElement> foundElements = new ArrayList<>();
        waitForLoad();
        report.report("looking for element: " + identifier + " recognized by: " + recognizeBy);
        switch (recognizeBy) {
            case ID: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.id(identifier)));
                foundElements = (ArrayList<WebElement>) driver.findElements(By.id(identifier));
                break;
            }
            case XPATH: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier)));
                foundElements = (ArrayList<WebElement>) driver.findElements(By.xpath(identifier));
                break;
            }
            case TEXT: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.linkText(identifier)));
                foundElements = (ArrayList<WebElement>) driver.findElements(By.linkText(identifier));
                break;
            }
            case NAME: {
                foundElements = (ArrayList<WebElement>) driver.findElements(By.name(identifier));
                break;
            }
            case CSS_SELECTOR: {
                foundElements = (ArrayList<WebElement>) driver.findElements(By.cssSelector(identifier));
                break;
            }
            case LINK_TEST: {
                foundElements = (ArrayList<WebElement>) driver.findElements(By.linkText(identifier));
                break;
            }
            case TAG_NAME: {
                foundElements = (ArrayList<WebElement>) driver.findElements(By.tagName(identifier));

                break;
            }
            case PATIAL_LINK_TEXT: {
                foundElements = (ArrayList<WebElement>) driver.findElements(By.partialLinkText(identifier));
                break;
            }
            case TYPE: {
                foundElements = (ArrayList<WebElement>) driver.findElements(By.xpath("//input[@type='" + identifier + "']"));
                break;
            }
            default: {
//                wait.until(ExpectedConditions.elementToBeClickable(By.className(identifier)));
                if (identifier.contains(" ")) {
                    foundElements = getElementByClassDoubleName(identifier);
                } else {
                    foundElements = (ArrayList<WebElement>) driver.findElements(By.className(identifier));
                }


            }
        }
        return foundElements;
    }

    public WebElement getWebElement(RecognizeBy recognizeBy, String identifier) {

        WebElement foundElement = null;
        ArrayList<WebElement> foundWebElementsList = getWebElements(recognizeBy, identifier);
        if (foundWebElementsList.size() > 0) {
            foundElement = foundWebElementsList.get(0);
        }

        return foundElement;


    }

    /**
     * create page snapshot
     *
     * @return
     * @throws Exception
     */
    public String takeSnapshot() throws Exception {
        String workingDir = System.getProperty("user.dir");
        String fullPath = StationUtils.appendFullChildName(workingDir, "log");
        fullPath = StationUtils.appendFullChildName(fullPath, "current");
        return takeSnapshot(fullPath);
    }

    /**
     * create page snapshot
     *
     * @param folderFullPath - full path of folder to put the snapshot in
     * @return
     * @throws Exception
     */
    public String takeSnapshot(String folderFullPath) throws Exception {
//        report.report("Creating snapshot ...");
        TakesScreenshot tss = (TakesScreenshot) driver;

        File srcfileObj = tss.getScreenshotAs(OutputType.FILE);
        String ts = CurrentTime.now("dd-MM-yyyy HH_mm_ss").replaceAll(" ", "_");
        String fileFullPath = StationUtils.appendFullChildName(folderFullPath, "SNAPSHOT_" + ts + ".jpg");//".png");
        report.report("Creating snapshot: " + fileFullPath);
        File DestFileObj = new File(fileFullPath);
        FileUtils.copyFile(srcfileObj, DestFileObj);
//        report.addLink("Snapshot file", fileFullPath);
        ReporterHelper.copyFileToReporterAndAddLink(report, new File(fileFullPath), "Snapshot File ");
        return fileFullPath;

    }

    /**
     * remove all stored cookies
     */
    public void deleteAllCookies() {
        report.report("Deleting all cookies ...");
        driver.manage().deleteAllCookies();
    }

    /**
     * executing any javascript commmads
     *
     * @param jsCommand
     */
    public void javaScriptCommandExecute(String jsCommand) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        //Click on play button
        jse.executeScript(jsCommand);
    }

    public void scrollToElementBy(RecognizeBy recognizeBy, String identifier) {
        WebElement elementToClick = getWebElement(recognizeBy, identifier);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(elementToClick);
        actions.perform();
    }

    /**
     * scroll down into the element
     */
    public void scrollInElementBy(RecognizeBy recognizeBy, String identifier, int scroll_count) {
        WebElement elementToClick = getWebElement(recognizeBy, identifier);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollTop = arguments[1];", elementToClick, scroll_count);
    }

    /**
     * @param elementToClickOn //     * @param enableElement - if element is disable , enable it bofore the click
     */
    public void javaScriptClickOnElement(WebElement elementToClickOn) {
//        WebElement ele = getWebElement(recognizeBy, identifier);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
//        if (enableElement) {
//            executor.executeAsyncScript("arguments[0].removeAttribute('disabled','disabled')",elementToClickOn);
//        }
        executor.executeScript("arguments[0].click();", elementToClickOn);
    }

    /**
     * return a text of en Element
     *
     * @param recognizeBy
     * @param identifier
     * @return
     */
    public String getElementText(RecognizeBy recognizeBy, String identifier) {
        report.report("get text of an Element: " + identifier + " by: " + recognizeBy);
        String foundText = GlobalParameters.notFound;
        WebElement elem = getWebElement(recognizeBy, identifier);
        if (elem != null)
            foundText = elem.getText();

        report.report("Found text is: " + foundText);
        return foundText;
    }

    /**
     * click on element from javascript
     *
     * @param recognizeBy
     * @param identifier
     */
    public void javaScriptClickOnElement(RecognizeBy recognizeBy, String identifier) {
        WebElement elementToClick = getWebElement(recognizeBy, identifier);
        javaScriptClickOnElement(elementToClick);
    }

    /**
     * select value from drowdown list
     *
     * @param recognizeBy
     * @param identifier
     * @param valueToSelect
     */
    public void selectDropdownListValue(RecognizeBy recognizeBy, String identifier, String valueToSelect) {
        Select select = new Select(getWebElement(recognizeBy, identifier));
        select.selectByValue(valueToSelect);
    }

    /**
     * waiting for page load completion
     */
    public void waitForLoad() {
        report.report("Wait for pege loading ...");
        try {
            ExpectedCondition<Boolean> pageLoadCondition = new
                    ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                        }
                    };
            WebDriverWait wait = new WebDriverWait(getDriver(), 30);
            wait.until(pageLoadCondition);
        } catch (Exception e) {
            report.report("Failed to wait for page load ....");
        } finally {
            report.report("Page load complete!");
        }
    }

    /**
     * click on all elements with tha same identifier
     *
     * @param recognizeBy
     * @param identifier
     */
    public void clickOnElementsAllBy(RecognizeBy recognizeBy, String identifier) {

        clickOnElementsBy(recognizeBy, identifier, false);


    }

    /**
     * click on element only if RecognizeBY + idetifier and attribute have expected value
     * (some objectes have the same RecognizeBY + idetifier and to distinguish between added attibute and its value
     *
     * @param recognizeBy
     * @param identifier
     * @param attribute
     * @param expectedAttibuteValue
     */
    public ArrayList<WebElement> getElementByAndHaveAttrute(RecognizeBy recognizeBy, String identifier, String attribute, String expectedAttibuteValue) {
        report.report("Click on Element " + identifier + " by: " + recognizeBy + " and attibute: " + attribute + " have value: " + expectedAttibuteValue);
        WebElement foundElement = null;
        ArrayList<WebElement> foundAllElements = getWebElements(recognizeBy, identifier);
        ArrayList<WebElement> foundElementsWithAttribute = new ArrayList<>();
        for (WebElement currWebElement : foundAllElements) {
            String currAttributeValue = currWebElement.getAttribute(attribute);
            if (currAttributeValue != null) {
                if (currAttributeValue.equals(expectedAttibuteValue)) {
                    foundElementsWithAttribute.add(currWebElement);
                }
            }
        }

        return foundElementsWithAttribute;
    }

    /**
     * click on element only if RecognizeBY + idetifier and attribute have expected value
     * (some objectes have the same RecognizeBY + idetifier and to distinguish between added attibute and its value
     *
     * @param recognizeBy
     * @param identifier
     * @param attribute
     * @param expectedAttibuteValue
     */
    public void clickOnElementByAndHaveAttrute(RecognizeBy recognizeBy, String identifier, String attribute, String expectedAttibuteValue) {
        report.report("Click on Element " + identifier + " by: " + recognizeBy + " and attibute: " + attribute + " have value: " + expectedAttibuteValue);
        WebElement foundElement = null;
//        ArrayList<WebElement> foundElements = getWebElements(recognizeBy, identifier);
        ArrayList<WebElement> foundElements = getElementByAndHaveAttrute(recognizeBy, identifier, attribute, expectedAttibuteValue);
        if (foundElements != null && !foundElements.isEmpty()) {
            foundElement = foundElements.get(0);
        }
//        for (WebElement currWebElement : foundElements) {
//            String currAttributeValue = currWebElement.getAttribute(attribute);
//            if (currAttributeValue.equals(expectedAttibuteValue)) {
//                foundElement = currWebElement;
//                break;
//            }
//        }
        if (foundElement != null) {
            report.report("Element found , click on it ...");
            foundElement.click();
        }
    }

    private void clickOnElementsBy(RecognizeBy recognizeBy, String identifier, boolean onlyTheFirst) {

        ArrayList<WebElement> foundElements = getWebElements(recognizeBy, identifier);
        for (WebElement currWebElement : foundElements) {

            if (currWebElement != null) {
                report.report("Click on Element: " + identifier + " by: " + recognizeBy.toString());
                if (validator.isClickable(currWebElement)) {
                    currWebElement.click();
                } else {
                    javaScriptClickOnElement(currWebElement);
//                    Actions actions = new Actions(getDriver());
//                    actions.moveToElement(currWebElement).click().perform();
                    report.report("click on Element: " + identifier + " by: " + recognizeBy.toString() + " even its not clickable");
                }
                if (onlyTheFirst)
                    break;
            } else {
                report.report("Element: " + identifier + " by: " + recognizeBy + " not found !!!", Reporter.WARNING);
            }
        }


    }


    /**
     * wait for web element presence on the page up to time defined by param: maxTimeToWaitInSeconds
     *
     * @param recognizeBy
     * @param identifier
     * @param maxTimeToWaitInSeconds
     * @throws Exception
     */
    public void waitForElementPresent(RecognizeBy recognizeBy, String identifier, int maxTimeToWaitInSeconds) throws Exception {
        WebElementPresentWaiter webElementPresentWaiter = new WebElementPresentWaiter(this, recognizeBy, identifier,
                "Wait for an element: " + identifier + " by: " + recognizeBy, "Element found");
        webElementPresentWaiter.waitFor(maxTimeToWaitInSeconds);
    }

    /**
     * click on element that matching the identifier
     * note: if there are more elements , click on the firs one only
     *
     * @param recognizeBy
     * @param identifier
     */
    public void clickOnElementBy(RecognizeBy recognizeBy, String identifier) {
        this.clickOnElementsBy(recognizeBy, identifier, true);
    }


    /**
     * closing tabs of other tabs and don't touch the current tab
     */
    public void closeOtherTabs() {
        String currentTabTitle = driver.getTitle();

//        for (int i = driver.getWindowHandles().size() - 1; i > 0; i--) {
        for (int i = 0; i < driver.getWindowHandles().size(); i++) {
            String winHandle = driver.getWindowHandles().toArray()[i].toString();
            driver.switchTo().window(winHandle);
            if (!driver.getTitle().equals(currentTabTitle)) {
                report.report("Closing tab with title: " + driver.getTitle());
                driver.close();
            }
        }
    }

    /**
     * open URL in a new TAB
     *
     * @param urlToNavigate
     */
    public void getInNewTab(String urlToNavigate) throws Exception {

        getInNewTab(urlToNavigate, false);
    }

    /**
     * open URL in a new TAB
     *
     * @param urlToNavigate
     */
    public void getInNewTab(String urlToNavigate, boolean stayInNewPage) throws Exception {

        driver.manage().window().maximize();
        String currUrl = driver.getCurrentUrl();
//

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('" + urlToNavigate + "','_blank');");

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        if (!stayInNewPage) {
            driver.switchTo().window(tabs.get(0)); // switch back to main screen
            navigate(currUrl);
        } else {
            driver.switchTo().window(tabs.get(tabs.size() - 1));
            navigate(urlToNavigate);
        }


    }

    /**
     * Select a value from dropDown / comboBox web element by value of element
     * only using if en element from Select type (dropDown list)
     *
     * @param recognizeBy
     * @param identifier
     * @param valueToSelect the value from list to select
     */

    public void selectDropDownListElement(RecognizeBy recognizeBy, String identifier, String valueToSelect) {
        Select dropdown = new Select(getWebElement(recognizeBy, identifier));
        dropdown.selectByValue(valueToSelect);
    }

    /**
     * Select a value from dropDown / comboBox web element by index of element
     * *  only using if en element from Select type (dropDown list)
     *
     * @param recognizeBy
     * @param identifier
     * @param indexToSelect
     */
    public void selectDropDownListElement(RecognizeBy recognizeBy, String identifier, int indexToSelect) {
        Select dropdown = new Select(getWebElement(recognizeBy, identifier));
        dropdown.selectByIndex(indexToSelect);
    }

    /**
     * set focus on element
     *
     * @param recognizeBy
     * @param identifier
     */
    public void setFocusOnElementBy(RecognizeBy recognizeBy, String identifier) {
        report.report("Set focus on element: " + identifier + " by: " + recognizeBy);
        WebElement foundElement = this.getWebElement(recognizeBy, identifier);
        if ("input".equals(foundElement.getTagName())) {
            foundElement.sendKeys("");
        } else {
            new Actions(driver).moveToElement(foundElement).perform();

        }
    }

    /**
     * selecting Tab by tab index
     *
     * @param tabIndex
     * @throws Exception
     */
    public void selectTabByTabIndex(int tabIndex) throws Exception {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        if (tabs.size() <= tabIndex) {
            int maxPossible = tabs.size() - 1;
            throw new Exception("Received invalid Tab index: " + tabIndex + " possible tab indexes are: 0-" + maxPossible);
        }
        report.report("activete tab: " + tabIndex);
        driver.switchTo().window(tabs.get(tabIndex));
    }

    /**
     * selecting Tab by title string
     *
     * @param lookingForTitle
     */
    public void selectTabByTitle(String lookingForTitle, StringOperator stringOperator) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        report.report("looking for tab with title: " + stringOperator + " to: " + lookingForTitle);
        for (String currTab : tabs) {
            driver.switchTo().window(currTab); // switch back to main screen
            String currTitle = driver.getTitle();
            if (StringUtils.stringCompare(currTitle, lookingForTitle, stringOperator)) {
//            if (currTitle.contains(lookingForTitle)) {
                report.report("tab: " + lookingForTitle + " found");
                break;
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public WebDriver getDriver() {
        return driver;
    }

    public String getDriverFullPath() {
        return driverFullPath;
    }

    public void setDriverFullPath(String driverFullPath) {
        this.driverFullPath = driverFullPath;
    }

    public BrowserValidator getValidator() {
        return validator;
    }

    public void setValidator(BrowserValidator validator) {
        this.validator = validator;
    }
}
