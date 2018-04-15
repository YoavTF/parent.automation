package com.cedex.browser.waiter;

import com.cedex.browser.BrowserBase;
import com.cedex.browser.BrowserBase.RecognizeBy;
import com.cedex.feature.waiter.WaiterInterface;
import com.cedex.jsystem.ReporterLight;

import static com.cedex.GlobalParameters.notFound;

public abstract class WaiterBaseImpl implements WaiterInterface,ReporterLight{

	private String waitForScenarioMessage = notFound;
	private String lastMessage = notFound;
	protected BrowserBase browser;
	protected RecognizeBy recognizeBy;
	protected String identifier;

	/**
	 * 
	 * @param browser
	 *            - base browser to execute commands on
	 * @param waitForScenarioMessage
	 *            - scenario name
	 * @param lastMessage
	 *            - OK and FAIL lastMessage
	 * @throws Exception
	 */
	public WaiterBaseImpl(BrowserBase browser,RecognizeBy recognizeBy,String identifier,String waitForScenarioMessage, String lastMessage) throws Exception {
		this.waitForScenarioMessage = waitForScenarioMessage;
		this.lastMessage = lastMessage;
		this.browser = browser;
		this.recognizeBy=recognizeBy;
		this.identifier=identifier;
	}

	@Override
	public void waitFor(int maxTimeToWaitInMin) throws Exception {
		int delayTimeInSec = 5;// in sec
		this.waitFor(maxTimeToWaitInMin, delayTimeInSec);
	}

	private int secondsToMiliseconds(int seconds) {
		return seconds * 1000;
	}

	@Override
	public void waitFor(int maxTimeToWaitInMin, int delayTimeInSec) throws Exception {

		report.startLevel("wait for: " + this.waitForScenarioMessage);
		try {

			// int delayTime = delayTimeInSec;

			int maxTimeToWaitInSec = maxTimeToWaitInMin * 60;
			int maxAttempt = maxTimeToWaitInSec / delayTimeInSec;
			int currAttempt = 0;
			boolean stopLoop = false;

			while (!stopLoop) {
				boolean isStopLoopCaseFound = this.isLoopStopCondition();
				if (isStopLoopCaseFound) {
					stopLoop = true;
					report.report(this.lastMessage + " was successfully waited");
				} else if (currAttempt >= maxAttempt) {
					stopLoop = true;
					report.report("Not reached: " + lastMessage + " after " + maxTimeToWaitInMin + " Min");
				} else {
					report.report("Sleep for: " + delayTimeInSec + " (sec) still not found: " + lastMessage
							+ " before next attempt: " + currAttempt + "/" + maxAttempt);
					Thread.sleep(this.secondsToMiliseconds(delayTimeInSec));
					currAttempt++;
				}
			}

		} catch (Exception e) {
//			throwExceptionIfNeeded(e, reportResult(Reporter.FAIL));
			throw e;
		} finally {
			report.stopLevel();
		}

	}

	@Override
	public abstract boolean isLoopStopCondition() throws Exception;

	public BrowserBase getBrowser() {
		return this.browser;
	}

	public void setBrowser(BrowserBase browser) {
		this.browser = browser;
	}
}
