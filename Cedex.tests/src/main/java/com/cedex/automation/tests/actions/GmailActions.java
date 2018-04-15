package com.cedex.automation.tests.actions;

import com.cedex.automation.src.wrappers.GmailWrapperBrowser;
import com.cedex.automation.tests.base.AbstractBrowserBaseTest;
import com.cedex.text.StringUtils.StringOperator;
import jsystem.framework.TestProperties;
import org.junit.Before;
import org.junit.Test;

public class GmailActions extends AbstractBrowserBaseTest {


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------- VARIABLES ------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    private String gmailLogin = "qac@optionfair.com";
    private String gmailPassword = "123456rOman";
    private int mailIndex = 0;
    private String mailSubjectToLookFor;
    private StringOperator stringOperator = StringOperator.CONTAINS;
    private GmailWrapperBrowser gmailWrapper;




    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //--------------------------------------- ACTIONS ----------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    @Before
    public void before() {
        gmailWrapper = new GmailWrapperBrowser(lab, browserIndex);
    }


    @Test
    @TestProperties(name = "BB: GMail Login", paramsExclude = {"mailIndex", "mailSubjectToLookFor", "stringOperator"}, returnParam = {})
    public void googleLogin() throws Exception {
        gmailWrapper.gMailLogin(gmailLogin, gmailPassword);
    }

    @Test
    @TestProperties(name = "BB: GMail Logout", paramsExclude = {"gmailLogin", "gmailPassword", "mailIndex", "mailSubjectToLookFor", "stringOperator"}, returnParam = {})
    public void googleLogout() throws Exception {
        gmailWrapper.gMailLogout();
    }

    @Test
    @TestProperties(name = "BB: GMail Open mail by index: ${mailIndex}", paramsExclude = {"gmailLogin", "gmailPassword", "mailSubjectToLookFor", "stringOperator"}, returnParam = {})
    public void googleOpenMailByIndex() throws Exception {
        gmailWrapper.gMailLogin(gmailLogin, gmailPassword);
        gmailWrapper.gMailOpenMailByIndex(mailIndex);
    }

    @Test
    @TestProperties(name = "BB: GMail Open mail by subject: ${mailSubjectToLookFor}", paramsExclude = {"gmailLogin", "gmailPassword", "mailIndex"}, returnParam = {})
    public void googleOpenMailBySubject() throws Exception {
        gmailWrapper.gMailOpenMailBySubject(mailSubjectToLookFor, stringOperator);
    }

    @Test
    @TestProperties(name = "BB: GMail go To Inbox", paramsExclude = {"gmailLogin", "gmailPassword", "mailIndex", "mailSubjectToLookFor", "stringOperator"}, returnParam = {})
    public void googleBackToInbox() throws Exception {
//        gmailWrapper.gMailLogin(gmailLogin, gmailPassword);
        gmailWrapper.gMailBackToInbox();
    }

    /**
     * removing mails from GMAIL by mail index or ALL mails
     *
     * to remove all mails from GMail set mailIndex=-1
     *
     * <b>NOTE:<b/>
     * Only delete all mails options is tested till now
     *
     * @throws Exception
     */
    @Test
    @TestProperties(name = "BB: GMail remove emails by index ${mailIndex}", paramsExclude = {"gmailLogin", "gmailPassword", "mailSubjectToLookFor", "stringOperator"}, returnParam = {})
    public void googleRemoveMails() throws Exception {
        mailIndex=-1;
        gmailWrapper.gMailLogin(gmailLogin, gmailPassword);
        gmailWrapper.gMailRemoveMails(mailIndex);
    }


    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------- SETTERS / GETTERS  ---------------------------------------
    //----------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------


    public String getGmailLogin() {
        return gmailLogin;
    }

    public void setGmailLogin(String gmailLogin) {
        this.gmailLogin = gmailLogin;
    }

    public String getGmailPassword() {
        return gmailPassword;
    }

    public void setGmailPassword(String gmailPassword) {
        this.gmailPassword = gmailPassword;
    }

    public int getMailIndex() {
        return mailIndex;
    }

    /**
     * the index of a email letter (started from 0 - the latest one)
     *
     * @param mailIndex
     */
    public void setMailIndex(int mailIndex) {
        this.mailIndex = mailIndex;
    }

    public String getMailSubjectToLookFor() {
        return mailSubjectToLookFor;
    }

    public void setMailSubjectToLookFor(String mailSubjectToLookFor) {
        this.mailSubjectToLookFor = mailSubjectToLookFor;
    }

    public StringOperator getStringOperator() {
        return stringOperator;
    }

    /**
     * the gmail subject (may be a partial)
     *
     * @param stringOperator
     */
    public void setStringOperator(StringOperator stringOperator) {
        this.stringOperator = stringOperator;
    }
}
