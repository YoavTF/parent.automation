package com.cedex.automation.src;

import com.cedex.api.crm.CRMApi;
import com.cedex.browser.BrowserBase;
import jsystem.framework.system.SystemObjectImpl;

public class Lab extends SystemObjectImpl {

    public BrowserBase[] browser;
    public CRMApi restApi;

    @Override
    public void init() throws Exception {
//        browser =(BrowserBase) system.getSystemObject("browser");
        super.init();

    }
}
