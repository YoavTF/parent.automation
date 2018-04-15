package com.cedex.automation.src.result;


import com.cedex.fortests.TestUtils;
import com.cedex.jsystem.ReporterLight;
import com.cedex.text.textformat.TextBlockFormatter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;


public class ResultManager implements ResultInterface, ReporterLight {
    String testName = "";
    private boolean testResult = true;
    private final HashMap<String, Boolean> subScenarioResultsMap = new LinkedHashMap<String, Boolean>();
    //    private ArrayList<String> jiraIssuesArr = new ArrayList<>();
    private HashSet<String> jiraIssuesArr = new HashSet<String>();

    public ResultManager(String testName) {
        this.testName = testName;
    }

    /**
     * updating testResult parameter (
     *
     * @param subResult
     */
    public void updateTestResult(boolean subResult) {
        this.testResult = this.testResult ? subResult : this.testResult;
    }

    /**
     * add sub result (AKA step) to test
     *
     * @param subScenarioResultName
     * @param subScenarioResult
     */
    public void addSubScenarioResult(String subScenarioResultName, boolean subScenarioResult) {
        this.subScenarioResultsMap.put(subScenarioResultName, subScenarioResult);
        updateTestResult(subScenarioResult);
    }

    /**
     * print full test result to the log file
     *
     * @throws Exception
     */
    public void printFullTestResult() throws Exception {

//        report.report("----------------------------------------------------------------------------------------------");
//        report.report("-------- Test '" + testName + "' result summary ------------------------------");
//        report.report("----------------------------------------------------------------------------------------------");
//        TextBlockFormatter.printHeader("TEST '" + testName + "' RESULT SUMMARY", '-',true);
        TextBlockFormatter.printHeader(" ---------- TEST RESULT SUMMARY ---------- ", '-',true);

        calculateFullTestResult(true, true);

        TestUtils.printTestResult(testName, testResult);

    }

    /**
     * calculate full test result only
     *
     * @param printResult print scenario result to log
     * @throws Exception
     */
    protected void calculateFullTestResult(boolean printResult, boolean junitResult) throws Exception {

        for (String currSubScenario : this.subScenarioResultsMap.keySet()) {
            boolean currSubScenarioResult = this.subScenarioResultsMap.get(currSubScenario);
            this.updateTestResult(currSubScenarioResult);
            if (printResult)
                report.report(currSubScenario + " result: " + currSubScenarioResult, currSubScenarioResult);
        }
        if (!junitResult) {
            this.updateTestResult(junitResult);
            if (printResult) {
                report.report("junit result: " + junitResult, junitResult);
                report.report("------------------------------------------------------------------");
                report.report("Jira issue(s) covered: " + jiraIssuesArr.toString());
                report.report("------------------------------------------------------------------");
            }
        }
    }

    public boolean isTestResult() throws Exception {
        calculateFullTestResult(false, true);
        return testResult;
    }

    public boolean isTestResult(boolean junitTestResult) throws Exception {
        calculateFullTestResult(false, junitTestResult);
        return testResult;
    }

    /**
     * add jira issue or list of jira issues to test result
     *
     * @param jiras
     */
    public void addJiraIssues(String[] jiras) {
        if (jiras != null) {
            for (String currJuraNumber : jiras) {
                if (currJuraNumber != null && !currJuraNumber.isEmpty())
                    this.jiraIssuesArr.add(currJuraNumber);
            }
        }
    }

}
