package com.cedex.automation.src.result;

public interface ResultInterface {

    /**
     * add sub result (AKA step) to test
     *
     * @param subScenarioResultName
     * @param subScenarioResult
     */
    public void addSubScenarioResult(String subScenarioResultName, boolean subScenarioResult);
    /**
     * print full test result to the log file
     *
     * @throws Exception
     */
    public void printFullTestResult() throws Exception;
}
