package com.cedex.browser.waiter;

import com.cedex.browser.BrowserBase;
import com.cedex.browser.BrowserBase.RecognizeBy;

public class WebElementPresentWaiter extends WaiterBaseImpl {


    /**
     * @param browser         - base browser to execute commands on
     * @param waitForScenarioMessage - scenario name
     * @param lastMessage         - last message after waiter stopped (OK and FAIL message)
     * @throws Exception
     */
    public WebElementPresentWaiter(BrowserBase browser, RecognizeBy recognizeBy, String identifier,String waitForScenarioMessage, String lastMessage) throws Exception {
        super(browser,recognizeBy,identifier, waitForScenarioMessage, lastMessage);

    }

    @Override
    public boolean isLoopStopCondition() throws Exception {

        boolean isLoopCondition=false;

        try {
           boolean isElementFound=getBrowser().getValidator().isElementPresent(recognizeBy, identifier);
            if (isElementFound) {
                isLoopCondition=true;
            }
        } catch (Exception e) {
            report.report("Element: "+identifier+" by: "+recognizeBy+" not found yet");
        }

        report.report("Element: "+identifier+" by: "+recognizeBy+" Found ");
        return isLoopCondition;
    }
}
