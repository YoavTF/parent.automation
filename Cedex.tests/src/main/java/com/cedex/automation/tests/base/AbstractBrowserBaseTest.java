package com.cedex.automation.tests.base;

import com.cedex.automation.src.wrappers.WindowsActionsWrapperBrowser;
import jsystem.framework.report.Reporter.ReportAttribute;
import org.junit.Before;

public abstract class AbstractBrowserBaseTest extends AbstractBaseTest {

    @Before
    public void getResources() throws Exception {
        report.report("-------------------- from after of AbstractBrowserBaseTest --------------------", ReportAttribute.BOLD);
        super.getResources();
        windowsActionsWrapper = new WindowsActionsWrapperBrowser(lab, browserIndex);
        takeSnapshotOnFailure();
        report.report("-------------------- END after(@After) of AbstractBrowserBaseTest --------------------", ReportAttribute.BOLD);
    }

    /**
     * taling a snapshot if test is crashed with exception
     *
     * @throws Exception
     */
    private void takeSnapshotOnFailure() throws Exception {
        if (this.failCause != null && windowsActionsWrapper != null)
            windowsActionsWrapper.takeSnapshot();
    }
}
