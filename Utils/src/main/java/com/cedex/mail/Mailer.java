package com.cedex.mail;

import com.cedex.jsystem.JsystemPropertiesLight;
import com.cedex.mail.message.EmailMessage;
import com.cedex.text.StringArrayUtils;
import com.cedex.time.CurrentTime;
import com.cedex.time.TimeRange;
import jsystem.framework.report.Reporter;
import jsystem.utils.MailMessage;
import jsystem.utils.MailUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * class sending and reading mails checked on: <br>
 * sending mails from: axxana
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 *
 * @author $Author: romang $
 * @version $Revision: 8874 $
 */
public class Mailer extends MailUtil implements JsystemPropertiesLight {
    private final String popUserName;
    private final String popGuestUser;
    private final String popPassword;
    private final boolean popSSL;
    private final String smtpUserName;
    private final String smtpPassword;
    private final boolean smtpSSL;

    /**
     * defines parametes from jsystem properties
     *
     * @throws Exception
     */
    public Mailer() throws Exception {
        popUserName = jSystemProperties.getPreference("mail.pop.user");
        popGuestUser = jSystemProperties.getPreference("mail.pop.guest.user");
        popPassword = jSystemProperties.getPreference("mail.pop.password");

        this.setPopHost(jSystemProperties.getPreference("mail.pop.host"));
        try {
            this.setPopPort(Integer.parseInt(jSystemProperties.getPreference("mail.pop.port")));
        } catch (Exception e) {
            throw new Exception("Can't convert popPort value:" + jSystemProperties.getPreference("mail.pop.port")
                    + " from jsystem properties file");
        }
        popSSL = jSystemProperties.getPreference("mail.pop.ssl").equalsIgnoreCase("true") ? true : false;

        smtpUserName = jSystemProperties.getPreference("mail.smtp.user");
        smtpPassword = jSystemProperties.getPreference("mail.smtp.password");

        this.setSmtpHostName(jSystemProperties.getPreference("mail.smtp.host"));

        try {
            this.setSmtpPort(Integer.parseInt(jSystemProperties.getPreference("mail.smtp.port")));
        } catch (Exception e) {
            throw new Exception("Can't convert sntsPort value:" + jSystemProperties.getPreference("mail.smtp.port")
                    + " from jsystem properties file");
        }
        smtpSSL = jSystemProperties.getPreference("mail.smtp.ssl").equalsIgnoreCase("true") ? true : false;
    }

    /**
     * sending email (checked on axxana exchange server)
     *
     * @param to
     * @param subject
     * @param msgBody
     * @param filesToAttach
     * @throws Exception
     */
    public void sendEmail(String[] to, String subject, String msgBody, String[] filesToAttach, boolean messageAsHtmlText)
            throws Exception {
        report.startLevel("sending email ...");
        String fromPropertiesFile = jSystemProperties.getPreference("mail.from");
        this.setPassword(this.smtpPassword);
        this.setSsl(this.smtpSSL);
        this.setSendTo(to);
        this.setFromAddress(fromPropertiesFile);
        if (this.smtpHostName == null || fromPropertiesFile == null || this.password == null || this.smtpPort == 0
                || to == null || subject == null || this.smtpUserName == null) {

            throw new Exception("Not all email param configured properly (one of those is null) : \n smtpHost ="
                    + smtpHostName + " \n fromPropertiesFile=" + fromPropertiesFile + " \n password=" + password
                    + " \n smtpPort=" + smtpPort + " \n to=" + Arrays.toString(to) + " \n isSSString=" + this.smtpSSL
                    + " \n subject=" + subject + " \n smtpUserName=" + smtpUserName);
        }

        report.report("sending mail:  \n smtpHost =" + this.smtpHostName + " \n fromPropertiesFile="
                + fromPropertiesFile + " \n smtpPassword=" + this.password + " \n smtpPort=" + this.smtpPort
                + " \n to=" + Arrays.toString(to) + " \n smtpSSL=" + this.smtpSSL + " \n subject=" + subject
                + " \n smtpUserName=" + this.smtpUserName);

        this.setUserName(smtpUserName);
        if (filesToAttach != null) {
            this.setAttachments(filesToAttach);
            report.report("attached files: " + Arrays.toString(filesToAttach));
        }
        report.report("is HTML: " + messageAsHtmlText);
        this.setMailMessageAsHtmlText(messageAsHtmlText);
        report.report("mail body: \n"+msgBody);
        this.sendMail(subject, msgBody);
        report.stopLevel();
    }

    /**
     * Sending mails from code (all mail parameter takes from jsystem.properties
     * file)
     *
     * @throws Exception
     */
    public void sendEmail(String subject) throws Exception {
        String mailTo = jSystemProperties.getPreference("mail.sendTo");
        sendEmail(new String[]{mailTo}, subject, "", null, false);
    }

    /**
     * Sending mails from code (all mail parameter takes from jsystem.properties
     * file)
     *
     * @throws Exception
     */
    public void sendEmail(String[] to, String subject, String msgBody) throws Exception {
        sendEmail(to, subject, msgBody, null, false);
    }

//    public void setAttachmentsDir(String attachmentsDir) {
//        if (attachmentsDir == null) {
//            String OS = (System.getProperty("os.name")).toUpperCase();
//            if (OS.contains("WIN")) {
//                super.setAttachmentsDir("C:");
//            } else {
//                super.setAttachmentsDir("/tmp");
//            }
//        }
//    }

    /**
     * Sending mails from code (all mail parameter takes from jsystem.properties
     * file)
     *
     * @throws Exception
     */
    public void sendEmail(String[] to, String subject, String msgBody, boolean messageAsHtmlText) throws Exception {
        sendEmail(to, subject, msgBody, null, messageAsHtmlText);
    }

    /**
     * Sending mails from code (all mail parameter takes from jsystem.properties
     * file)
     *
     * @throws Exception
     */
    public void sendEmail(String subject, String msgBody) throws Exception {
        String mailTo = jSystemProperties.getPreference("mail.sendTo");
        sendEmail(new String[]{mailTo}, subject, msgBody, null, false);
    }

    /**
     * Sending mails from code (all mail parameter takes from jsystem.properties
     * file)
     *
     * @throws Exception
     */
    public void sendEmail(String subject, String[] filesToAttach) throws Exception {
        String mailTo = jSystemProperties.getPreference("mail.sendTo");
        sendEmail(new String[]{mailTo}, subject, "", filesToAttach, false);
    }

    /**
     * Sending emails from code (all mail parameter takes from jsystem.properties
     * file)
     *
     * @throws Exception
     */
    public void sendEmail(String subject, String msgBody, String[] filesToAttach) throws Exception {
        String mailTo = jSystemProperties.getPreference("mail.sendTo");
        sendEmail(new String[]{mailTo}, subject, msgBody, filesToAttach, false);
    }

    /**
     * reading all emails from axxana email box
     *
     * @return
     * @throws Exception
     */
    public EmailMessage[] getAllAxxanaEMails(String sentFromIdentificator) throws Exception {
        return this.getAllEMails(false, sentFromIdentificator);
    }

    /**
     * reading all emails from axxana guest email box
     *
     * @return
     * @throws Exception
     */
    public EmailMessage[] getAllAxxanaGuestEMails(String sentFromIdentificator) throws Exception {
        return this.getAllEMails(true, sentFromIdentificator);
    }

    /**
     * get all last received emails from axxana Gmail account
     *
     * @param timeRange             - last time in minutes (receive all mails newer than X minutes back)
     *                              contains range and range units (10 , MINUTES or 100 hours)
     * @param sentFromIdentificator - the sender identificator string (as its configured in axxana install Script)
     *                              from axxana install script: "Enter the customer name to be used in events notification"
     * @return
     */
    public EmailMessage[] getAllLastAxxanaEMails(TimeRange timeRange, String sentFromIdentificator, String[] emailPatternsList) throws Exception {
        return this.getAllLastEMails(timeRange, sentFromIdentificator, emailPatternsList, false);
    }

    /**
     * get all last received emails from axxana guest Gmail account
     * *                  contains range and range units (10 , MINUTES or 100 hours)
     *
     * @param timeRange             - last time in minutes (receive all mails newer than X minutes back)
     * @param sentFromIdentificator - the sender identificator string (as its configured in axxana install Script)
     *                              from axxana install script: "Enter the customer name to be used in events notification"
     * @return
     */
    public EmailMessage[] getAllLastAxxanaGuestEMails(TimeRange timeRange, String sentFromIdentificator, String[] emailPatternsList) throws Exception {
        return this.getAllLastEMails(timeRange, sentFromIdentificator, emailPatternsList, true);

    }

    /**
     * get all last received emails from one of axxana Gmail accounts : axxana or Axxana guest
     * *                  contains range and range units (10 , MINUTES or 100 hours)
     *
     * @param timeRange             - last time in minutes (geceive all mails newer than X minutes back)
     * @param sentFromIdentificator - the sender identificator string (as its configured in axxana install Script)
     *                              from axxana install script: "Enter the customer name to be used in events notification"
     * @param emailPatternsList     - list of mail subject patterns to look for
     * @param isAxxanaGuest
     * @return
     */
    public EmailMessage[] getAllLastEMails(TimeRange timeRange, String sentFromIdentificator, String[] emailPatternsList, boolean isAxxanaGuest) throws Exception {
        String userType = isAxxanaGuest ? " Axxana Guest " : " Axxana ";
        report.startLevel("looking for email massages from user: " + userType);
        EmailMessage[] listOfMails = null;
        try {
            listOfMails = this.getAllEMails(isAxxanaGuest, sentFromIdentificator);
        } catch (Exception e) {
            report.report("Failed to featch emails for user: " + userType + " the exception is: " + e.getMessage(), Reporter.FAIL);
            throw e;
        }

        ArrayList<EmailMessage> mailMessagesInRange = new ArrayList<EmailMessage>();
        ArrayList<EmailMessage> listOfMailsInRange = new ArrayList<EmailMessage>();
        EmailMessage[] eMailsInRangeArray = null;

        //WORK AROUND
//        this.setAttachmentsDir(null);
        int outlookServerDateDiff = -4;
        try {
            Date nowDate = CurrentTime.nowDate();
            report.report("current time is: " + nowDate);
            if (listOfMails != null) {
                for (EmailMessage currMail : listOfMails) {
                    Date mailReceivedDay = currMail.getMailMessageObject().getDate();
//                mailReceivedDay=DateManipulation.addTime2Date(mailReceivedDay,outlookServerDateDiff, DateManipulation.DateUnits.HOUR);
                    report.report("mail received time: " + mailReceivedDay);
                    if (timeRange.isDateInRange(mailReceivedDay)) {
                        String currMailSubject = currMail.getMessage();
                        String currMailBody = currMail.getMailMessageObject().getContent();
                        if (currMailBody.contains(sentFromIdentificator)) {
                            if (emailPatternsList != null) {
                                if (StringArrayUtils.isArrayListContainValue(new ArrayList(Arrays.asList(emailPatternsList)), currMailSubject, false, false, true)) {
                                    report.report("adding mail: " + currMail + " to the list");
                                    mailMessagesInRange.add(currMail);
                                }
                            } else {
                                report.report("adding mail: " + currMail + " to the list");
                                mailMessagesInRange.add(currMail);
                            }
                        } else {
                            report.report("Email: " + currMailSubject + " not from source: " + sentFromIdentificator + " so missing this one ...");
                        }
                    }
                }


                report.report("Found Email massages: ");

                eMailsInRangeArray = new EmailMessage[mailMessagesInRange.size()];
                for (int i = 0; i < mailMessagesInRange.size(); i++) {
                    EmailMessage currEmail = mailMessagesInRange.get(i);
                    report.report("Message: " + i + " " + currEmail.getMessage());
                    eMailsInRangeArray[i] = currEmail;
                }
            } else {
                report.report("Emails wasn't found", Reporter.FAIL);
            }
        } catch (Exception e) {
//            throwExceptionIfNeeded(e, reportResult(Reporter.FAIL));
            throw e;
        } finally {
            report.stopLevel();
        }
        return eMailsInRangeArray;
    }


    private EmailMessage[] getAllEMails(boolean isAxxanaGuest, String sentFromIdentificator) throws Exception {
        return getAllEMails(isAxxanaGuest, sentFromIdentificator, 50);
    }

    /**
     * reading all emails from snmpaxxana or snmpclient boxes
     *
     * @param isAxxanaGuest - set true for axxana guest mail user<br>
     *                      set false for axxana mail user
     * @return
     * @throws Exception
     */
    private EmailMessage[] getAllEMails(boolean isAxxanaGuest, String sentFromIdentificator, int lastMailsCount) throws Exception {
        report.startLevel("reading emails for user: " + popUserName + " only last: " + lastMailsCount + " mails...");
        String emailHost = !isAxxanaGuest ? popUserName : popGuestUser;
        EmailMessage[] receivedEmails = null;
        //WORK AROUND
//        this.setAttachmentsDir(null);
        try {


            this.setUserName(emailHost);
            this.setPassword(popPassword);
            this.setPopHost(popHost);
            this.setPopPort(popPort);
            this.setSsl(popSSL);

            if (userName == null || password == null || this.popHost == null || this.popPort == 0) {

                throw new Exception("Not all email param configured properly (one of those is null) : \n userName ="
                        + userName + " \n password=" + password + " \n popHost=" + popHost + " \n popPort=" + popPort
                        + " \n ssl=" + ssl + " \n ");
            }


            MailMessage[] receivedMails = this.getMail("", lastMailsCount, false);
            receivedEmails = new EmailMessage[receivedMails.length];
            for (int i = 0; i < receivedMails.length; i++) {
                MailMessage currMail = receivedMails[i];
//            report.report("currmail" + currMail);
                receivedEmails[i] = new EmailMessage(currMail, sentFromIdentificator);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            report.stopLevel();
        }

        return receivedEmails;
    }

}
