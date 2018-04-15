package com.cedex.automation.tests.actions.utils;

import com.cedex.automation.tests.base.AbstractBaseTest;
import com.cedex.time.Sleeper;
import jsystem.framework.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class UtilsActions extends AbstractBaseTest {

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


//    private long sleepMaxTimeInMilis = 5000;
//    private long sleepMinTimeInMilis = 1000;

    private int sleepMaxTime = 5;
    private int sleepMinTime = 1;

    private int sleepTime = 1;
    private TimeUnit sleepUnit = TimeUnit.SECONDS;

    private String sleepMessage = "Sleeping for: " + sleepTime + " " +sleepUnit+"(s)";
    private String textToPrintToLog = "print to log sone text ...";

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------

//    @Test
//    @TestProperties(name = "BB: Sleep for: ${sleepTimeInSec} seconds", paramsExclude = {"sleepTime","sleepUnit","browserIndex", "jiraIssueList", "mandatoryTest", "sleepMaxTimeInMilis", "sleepMinTimeInMilis", "textToPrintToLog"}, returnParam = {})
//    public void sleep() throws Exception {
//        Assert.assertNotNull(sleepTimeInSec);
//        Sleeper.sleepForNSeconds(sleepMessage, sleepTimeInSec);
//    }

    @Test
    @TestProperties(name = "BB: Sleep for: ${sleepTime} ${sleepUnit}", paramsExclude = {"browserIndex", "jiraIssueList", "mandatoryTest", "sleepMaxTime", "sleepMinTime", "textToPrintToLog"}, returnParam = {})
    public void sleep() throws Exception {
        Assert.assertNotNull(sleepTime);
        Sleeper.sleepForNTime(sleepMessage, sleepTime, sleepUnit);
    }

    /**
     * Random sleep at least time difined by: sleepMinTimeInMilis and max randon time defined by: sleepMaxTimeInMilis value
     * <p>
     * <b>both sleep times in miliseconds</>
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: Sleep Random time between ${sleepMinTimeInMilis} up to  ${sleepMaxTimeInMilis} miliseconds",
            paramsExclude = {"sleepTime", "sleepUnit", "browserIndex", "jiraIssueList", "mandatoryTest", "sleepTimeInSec", "textToPrintToLog"}, returnParam = {})
    public void sleepRandom() throws Exception {
        Assert.assertNotNull(sleepMaxTime);
        Assert.assertNotNull(sleepMinTime);
        Sleeper.sleepForRandomNTime(sleepMessage, sleepMinTime, sleepMaxTime,sleepUnit);
    }



    @Test
    @TestProperties(name = "BB: print text to log ", paramsExclude = {"sleepTime","sleepMessage", "sleepUnit", "browserIndex", "jiraIssueList", "mandatoryTest", "sleepMaxTime", "sleepMinTime","sleepUnit"}, returnParam = {})
    public void printTextToLog() throws Exception {

        report.report(super.loadStoredParameter(textToPrintToLog));
    }

    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public TimeUnit getSleepUnit() {
        return sleepUnit;
    }

    public void setSleepUnit(TimeUnit sleepUnit) {
        this.sleepUnit = sleepUnit;
    }

//    public int getSleepTimeInSec() {
//        return sleepTimeInSec;
//    }
//
//    /**
//     * time to sleep in seconds
//     *
//     * @param sleepTimeInSec
//     */
//    public void setSleepTimeInSec(int sleepTimeInSec) {
//        this.sleepTimeInSec = sleepTimeInSec;
//    }

    public String getSleepMessage() {
        return sleepMessage;
    }

    /**
     * the message will be issued during the sleep
     *
     * @param sleepMessage
     */
    public void setSleepMessage(String sleepMessage) {
        this.sleepMessage = sleepMessage;
    }

    public long getSleepMaxTime() {
        return sleepMaxTime;
    }

    public void setSleepMaxTime(int sleepMaxTime) {
        this.sleepMaxTime = sleepMaxTime;
    }

    public long getSleepMinTime() {
        return sleepMinTime;
    }

    public void setSleepMinTime(int sleepMinTime) {
        this.sleepMinTime = sleepMinTime;
    }


    //    public long getSleepMaxTimeInMilis() {
//        return sleepMaxTimeInMilis;
//    }
//
//    public void setSleepMaxTimeInMilis(long sleepMaxTimeInMilis) {
//        this.sleepMaxTimeInMilis = sleepMaxTimeInMilis;
//    }
//
//    public long getSleepMinTimeInMilis() {
//        return sleepMinTimeInMilis;
//    }
//
//    public void setSleepMinTimeInMilis(long sleepMinTimeInMilis) {
//        this.sleepMinTimeInMilis = sleepMinTimeInMilis;
//    }

    public String getTextToPrintToLog() {
        return textToPrintToLog;
    }

    public void setTextToPrintToLog(String textToPrintToLog) {
        this.textToPrintToLog = textToPrintToLog;
    }
}
