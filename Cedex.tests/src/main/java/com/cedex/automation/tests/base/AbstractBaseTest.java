package com.cedex.automation.tests.base;


import com.cedex.OsLocal;
import com.cedex.automation.src.Lab;
import com.cedex.automation.src.wrappers.WindowsActionsWrapperBrowser;
import com.cedex.automation.src.result.ResultInterface;
import com.cedex.automation.src.result.ResultManager;
import com.cedex.automation.src.run.AntProperties;
import com.cedex.automation.tests.actions.utils.ExecutionSummary;
import com.cedex.fortests.SutUtils;
import com.cedex.text.RegExpr;
import jsystem.framework.RunProperties;
import jsystem.framework.RunnerStatePersistencyManager;
import jsystem.framework.report.Reporter;
import jsystem.framework.report.Reporter.ReportAttribute;
import jsystem.framework.scenario.JTest;
import jsystem.framework.scenario.Scenario;
import jsystem.framework.scenario.ScenariosManager;
import junit.framework.SystemTestCase4;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

import static com.cedex.GlobalParameters.notFound;

public abstract class AbstractBaseTest extends SystemTestCase4 implements ResultInterface {

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    protected ResultManager resultManager;
    //    private static int testIndex=0;
    protected Lab lab;
    private final char storedParamChar = '#';

    protected WindowsActionsWrapperBrowser windowsActionsWrapper;

    /**
     * global ant properties file (for reading/writing from/to it)
     */
    public AntProperties antProperties = AntProperties.getInstance();

    /**
     * represents machine that Eclipse or jsystem running on
     */
    public OsLocal localMachine = new OsLocal();

    /**
     * collecting executed test statuses
     */
    private static ExecutionSummary executionSummary;

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- GLOBAL TEST PARAMS ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    protected int browserIndex = 0;
    protected String[] jiraIssueList;
    protected boolean mandatoryTest = false;
    /**
     * if test is passed so the test is failed and vice versa
     */
    protected boolean isNegativeTest = false;

    /**
     * index of executed test
     */
    protected static int myTestIndex = -1;

    @Before
    public void getResources() throws Exception {
        report.report("current SUT is: " + SutUtils.getCurrentSUTName(), ReportAttribute.BOLD);
        report.startLevel("Before Tests Execution");
        try {

            String runningTestName = getCurrentTestName();
            lab = (Lab) system.getSystemObject("lab");
            resultManager = new ResultManager(runningTestName);

            this.executedSteps = this.executedSteps == null ? new StringBuffer() : this.executedSteps;
            executionSummary = executionSummary == null ? new ExecutionSummary() : executionSummary;
//            windowsActionsWrapper = new WindowsActionsWrapperBrowser(lab, browserIndex);
        } catch (Exception e) {
            report.report("got exception: " + e.getMessage() + " " + e.getStackTrace(), Reporter.FAIL);
        } finally {
            countCurrentTest();
            report.report("-------------------- END getResources(@BEFORE) of AxxanaBaseTest --------------------\n\n\n", ReportAttribute.BOLD);
            report.stopLevel();
            report.report("--- TEST STARTING HERE ---", ReportAttribute.BOLD);
        }
    }

    @After
    public void after() throws Exception {
//        this.takeSnapshotOnFailure();
        report.startLevel("After Tests Execution");
        try {


            resultManager.updateTestResult(super.getTestResult().wasSuccessful());
            // executionSummary.addLastRunnedTestResultToSummaryMap(isPass());
            resultManager.addJiraIssues(this.jiraIssueList);
            executionSummary.addLastRunnedTestResultToSummaryMap(resultManager.isTestResult(isPass()), jiraIssueList, mandatoryTest);


        } catch (Exception e) {
            report.report("got exception: " + e.getMessage() + " " + e.getStackTrace(), Reporter.FAIL);
        } finally {
            report.report("-------------------- END after(@After) of AxxanaBaseTest --------------------", ReportAttribute.BOLD);
            report.stopLevel();

            resultManager.printFullTestResult();
        }

//        testIndex++;
    }

    @Override
    public boolean isPass() {
        boolean isPassed = super.isPass();
        if (isNegativeTest) {
            report.report("Negative scenario so reverting result: " + isPassed + " to negative", ReportAttribute.BOLD);
            if (isPassed) {
                isPassed = false;
            } else {
                isPassed = true;
            }
        }
        return isPassed;
    }


    /**
     * alternative way to count test indexes <br>
     * if scenario converted to test (by 'mark scenario as test' option) <br>
     * build in testIndex parameter always =-1 so this one should fix this
     * issue <br>
     *
     * @throws Exception
     */
    private void countCurrentTest() throws Exception {
        myTestIndex++;
        antProperties.setAntProperty("mytestIndex", Integer.toString(myTestIndex));

        String indexAsString = antProperties.getAntProperty("mytestIndex");
        int activeTestIndex = Integer.parseInt(indexAsString);

        JTest currentTest = ScenariosManager.getInstance().getCurrentScenario().getTest(activeTestIndex);
        while (currentTest.isDisable()) {
            myTestIndex++;
            antProperties.setAntProperty("mytestIndex", Integer.toString(myTestIndex));
            indexAsString = antProperties.getAntProperty("mytestIndex");
            activeTestIndex = Integer.parseInt(indexAsString);
            currentTest = ScenariosManager.getInstance().getCurrentScenario().getTest(activeTestIndex);

            if (currentTest == null) {
                report.report("current test is null in disabled test");
                return;
            }
        }
    }

    /**
     * print current test name running to reporter
     */
    protected String getCurrentTestName() {
        int testIndex = RunnerStatePersistencyManager.getInstance().getActiveTestIndex();
        Scenario s = ScenariosManager.getInstance().getCurrentScenario();
        JTest currentTest = ScenariosManager.getInstance().getCurrentScenario().getTest(testIndex);

        String currentTestName = currentTest == null ? notFound : currentTest.getTestName();
        report.report("current Test name:" + currentTestName, ReportAttribute.BOLD);
        return currentTestName;
    }

    @Override
    public void addSubScenarioResult(String subScenarioResultName, boolean subScenarioResult) {
        resultManager.addSubScenarioResult(subScenarioResultName, subScenarioResult);
    }

    @Override
    public void printFullTestResult() throws Exception {
        resultManager.printFullTestResult();
    }

    /**
     * get loaded parameter from .ant.properties file or return received param as is
     *
     * @param parameterToLoad
     * @return
     * @throws IOException
     */
    protected String loadStoredParameter(String parameterToLoad) throws Exception {
        String loadedParameter = parameterToLoad;

        RegExpr regExpr = new RegExpr(parameterToLoad, "#\\{([\\w|_|\\d]+)\\}");
        if (regExpr.isFound()) {

            parameterToLoad = regExpr.getGroupText(2);
            String storedValue= RunProperties.getInstance().getRunProperty(parameterToLoad);
            loadedParameter=loadedParameter.replaceAll("#\\{([\\w|_|\\d]+)\\}", storedValue);
        } else {
            loadedParameter = parameterToLoad;
        }

//        if (parameterToLoad.charAt(0) == storedParamChar) {
//            parameterToLoad = parameterToLoad.substring(1, parameterToLoad.length());
//            loadedParameter = RunProperties.getInstance().getRunProperty(parameterToLoad);
//        } else {
//            loadedParameter = parameterToLoad;
//        }

        return loadedParameter;
    }

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------

    public int getBrowserIndex() {
        return browserIndex;
    }

    /**
     * index of the chosen browser (referenced to SUT file)
     *
     * @param browserIndex
     */
    public void setBrowserIndex(int browserIndex) {
        this.browserIndex = browserIndex;
    }

    public String[] getJiraIssueList() {
        return jiraIssueList;
    }

    /**
     * Jira issue (or list of issues (not recommended) like: DEX-123;DEX-345
     *
     * @param jiraIssueList
     * @section Global
     */
    public void setJiraIssueList(String[] jiraIssueList) {
        this.jiraIssueList = jiraIssueList;
    }

    public boolean isMandatoryTest() {
        return mandatoryTest;
    }

    /**
     * for mandatory test set true
     *
     * @param mandatoryTest
     * @section Global
     */
    public void setMandatoryTest(boolean mandatoryTest) {
        this.mandatoryTest = mandatoryTest;
    }

    public boolean isNegativeTest() {
        return isNegativeTest;
    }

    /**
     * if this parameter is true so if is test passed - the result will fail
     *
     * @param negativeTest
     * @section Global
     */
    public void setNegativeTest(boolean negativeTest) {
        isNegativeTest = negativeTest;
    }
}
