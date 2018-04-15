package com.cedex.automation.src.wrappers;

import com.cedex.StationUtils;
import com.cedex.automation.src.Lab;

public class WindowsActionsWrapperBrowser extends BrowserBaseWrapper {

    public WindowsActionsWrapperBrowser(Lab lab, int browserIndex) {
        super(lab, browserIndex);
    }

    /**
     * take snapshot and put it in tests current logs folder
     *
     * @throws Exception
     */
    public void takeSnapshot() throws Exception {
        String workingDir = System.getProperty("user.dir");
        String fullPath = StationUtils.appendFullChildName(workingDir, "log");
        fullPath = StationUtils.appendFullChildName(fullPath, "current");
        lab.browser[browserIndex].takeSnapshot(fullPath);
    }
}
