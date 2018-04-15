package com.cedex.automation.tests.actions.utils;


import com.cedex.automation.src.run.AntProperties;
import com.cedex.automation.src.run.TestUnit;
import com.cedex.fortests.ScenarioUtils;
import com.cedex.fortests.SutUtils;
import com.cedex.numbers.Randomalizator;
import com.cedex.text.Html2Text;
import com.cedex.text.StringArrayUtils;
import jsystem.framework.RunProperties;
import jsystem.framework.TestProperties;
import jsystem.framework.common.CommonResources;
import jsystem.framework.report.ExtendTestListener;
import jsystem.framework.report.ListenerstManager;
import jsystem.framework.report.Reporter;
import jsystem.framework.report.Reporter.ReportAttribute;
import jsystem.framework.report.TestInfo;
import jsystem.framework.scenario.JTest;
import jsystem.framework.scenario.JTestContainer;
import jsystem.framework.scenario.ScenariosManager;
import jsystem.framework.scenario.flow_control.AntForLoop;
import jsystem.utils.FileUtils;
import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static com.cedex.GlobalParameters.notFound;
import static com.cedex.automation.tests.actions.utils.ExecutionSummary.SummaryMailSendCause.IF_ANY_TEST_FAIL;
import static com.cedex.automation.tests.actions.utils.ExecutionSummary.SummaryMailSendCause.IF_MANDATORY_FAIL;

/**
 * manage executed tests and send execution summary emal
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$ <br>
 * Author: $Author: romang $<br>
 * Svn Version: $Revision: 12238 $ <br>
 *
 * @author $Author: romang $
 * @version $Revision: 12238 $
 */
public class ExecutionSummary extends MailTests implements ExtendTestListener {

    protected static int loopCount = 0;

    protected static int testIndexShownInReportlog;

    protected SummaryMailSubjectType summaryMailSubjectType = SummaryMailSubjectType.ENV_SCENARIO_RESULT;

    public static enum SummaryMailSendCause {
        ALL, IF_ANY_TEST_FAIL, IF_MANDATORY_FAIL;
    }

    public static enum SummaryMailSubjectType {
        ENV_SCENARIO_RESULT, SCENARIO_VERSION_RESULT;
    }

    /**
     * summary of execution contains test name -> test result <br>
     * looks like: <br>
     * key: scenario_testName -> Reporter.PASS/Reporter.Fail/Reporter.Warning
     */
    // protected static HashMap<String, Integer> executionSummary;
    protected static LinkedHashMap<String, LinkedHashMap<String, TestUnit>> executionSummaryMap;

    private SummaryMailSendCause summaryMailSendCause = SummaryMailSendCause.ALL;


    public ExecutionSummary() {
        ListenerstManager.getInstance().addListener(this);

    }

    public void before() throws IOException {
        report.startLevel("from @Before of ExecutionSummary class");
        try {
            testIndexShownInReportlog = Integer.parseInt(RunProperties.getInstance().getRunProperty("testIndexShownInReportlog"));
        } catch (NumberFormatException e) {
            report.report("from @Before of ExecutionSummary class" + e.getMessage());

        } catch (IOException e1) {
            report.report("from @Before of ExecutionSummary class" + e1.getMessage());

        }
        report.report("testIndexShownInReportlog=" + testIndexShownInReportlog);
        if (executionSummaryMap == null) {
            report.report("executionSummaryMap =null define it");
            executionSummaryMap = new LinkedHashMap<String, LinkedHashMap<String, TestUnit>>();
        } else {
            report.report("executionSummaryMap !=null");
            report.report("Execution Summary map contains: " + executionSummaryMap.toString());
        }
        report.stopLevel();
    }


    /**
     * send mail without attachments <br>
     * parameters like: host,user,password,port takes from jsystem.properties
     * file <br>
     * <b><font color=red>NOTE:<br>
     * <ol type=A>
     * to use this function add below parameters to jsystem.proprties file
     * (located under runner folder) <br>
     * the parameters are: <br>
     * <p>
     * <li>mail.from=Automation
     * <li>
     * mail.pop.host=pop.gmail.com
     * <li>
     * mail.pop.password=axxanaaxxana
     * <li>mail.pop.port=995
     * <li>
     * mail.pop.ssl=true
     * <li>mail.pop.user=axxanaMailer@gmail.com
     * <li>
     * mail.sendTo=autoreport@axxana.com
     * <li>mail.sntp.host=192.168.1.10
     * <li>
     * mail.sntp.password=5IGo05cU0N4\=%&*
     * <li>mail.sntp.port=25
     * <li>
     * mail.sntp.ssl=false
     * <li>mail.sntp.user=automation@axxana.com
     * <li>
     * mail.subject=Automation auto-generate mail
     * <li>at least one test executed before
     * <p>
     * </ol>
     * </font></b>
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "MAIL: Send summary email", paramsExclude = {"mandatoryTest", "browserIndex", "jiraIssueList", "emailsLike",
            "emailsLikeOpenText", "subject", "body", "from", "lastStepPass"})
    public void sendSummary() throws Exception {
        String sut = SutUtils.getCurrentSUTName();
        String subjectResult = notFound;
        String subjectSvnVersion = notFound;
        String subjectSwVersion = notFound;
        String subjectScenario = notFound;
        String totalExecutionResult = notFound;
        body = body + "<u>executed on Env: " + sut + "</u>\n";

        String infoLevel1 = "";
        String infoLevel2 = "";
        String infoLevel3 = "";
        String localBody = "";
        int countOfPassed = 0;
        int countOfFailed = 0;
        int countOfWarn = 0;
        int totalScenarioCount = 0;
        if (!isSummaryMailNeedToSend()) {
            report.report("Summary email should't be sent ...");
            return;
        }
        String tableLevel2Headers = "<table border=\"1\">" + "<tr>" + "<th>  Scenario   </th>"
                + "<th>  Passed     </th> <th>  Failed    </th> <th>  Warned    </th> <th>  Total    </th></tr>";

        String tableLevel3Headers = "<table border=\"1\">" + "<tr>" + "<th>  Scenario   </th>" + "<th>  Test Name  </th>"
                + "<th>  Result     </th>" + "<th>  Jira's Number  </th>" + "</tr>";

        String rootScenarioName = ScenariosManager.getInstance().getCurrentScenario().getRoot().getMyScenario().toString();

        String versionsLevel = "<table border=\"1\">" + "<tr>" + "<th>  /   </th>" + "<th>  Version     </th>" + "</tr>";
        // get BBX version
        String dbVersion = notFound;
        try {

//				  dbVersion = lab.bbx.getVersion();
            //TODO: add get DB Version
            dbVersion = "1.0";
        } catch (Exception e) {
            report.report("failed to get bbx version:" + e.getMessage(), Reporter.WARNING);
        } finally {
            versionsLevel = versionsLevel + "<tr><td>DB</td><td>" + dbVersion + "</td>";
        }
        // get collectors version
        String crmVersion = notFound;

//			for (int i = 0; i < lab.collectors.length; i++) {
        for (int i = 0; i < 1; i++) {

            try {
                crmVersion = "1.0";
//						crmVersion = lab.collectors[i].getVersion();
//						subjectSwVersion = lab.collectors[i].getVersion();
//						if (subjectSvnVersion.equals(notFound)) {
//							  if (!crmVersion.equals(notFound)) {
//									subjectSvnVersion = crmVersion;
//							  }
//
//						}

            } catch (Exception e) {
                report.report("failed to get collector " + i + " version:" + e.getMessage(), Reporter.WARNING);
            } finally {
                versionsLevel = versionsLevel + "<tr><td>CRM  </td><td>" + crmVersion + "</td>";
            }
        }

        // get recoverers version
//			for (int i = 0; i < lab.recoverers.length; i++) {
        for (int i = 0; i < 1; i++) {
            String webVersion = notFound;
            try {
//						webVersion = lab.recoverers[i].getVersion();
                webVersion = "1.0";
            } catch (Exception e) {
                report.report("failed to get recoverer " + i + " version:" + e.getMessage(), Reporter.WARNING);
            } finally {
                versionsLevel = versionsLevel + "<tr><td>Web </td><td>" + webVersion + "</td>";
            }
        }
        versionsLevel = versionsLevel + "</table>";
        rootScenarioName = rootScenarioName.replaceFirst("scenarios", "");
        String scenarioColor = "";
        String scenarioResultString = "";
        int runResult = Reporter.PASS;
        HashMap<String, HashMap<String, Integer>> scenarioResuls = new HashMap<String, HashMap<String, Integer>>();
        String level2Row = "<tr><td>{0}</td><td><font color=blue>{1}</font></b></td><td><font color=red>{2}</font></b></td><td><font color=orange>{3}</font></b></td><td><font color=black>{4}</font></b></td></tr>";

        String level3Row = "<tr><td></td><td>{0}</td><td><font color={1}>{2}</font></b></td><td>{3}</td></tr>";
        infoLevel2 = infoLevel2 + tableLevel2Headers;

        for (String scenarioName : executionSummaryMap.keySet()) {
            String[] scenarioNameAsArr = scenarioName.split("_Loop");
            String scenarioNameShort = scenarioNameAsArr[0];

            if (scenarioResuls.get(scenarioNameShort) == null) {
                scenarioResuls.put(scenarioNameShort, new HashMap<String, Integer>());

                scenarioResuls.get(scenarioNameShort).put("Pass", 0);
                scenarioResuls.get(scenarioNameShort).put("Fail", 0);
                scenarioResuls.get(scenarioNameShort).put("Warn", 0);
            }

            totalScenarioCount++;
            int scenarioResult = Reporter.PASS;
            infoLevel3 = infoLevel3 + "<h2>Scenario: " + scenarioName + "</h2>";
            infoLevel3 = infoLevel3 + tableLevel3Headers;
            report.report("going to collect test results");
            HashMap<String, TestUnit> currTest = executionSummaryMap.get(scenarioName);
            for (String testName : currTest.keySet()) {
                TestUnit currTestUnit = currTest.get(testName);
                String jiras = currTestUnit.getJiraIssueList() != null ? StringArrayUtils.stringArrayToString(currTestUnit.getJiraIssueList()) : "";

                Integer testResult = currTestUnit.getResult();
                String[] testNameAsArr = testName.split("@");
                testName = testNameAsArr[0] + "_" + testNameAsArr[2];
                testName = testName.replaceAll("_[\\d]+$","" );
                report.report("scenarioName= " + scenarioNameShort + " testName=" + testName + " result " + testResult);
                String resultRow = "";
                if (testResult == Reporter.PASS) {
                    infoLevel3 = infoLevel3 + MessageFormat.format(level3Row, testName, "blue", "Pass", jiras);

                } else if (testResult == Reporter.FAIL) {
                    scenarioResult = Reporter.FAIL;
                    runResult = Reporter.FAIL;
                    infoLevel3 = infoLevel3 + MessageFormat.format(level3Row, testName, "red", "Fail", jiras);
                } else {
                    scenarioResult = scenarioResult != Reporter.FAIL ? Reporter.WARNING : Reporter.FAIL;
                    runResult = runResult != Reporter.FAIL ? Reporter.WARNING : Reporter.FAIL;
                    infoLevel3 = infoLevel3 + MessageFormat.format(level3Row, testName, "orange", "Warn", jiras);
                }

                localBody = localBody + resultRow;

            }
            scenarioColor = getSummaryResult(scenarioResult, "color");
            scenarioResultString = getSummaryResult(scenarioResult, "result");
            if (scenarioResult == Reporter.PASS) {
                countOfPassed++;
                HashMap<String, Integer> tempResult = scenarioResuls.get(scenarioNameShort);
                tempResult.put("Pass", tempResult.get("Pass") + 1);
                scenarioResuls.put(scenarioNameShort, tempResult);

            } else if (scenarioResult == Reporter.FAIL) {
                HashMap<String, Integer> tempResult = scenarioResuls.get(scenarioNameShort);
                tempResult.put("Fail", tempResult.get("Fail") + 1);
                scenarioResuls.put(scenarioNameShort, tempResult);
                countOfFailed++;
            } else {
                HashMap<String, Integer> tempResult = scenarioResuls.get(scenarioNameShort);
                tempResult.put("Warn", tempResult.get("Warn") + 1);
                scenarioResuls.put(scenarioNameShort, tempResult);
                countOfWarn++;
            }
            infoLevel3 = infoLevel3 + "</table>";
        }
        scenarioColor = getSummaryResult(runResult, "color");
        scenarioResultString = getSummaryResult(runResult, "result");
        for (String currScenario : scenarioResuls.keySet()) {
            HashMap<String, Integer> currResult = scenarioResuls.get(currScenario);
            report.report("currScenario=" + currScenario + " vs rootScenarioName=" + rootScenarioName);
            if (rootScenarioName.equals("/" + currScenario)) {
                currScenario = "[root]" + currScenario;
            }
            int totalPerScenario = currResult.get("Pass") + currResult.get("Fail") + currResult.get("Warn");
            infoLevel2 = infoLevel2
                    + MessageFormat.format(level2Row, currScenario, currResult.get("Pass"), currResult.get("Fail"), currResult.get("Warn"),
                    totalPerScenario);

        }
        infoLevel2 = infoLevel2 + "</table>";
        report.report("infoLevel3=" + infoLevel3);
        // email subject ///
        if (countOfFailed > 0) {
            totalExecutionResult = "Failed";
            subjectResult = totalExecutionResult;
            localBody = MessageFormat.format(localBody, "<b><font color=red>Failed</font></b>");

        } else if (countOfWarn > 0) {
            totalExecutionResult = "Warned";
            localBody = MessageFormat.format(localBody, "<b><font color=orange>Passed with warnings</font></b>");
            subjectResult = totalExecutionResult;
        } else {
            totalExecutionResult = "Passed";
            localBody = MessageFormat.format(localBody, "<b><font color=blue>Passed</font></b>");
            subjectResult = totalExecutionResult;
        }

        infoLevel1 = infoLevel1 + "<br>" + "<table border=\"1\"><tr><th></th><th>count</th></tr>";
        infoLevel1 = infoLevel1 + "<tr><td><font color=blue>Passed</font></b></td><td><b>" + countOfPassed + "</td></tr>";
        infoLevel1 = infoLevel1 + "<tr><td><font color=red>Failed</font></b></td><td><b>" + countOfFailed + "</td></tr>";
        infoLevel1 = infoLevel1 + "<tr><td><font color=orange>Warning</font></b></td><td><b>" + countOfWarn + "</td></tr>";
        infoLevel1 = infoLevel1 + "<tr><td>Total</td><td><b><font color=black>" + totalScenarioCount + "</font></b></td></tr></table>";

        body = body + "<br><br>Level 0 : Versions description:<br>";
        body = body + versionsLevel;
        body = body + "<br><br>Level 1 : Scenarios Total description:<br>";
        body = body + infoLevel1;
        body = body + "<br><br>Level 2 : Scenarios One by One description:<br>";
        body = body + infoLevel2;
        body = body + "<br><br>Level 3 : Tests One by One description:<br>";
        body = body + "<br>" + infoLevel3;

        // add log url to mail

        String autoMachineHostName = localMachine.getHostName();

        String href = "<a href=\"http://" + autoMachineHostName + "/romangAuto/\">Link to: JSystem summary report</a>";
        body = body + "<br><br>" + href;
        String readableText = Html2Text.toPlainText(body);
        readableText = body.replaceAll("Level", "\nLevel");
        report.report(readableText);
        AntProperties.getInstance().setAntProperty("runResult", totalExecutionResult);
        subjectScenario = rootScenarioName.replaceFirst("\\/", "");
        subject = buildSummaryMailSubject(subjectScenario, subjectSvnVersion, subjectSwVersion, subjectResult, sut);
        sendLocalAsHTML();
    }

    public boolean isSummaryMailNeedToSend() throws IOException {
        report.startLevel("Validating if Summary mail will send");
        boolean isMailWillSend = false;
        try {

            switch (this.summaryMailSendCause) {
                case ALL: {
                    isMailWillSend = true;
                    break;
                }
                case IF_ANY_TEST_FAIL:
                case IF_MANDATORY_FAIL: {
                    for (String scenarioName : executionSummaryMap.keySet()) {
                        LinkedHashMap<String, TestUnit> currTestResult = executionSummaryMap.get(scenarioName);

//                    for (int i = 0; i < currTestResult.size(); i++) {
                        for (String currName : currTestResult.keySet()) {
                            TestUnit currTestUnit = currTestResult.get(currName);
                            String currTestName = currTestUnit.getName();
                            boolean isCurrentLoop = false;
                            if (!currTestName.endsWith("_Loop")) {
                                isCurrentLoop = true;
                            } else if (currTestName.endsWith("_Loop" + loopCount)) {
                                isCurrentLoop = true;
                            }


                            if (isCurrentLoop) {
                                if (!currTestName.contains("Send summary email")) {
                                    if (this.summaryMailSendCause == IF_ANY_TEST_FAIL) {
                                        if (currTestUnit.getResult() == Reporter.FAIL) {
                                            report.report("Test failed ...");
                                            isMailWillSend = true;
                                            break;
                                        }
                                    } else if (this.summaryMailSendCause == IF_MANDATORY_FAIL) {
                                        if (currTestUnit.getResult() == Reporter.FAIL && currTestUnit.isMandatory()) {
                                            report.report("Mandatory test failed ...");
                                            isMailWillSend = true;
                                            break;
                                        }
                                    }
                                } else {
                                    report.report("Skip send summary test from send mail calculation...");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            report.report("Got exiprion during mail send verification " + e.getMessage() + " " + e.getStackTrace());
        } finally {
            report.stopLevel();
        }
        return isMailWillSend;
    }

    /**
     * returns jsystem GUI's loop current iteration <br>
     * (working with default myVar value)
     *
     * @return
     * @throws IOException
     */
    public int getLoopIterationCount() throws IOException {
        int guiLoopIterationCount = 0;
        File runPropertiesFile = new File(CommonResources.ANT_INTERNAL_PROPERTIES_FILE);
        FileUtils.loadPropertiesFromFile(runPropertiesFile.getAbsolutePath());
        String myVar = antProperties.getAntProperty("myVar"); // AntProperties.getInstance().getAntProperty("myVar");
        guiLoopIterationCount = myVar == null ? guiLoopIterationCount : Integer.parseInt(myVar);

        return guiLoopIterationCount;
    }

    /**
     * add ended test restult to executionSummary map <br>
     * invoked from @After only
     *
     * @throws Exception
     */
    public void addLastRunnedTestResultToSummaryMap(boolean isPassed, String[] jiraIssueList, boolean isMandatoryTest) throws Exception {
        before();
        boolean isScenarioMarkedAsTest = false;
        String indexAsString = antProperties.getAntProperty("mytestIndex");
        int activeTestIndex = Integer.parseInt(indexAsString);

        JTest currentTest = ScenariosManager.getInstance().getCurrentScenario().getTest(activeTestIndex);

        if (currentTest == null) {
            report.report("currentTest =null , going to fix it");
            int index = Integer.parseInt(indexAsString);

            currentTest = ScenariosManager.getInstance().getCurrentScenario().getTestFromRoot(index);
            boolean buildInMarkAsTest = ScenariosManager.getInstance().getCurrentScenario().isScenarioAsTest();
            report.report("buildInMarkAsTest=" + buildInMarkAsTest);
            isScenarioMarkedAsTest = true;
            if (currentTest == null) {
                report.report("current test is null");
                return;
            }

        }

        String testName = currentTest.getMeaningfulName();
        if (isScenarioMarkedAsTest) {
            testName = currentTest.getTestName();
        } else if (testName == null) {
            testName = currentTest.getTestName();
        }

        report.startLevel("add test: " + testName + " to Execution results summary Map");

        String scenarioName = ScenarioUtils.getScenarioFullName(currentTest);

        scenarioName = scenarioName.replaceFirst("scenarios", "");

        Integer testResult = Reporter.PASS;
        if (!isPassed) {
            testResult = Reporter.FAIL;
            report.report("test: " + testName + " Failed");
        } else if (isPassed) {
            testResult = Reporter.PASS;
            report.report("test: " + testName + " Pass");
        } else {
            testResult = Reporter.FAIL;
            report.report("test: " + testName + " UNKNOWN", Reporter.FAIL);
        }

        String currentUUID = this.getFullUUID();

        String isLoopStarted = RunProperties.getInstance().getRunProperty("loopStarted") != null ? RunProperties.getInstance().getRunProperty(
                "loopStarted") : "";
        if (isLoopStarted.equals("true"))
            loopCount = getLoopIterationCount();
        else
            loopCount = 0;

        testName = testName + "@" + currentUUID + "@" + testIndexShownInReportlog + "@" + loopCount;
        scenarioName = loopCount != 0 ? scenarioName + "_Loop" + loopCount : scenarioName;

        this.executionSummaryMapInsert(scenarioName, testName, testResult, jiraIssueList, isMandatoryTest);

        report.stopLevel();
    }

    private void executionSummaryMapInsert(String scenarioName, String testName, int testResult, String[] jiraIssueList, boolean isMandatoryTest) {
        report.report("add to scenario: " + scenarioName + " test: " + testName + " -> " + testResult, ReportAttribute.BOLD);

        LinkedHashMap<String, TestUnit> currTestResult = executionSummaryMap.get(scenarioName) != null ? executionSummaryMap.get(scenarioName)
                : new LinkedHashMap<String, TestUnit>();
        TestUnit currTestUnit = currTestResult.get(testName);
//        currTestUnit.setMandatory(super.isMandatoryTest());
//        currTestUnit.setJiraIssueList(super.getJiraIssueList());
//        Integer cuttTestResult = currTestUnit.getResult();
        if (currTestUnit != null) {
//            report.report("Test: " + testName + " already exists !!!!", Reporter.WARNING);
            report.report("Test: " + testName + " already exists , (adding randon suffix)");
            String randomIDName="_"+ Randomalizator.getRandomNumber(10000);
            testName+=randomIDName;
        }
        currTestUnit = new TestUnit(testName, testResult, isMandatoryTest, jiraIssueList);
        currTestResult.put(testName, currTestUnit);

        report.report("executionSummary size before:" + executionSummaryMap.size());

        executionSummaryMap.put(scenarioName, currTestResult);

        executionSummaryMapPrint();

    }

    private void executionSummaryMapPrint() {
        // print already exist tests
        Collection c = executionSummaryMap.values();

        // obtain an Iterator for Collection
        Iterator itr = c.iterator();

        // iterate through LinkedHashMap values iterator
        while (itr.hasNext())
            report.report("summary map : " + itr.next().toString());

        report.report("executionSummary size after:" + executionSummaryMap.size());
    }

    /**
     * create subject to other types of summary mail<br>
     * 1. for regular summary<br>
     * 2. for jenkins summary <br>
     *
     * @param scenario
     * @param svnBuild
     * @param swBuild
     * @param result
     * @param sut
     * @return
     * @throws Exception
     */
    private String buildSummaryMailSubject(String scenario, String svnBuild, String swBuild, String result, String sut) throws Exception {

        switch (summaryMailSubjectType) {
            case ENV_SCENARIO_RESULT:
                subject = "Env: " + sut + " Scenario: " + scenario + " " + result;
                break;
            case SCENARIO_VERSION_RESULT:
                subject = " Scenario: " + scenario + " Version: " + svnBuild + "(" + swBuild + ") " + " Result: " + result;
                break;
            default:
//						throwExceptionIfNeeded(new Exception("not found option for parameter:" + summaryMailSubjectType), Reporter.FAIL);
                throw new Exception("not found option for parameter:" + summaryMailSubjectType);
        }
        return subject;
    }

    /**
     * get result/color of scenario or sub scenario
     *
     * @param currResult - current result
     * @param parameter  possible values: color, result
     * @return
     * @throws Exception
     */
    private String getSummaryResult(int currResult, String parameter) throws Exception {
        String returnValue = "";

        switch (currResult) {
            case Reporter.PASS:
                if (parameter.equals("color")) {
                    returnValue = "blue";
                } else {
                    returnValue = "Pass";
                }

                break;
            case Reporter.FAIL:
                if (parameter.equals("color")) {
                    returnValue = "red";
                } else {
                    returnValue = "Fail";
                }
                break;
            case Reporter.WARNING:
                if (parameter.equals("color")) {
                    returnValue = "orange";
                } else {
                    returnValue = "Warn";
                }

                break;
            default:
//						throwExceptionIfNeeded(new Exception("not found option"), reportResult(Reporter.FAIL));
                throw new Exception("not found option");
        }
        return returnValue;
    }

    @Override
    public void addFailure(junit.framework.Test arg0, AssertionFailedError arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void endTest(junit.framework.Test arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void startTest(junit.framework.Test arg0) {

    }

    @Override
    public void addWarning(junit.framework.Test test) {
        // TODO Auto-generated method stub

    }

    @Override
    public void startTest(TestInfo testInfo) {

        testIndexShownInReportlog = testInfo.count;

        try {
            RunProperties.getInstance().setRunProperty("testIndexShownInReportlog", testIndexShownInReportlog + "");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endRun() {
        // TODO Auto-generated method stub

    }

    @Override
    public void startLoop(AntForLoop loop, int count) {

        try {
            RunProperties.getInstance().setRunProperty("loopStarted", "true");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // report.report("from startLoop numOfLoops: " +
        // loop.getNumOfLoops() +
        // " firstLoopTestUID =" + getFullUUID(),
        // ReportAttribute.BOLD);
    }

    @Override
    public void endLoop(AntForLoop loop, int count) {
        try {
            RunProperties.getInstance().setRunProperty("loopStarted", "false");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // report.report("from endLoop", ReportAttribute.BOLD);

    }

    @Override
    public void startContainer(JTestContainer container) {
        // TODO Auto-generated method stub

    }

    @Override
    public void endContainer(JTestContainer container) {
        // TODO Auto-generated method stub

    }

    /**
     * @return the summaryMailSubjectType
     */
    public SummaryMailSubjectType getSummaryMailSubjectType() {
        return summaryMailSubjectType;
    }

    /**
     * @param summaryMailSubjectType the summaryMailSubjectType to set
     */
    public void setSummaryMailSubjectType(SummaryMailSubjectType summaryMailSubjectType) {
        this.summaryMailSubjectType = summaryMailSubjectType;
    }

    public SummaryMailSendCause getSummaryMailSendCause() {
        return summaryMailSendCause;
    }

    /**
     * Sending summary main in this case
     *
     * @param summaryMailSendCause
     */
    public void setSummaryMailSendCause(SummaryMailSendCause summaryMailSendCause) {
        this.summaryMailSendCause = summaryMailSendCause;
    }
}
