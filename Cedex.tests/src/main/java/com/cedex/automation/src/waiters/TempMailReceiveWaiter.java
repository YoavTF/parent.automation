package com.cedex.automation.src.waiters;

import com.cedex.browser.BrowserBase;
import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.browser.waiter.WaiterBaseImpl;
import org.openqa.selenium.WebElement;

public class TempMailReceiveWaiter extends WaiterBaseImpl {
     WebElement refreshButton;
    /**
     * @param browser                - base browser to execute commands on
     * @param recognizeBy
     * @param identifier
     * @param waitForScenarioMessage - scenario name
     * @param lastMessage            - OK and FAIL lastMessage
     * @throws Exception
     */
    public TempMailReceiveWaiter(BrowserBase browser, RecognizeBy recognizeBy, String identifier, WebElement refreshButton,String waitForScenarioMessage, String lastMessage) throws Exception {
        super(browser, recognizeBy, identifier, waitForScenarioMessage, lastMessage);
        this.refreshButton=refreshButton;
    }

    @Override
    public boolean isLoopStopCondition() throws Exception {
        boolean isMailFound=browser.getValidator().isElementPresent(recognizeBy, identifier);
        if (isMailFound) {
            report.report("mail received ");
            return true;
        } else {
            report.report("refreshing ...");
            refreshButton.click();
        }
        return false;
    }
}
