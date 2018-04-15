package com.cedex.automation.tests.actions;

import com.cedex.automation.tests.base.AbstractBrowserBaseTest;
import com.cedex.text.StringUtils.StringOperator;
import jsystem.framework.TestProperties;
import org.junit.Test;

public class WindowActions extends AbstractBrowserBaseTest {


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    private int tabIndex = 0;
    private String titleToLookFor;
    private boolean waitForPageReload = true;
    private StringOperator stringOperator= StringOperator.EQUALS;

//    private WindowsActionsWrapperBrowser windowsActionsWrapper;

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
//    @Before
//    public void before() {
//        windowsActionsWrapper = new WindowsActionsWrapperBrowser(lab, browserIndex);
//
//    }

    @Test
    @TestProperties(name = "BB: Delete all cookies", paramsExclude = {"titleToLookFor", "tabIndex", "waitForPageReload","stringOperator"}, returnParam = {})
    public void deleteAllCookies() {
        lab.browser[browserIndex].deleteAllCookies();
    }


    @Test
    @TestProperties(name = "BB: Close all tabs except the main", paramsExclude = {"titleToLookFor", "tabIndex", "waitForPageReload","stringOperator"}, returnParam = {})
    public void closeOtherTabs() throws Exception {
        lab.browser[browserIndex].closeOtherTabs();
    }

    @Test
    @TestProperties(name = "BB: Close Browser window", paramsExclude = {"titleToLookFor", "tabIndex", "waitForPageReload","stringOperator"}, returnParam = {})
    public void closeBrowser() throws Exception {
        lab.browser[browserIndex].closeBrowser();
    }

    @Test
    @TestProperties(name = "BB: Refresh Browser window", paramsExclude = {"titleToLookFor", "tabIndex","stringOperator"}, returnParam = {})
    public void refresh() throws Exception {
        lab.browser[browserIndex].refresh(waitForPageReload);
    }

    /**
     * navigate back to previous page
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: navigate back", paramsExclude = {"titleToLookFor", "tabIndex", "waitForPageReload","stringOperator"}, returnParam = {})
    public void back() throws Exception {
        lab.browser[browserIndex].getDriver().navigate().back();
    }

    /**
     * navigate forward to next page
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: navigate forward", paramsExclude = {"titleToLookFor", "tabIndex", "waitForPageReload","stringOperator"}, returnParam = {})
    public void forward() throws Exception {
        lab.browser[browserIndex].getDriver().navigate().forward();
    }

    @Test
    @TestProperties(name = "BB: Wait for page loaded", paramsExclude = {"titleToLookFor", "tabIndex", "waitForPageReload","stringOperator"}, returnParam = {})
    public void waitForPageLoad() throws Exception {
        lab.browser[browserIndex].waitForLoad();
    }

    @Test
    @TestProperties(name = "BB: Select tab by index: ${tabIndex}", paramsExclude = {"titleToLookFor", "waitForPageReload","stringOperator"}, returnParam = {})
    public void selectTabByIndex() throws Exception {
//        Assert.assertNotNull(tabIndex);
        lab.browser[browserIndex].selectTabByTabIndex(tabIndex);
    }

    @Test
    @TestProperties(name = "BB: Select tab by title: ${titleToLookFor}", paramsExclude = {"tabIndex", "waitForPageReload"}, returnParam = {})
    public void selectTabByTitle() throws Exception {
//        Assert.assertNotNull(titleToLookFor);
        lab.browser[browserIndex].selectTabByTitle(titleToLookFor,stringOperator);
    }

    @Test
    @TestProperties(name = "BB: Take snapshot", paramsExclude = {"tabIndex", "titleToLookFor", "waitForPageReload","stringOperator"}, returnParam = {})
    public void takeSnapshot() throws Exception {
//        Assert.assertNotNull(titleToLookFor);
        windowsActionsWrapper.takeSnapshot();

    }
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * Tab index to activate for (starting from 0)
     *
     * @param tabIndex
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getTitleToLookFor() {
        return titleToLookFor;
    }

    /**
     * Tab title (or part of title) to activate for
     *
     * @param titleToLookFor
     */
    public void setTitleToLookFor(String titleToLookFor) {
        this.titleToLookFor = titleToLookFor;
    }

    public boolean isWaitForPageReload() {
        return waitForPageReload;
    }

    public void setWaitForPageReload(boolean waitForPageReload) {
        this.waitForPageReload = waitForPageReload;
    }

    public StringOperator getStringOperator() {
        return stringOperator;
    }

    /**
     * String Operator may be: EQUALS,CONTAINS ,START_AT and etc ...
     * @param stringOperator
     */
    public void setStringOperator(StringOperator stringOperator) {
        this.stringOperator = stringOperator;
    }
}
