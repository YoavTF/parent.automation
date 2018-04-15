package com.cedex.automation.src.wrappers;

import com.cedex.automation.src.Lab;
import com.cedex.automation.src.waiters.TempMailReceiveWaiter;
import com.cedex.browser.BrowserBase.RecognizeBy;
import org.openqa.selenium.WebElement;

public class CedexRegistrationWrapperBrowser extends BrowserBaseWrapper {

    public CedexRegistrationWrapperBrowser(Lab lab, int browserIndex) {
        super(lab,browserIndex);
    }


    public void waitForEmailReceiving(int browserIndex, RecognizeBy recognizeBy, String identifier, WebElement refreshElement) throws Exception {
        String waitMassage="Wait for mail receiving ...";
        String completeMessage="mail received ";
        TempMailReceiveWaiter tempMailReceiveWaiter=new TempMailReceiveWaiter(lab.browser[browserIndex],recognizeBy,identifier,refreshElement,waitMassage,completeMessage);
        tempMailReceiveWaiter.waitFor(3 );
    }
}
