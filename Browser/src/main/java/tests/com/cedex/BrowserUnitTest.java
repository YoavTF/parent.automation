package tests.com.cedex;

import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.browser.ChromeBrowser;
import com.cedex.browser.EdgeBrowser;
import com.cedex.browser.FirefoxBrowser;
import com.cedex.text.StringUtils.StringOperator;
import junit.framework.SystemTestCase4;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class BrowserUnitTest extends SystemTestCase4 {

    protected FirefoxBrowser firefox;
    protected ChromeBrowser chrome;
    protected EdgeBrowser edge;

    @Before
    public void before() throws Exception {
        firefox = (FirefoxBrowser) system.getSystemObject("firefox");
        edge = (EdgeBrowser) system.getSystemObject("edge");
        chrome = (ChromeBrowser) system.getSystemObject("chrome");
    }


    @Test

    public void getValueFromTable() throws Exception {
        firefox.navigate("https://group.techfinancials.com/share-price/ ");
        firefox.getValidator().isElementAttributeEqualsTo(RecognizeBy.CLASS, "stock_price", "b", "0", StringOperator.EQUALS);
//        firefox.getTableCellValue(2,1);
    }
    @Test
    public void firefoxTest() throws Exception {

        firefox.get();
        firefox.takeSnapshot();
        firefox.refresh(true);
        firefox.clickOnUrl("/videos");
        firefox.closeBrowser();
        firefox.get();

    }

    @Test
    public void chromeTest() throws Exception {

        chrome.get();
        chrome.clickOnUrl("/registration");
        chrome.sendKeys(RecognizeBy.CLASS, "cedex-input", "Roman");
        chrome.clickOnElementBy(RecognizeBy.ID, "checkbox_terms");

    }

    @Test
    public void iexplorerTest() throws Exception {

        edge.get();
        edge.clickOnUrl("/registration");

    }

    @Test
    public void videosTest() throws Exception {

        chrome.get();
        chrome.navigate("http://google.com");
        chrome.clickOnElementBy(RecognizeBy.ID, "link_videos");


//        WebElement elm = chrome.getWebElement(RecognizeBy.ID,"");
//        String urlStr = elm.getAttribute("src");
//        System.out.println("Video Url : " + urlStr);
//        driver.navigate().to(urlStr);
//        cdrv.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//
//        JavascriptExecutor jse = (JavascriptExecutor) driver;
////Click on play button
//        jse.executeScript("jwplayer().play();");
//        Thread.sleep(2000);
////Pause
//        jse.executeScript("jwplayer().pause()");
//        Thread.sleep(2000);

        List<WebElement> myList = chrome.getDriver().findElements(By.className("wrapper"));

//        //myList contains all the web elements
//        //if you want to get all elements text into array list
        List<String> all_elements_text = new ArrayList<>();

        for (int i = 0; i < myList.size(); i++) {

            //loading text of each element in to array all_elements_text
            all_elements_text.add(myList.get(i).getText());

            //to print directly
            System.out.println(myList.get(i).getText());

        }


    }
}



