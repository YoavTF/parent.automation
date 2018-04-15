package com.cedex.automation.src.wrappers;

import com.cedex.automation.src.Lab;

public abstract class BrowserBaseWrapper extends BaseWrapper {
    protected int browserIndex;

    public BrowserBaseWrapper(Lab lab, int browserIndex) {
        super(lab);
        this.browserIndex = browserIndex;

    }


}
