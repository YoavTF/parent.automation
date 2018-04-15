package com.cedex.automation.src.run;

import com.cedex.jsystem.ReporterLight;
import com.cedex.xml.XmlParser;
import jsystem.extensions.report.xml.XmlReportHandler;
import jsystem.framework.report.ExtendTestListener;
import jsystem.framework.report.TestInfo;
import jsystem.framework.scenario.JTestContainer;
import jsystem.framework.scenario.flow_control.AntForLoop;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

public class ExecutionListener implements ExtendTestListener, ReporterLight {

	public boolean isCurrentTestWarned = false;
	public boolean isCurrentTestFailed = false;
	public static int totalTestsCount = 0;
	public static int passedTestsCount = 0;
	public static int failedTestsCount = 0;
	public static int warningTestsCount = 0;
	private XmlReportHandler xmlReportHandler;
	private XmlParser xmlParser;

	ExecutionListener() {
		xmlReportHandler = XmlReportHandler.getInstance();
		// xmlParser = new XmlParser();
	}

	@Override
	public void addError(Test arg0, Throwable arg1) {
		isCurrentTestFailed = true;
		// report.report("from: addError" + arg0.toString());
	}

	@Override
	public void addFailure(Test arg0, AssertionFailedError arg1) {
		isCurrentTestFailed = true;
		// report.report("from: addFailure" + arg0.toString());

	}

	@Override
	public void endTest(Test arg0) {
		// report.report("from: endTest" + arg0.toString());
		// report.report("@@@ Test0: " + xmlReportHandler.getTestName(0) +
		// " status: " + xmlReportHandler.getTestStatus(0)
		// + " @@@");
		// if (isCurrentTestFailed) {
		// report.report("test failed @@@@@@@@@@@@@@@@@@@@@");
		// failedTestsCount++;
		// isCurrentTestFailed = false;
		// } else {
		// if (isCurrentTestWarned) {
		// report.report("test warned @@@@@@@@@@@@@@@@@@@@@");
		// warningTestsCount++;
		// isCurrentTestWarned = false;
		// } else {
		// report.report("test passed @@@@@@@@@@@@@@@@@@@@@");
		// passedTestsCount++;
		// }
		// }

	}

	@Override
	public void startTest(Test arg0) {
		// totalTestsCount++;
		// System.out.println("################## Started test: ##################");
		// report.report("from: startTest" + arg0.toString());

	}

	@Override
	public void addWarning(Test test) {
		isCurrentTestWarned = true;
		// report.report("from: addWarning" + test.toString());

	}

	@Override
	public void startTest(TestInfo testInfo) {
		// totalTestsCount++;
		// report.report("from: startTest" + testInfo.basicName);
		// System.out.println("$$$$$$$$$$$$$$$$$$ Started test: " +
		// testInfo.methodName + " count= " + testInfo.count
		// + " @@@@@@@@@@@@@@@@@@@@@");

	}

	@Override
	public void endRun() {
		// report.report("from: endRun");
		// totalTestsCount = xmlReportHandler.getNumberOfTests();
		// passedTestsCount = xmlReportHandler.getNumberOfTestsPass();
		// failedTestsCount = xmlReportHandler.getNumberOfTestsFail();
		// warningTestsCount = xmlReportHandler.getNumberOfTestsWarning();
		// report.report("@@@@@@@@@@@@@@@@@@@@@ Execution Summary: @@@@@@@@@@@@@@@@@@@@@");
		// report.report("@@@ Total count: " +
		// xmlReportHandler.getNumberOfTests() + " @@@");
		// report.report("@@@ Passed count: " +
		// xmlReportHandler.getNumberOfTestsPass() + " @@@");
		// report.report("@@@ Failed count: " +
		// xmlReportHandler.getNumberOfTestsFail() + " @@@");
		// report.report("@@@ Warned count: " +
		// xmlReportHandler.getNumberOfTestsWarning() + " @@@");

	}

	@Override
	public void startLoop(AntForLoop loop, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endLoop(AntForLoop loop, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startContainer(JTestContainer container) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContainer(JTestContainer container) {
		// TODO Auto-generated method stub

	}

}
