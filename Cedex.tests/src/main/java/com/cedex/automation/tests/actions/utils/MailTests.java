package com.cedex.automation.tests.actions.utils;


import com.cedex.automation.tests.base.AbstractBaseTest;
import com.cedex.fortests.SutUtils;
import com.cedex.jsystem.JsystemPropertiesLight;
import com.cedex.mail.Mailer;
import com.cedex.mail.message.EmailMessage;
import jsystem.framework.TestProperties;
import jsystem.framework.report.Reporter;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Sending/receiving Mails class
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 * 
 * @author $Author: romang $
 * @version $Revision: 8874 $
 * 
 */
public class MailTests extends AbstractBaseTest implements JsystemPropertiesLight {

	protected String[] to;
	protected String from = jSystemProperties.getPreference("mail.from");
	protected String subject = jSystemProperties.getPreference("mail.subject");
	protected String body = "";
//	private emailPossibleContent emailsLike = emailPossibleContent.ALL;
	private String emailsLikeOpenText;

	protected Mailer mailer;

//	public enum emailPossibleContent {
//		ALL("OTHER"), BBX_DOWN_DUE_HIGT_TEMPERATURE("Black Box shutting down due to extremely high temperature."), BBX_UP_DUE_NORMAL_TEMPERATURE(
//				"Black Box has returned to operational temperature. Black Box is still in Bypass mode."), LOW_DISK_SPACE_ALLERT(
//				"Low disk space alert. Once disk is full, Axxana protection will not be available."), LOW_DISK_SPACE_ALLERT_OFF(
//				"Low disk space alert is off."), BBX_BATTERY_FAILED("Black Box battery has FAILED."), BBX_BATTERY_OK(
//				"Black Box battery is OK."), BBX_TEMPERATURE_SENSOR_FAILED("Black Box temperature sensor has FAILED."), BBX_TEMPERATURE_SENSOR_OK(
//				"Black Box temperature sensor is OK."), BBX_BATTERY_SENSOR_FAILED(
//				"Black Box battery sensor has FAILED."), BBX_BATTERY_SENSOR_OK("Black Box battery sensor is OK."), BBX_CELLULAR_FAILED(
//				"Black Box wireless cellular has FAILED."), BBX_CELLULAR_OK("Black Box wireless cellular is OK."), BBX_SSD_FAILED(
//				"Black Box SSD has FAILED."), BBX_SSD_OK("Black Box SSD is OK."), SYSTEM_IN_FAILOVER(
//				"System entered Failover mode."), SYSTEM_IN_BACK_NORMAL_FROM_FAILOVER(
//				"System has exited Failover mode and returned to Normal mode."), SYSTEM_IN_BYPASS(
//				"System has entered Bypass mode."), SYSTEM_IN_BACK_NORMAL_FROM_BYPASS(
//				"System has exited Bypass mode and returned to Normal mode."), SYSTEM_IN_DR_TEST(
//				"System entered DR Test mode."), SYSTEM_IN_BACK_NORMAL_FROM_BYPASS_FROM_DR_TEST(
//				"System has exited DR Test mode and returned to Normal mode.");
//
//		private final String emailContent;
//
//		private emailPossibleContent(String emailContent) {
//			this.emailContent = emailContent;
//		}
//
//		/**
//		 * @return the linuxLog
//		 */
//		public String getEmailContent() {
//			return emailContent;
//		}
//
//	}

	@Before
	public void init() throws Exception {
		if (this.mailer == null) this.mailer = new Mailer();
	}

	/**
	 * send execution summary email <br>
	 * parameters like: host,user,password,port takes from jsystem.properties
	 * file
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	@TestProperties(name = "MAIL: Send Email:${subject}", paramsExclude = { "emailsLike", "emailsLikeOpenText" })
	public void send() throws Exception {
		to = new String[] { "romang@cedex.com" };
		sendLocal();
	}

	/**
	 * retrieve all mails contain string defined by: emailsLike /
	 * emailsLikeOpenText parameter <br>
	 * and <br>
	 * received after GREP_EMAIL_TIME_MARKER <br>
	 * (don't forget use setTimemarker BB) <br>
	 * 
	 * <b><font color=green> <li>note<br>
	 * 1. test pass if found at least one mail <br>
	 * if not found mails test failed <br>
	 * 2. <li>if param emailsLikeOpenText not defined using emailsLike (default
	 * value all) <br>
	 * <br><li>if emailsLikeOpenText and emailsLike - using emailsLikeOpenText
	 * param 3. be sure that customer name (defined it while install script)
	 * contains you env name : like env3... (don't use env03)
	 * 
	 * </font><b>
	 * 
	 * <br>
	 * 
	 * @throws Exception
	 */
	@Test
	@TestProperties(name = "MAIL: retrieve all mails contain:${emailsLike} and ${emailsLikeOpenText} ", paramsExclude = {
			"to", "from", "subject", "body" })
	public void grepMailFromTimeMarker() throws Exception {
		getAllMails();
	}

	/**
	 * reading all mails by default from gmail account
	 * 
	 * @throws Exception
	 */
	public void getAllMails() throws Exception {
		init();
//		String lookingForContent = emailsLikeOpenText != null ? "Message:\\s+" + this.emailsLike.getEmailContent()
//				: emailsLikeOpenText;

		String env = SutUtils.getCurrentSUTName();
		String lookingForSentFrom = "Source:\\s*" + env;
		EmailMessage[] reseivedMails = mailer.getAllAxxanaEMails("");
		// MailMessage[] reseivedMails
		int foundMailsCount = 0;
		for (EmailMessage currMail : reseivedMails) {
			boolean isMailFromCondition = false;
			Date mailReceivedDate = currMail.getMailMessageObject().getDate();
//			String timemarker = timeMarkerMap.get(TimeMarkerType.GREP_EMAIL_TIME_MARKER);

			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			Date timeMarkerAsDate = formatter.parse(timemarker);

			// Date timeMarkerAsDate = DateFormat.parse(timemarker);
//			if (timeMarkerAsDate.compareTo(mailReceivedDate) < 0) {
//				RegExpr sentFromRegExpr = new RegExpr(currMail.getMailMessageObject().getContent(), lookingForSentFrom, false);
//				sentFromRegExpr.analyze();
//				if (sentFromRegExpr.isFound()) {
//					if (this.emailsLike != emailPossibleContent.ALL) {
//						RegExpr contentRegExpr = new RegExpr(currMail.getMailMessageObject().getContent(), lookingForContent);
//						contentRegExpr.analyze();
//						if (contentRegExpr.isFound()) {
//							isMailFromCondition = true;
//						}
//					} else {
//						isMailFromCondition = true;
//					}
//
//					if (isMailFromCondition) {
//						foundMailsCount++;
//						report.report("found mail #" + foundMailsCount + " like: " + lookingForContent + " " + currMail);
//					}
//				}
//			}
		}
		if (foundMailsCount > 0) {
//			report.report("Found: " + foundMailsCount + " like: " + lookingForContent, Reporter.PASS);
            report.report("Found: " + foundMailsCount, Reporter.PASS);
		} else {
//			report.report("Found: " + foundMailsCount + " like: " + lookingForContent,Reporter.FAIL);
            report.report("Found: " + foundMailsCount,Reporter.FAIL);
		}

	}

	/**
	 * send mail without attachments <br>
	 * parameters like: host,user,password,port takes from jsystem.properties
	 * file
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void sendLocalAsHTML() throws Exception {
		init();
		//to=new String[] {"roman.glaz@axxana.com"};
		//body="<b><font color=red>RED</font></b>";
		if (to == null) {
			to = new String[] { jSystemProperties.getPreference("mail.sendTo") };
		}

		mailer.setFromAddress(from);
		mailer.sendEmail(to, subject, body, true);
	}

	/**
	 * send mail without attachments <br>
	 * parameters like: host,user,password,port takes from jsystem.properties
	 * file
	 * 
	 *
	 * @throws Exception
	 */
	public void sendLocal() throws Exception {
		init();
		if (to == null) {
			to = new String[] { jSystemProperties.getPreference("mail.sendTo") };
		}
		mailer.setFromAddress(from);
		mailer.sendEmail(to, subject, body);
	}


	/**
	 * send mail without attachments <br>
	 * parameters like: to,from,host user,password takes from jsystem.properties
	 * file
	 * 
	 * @param subject
	 *            email subject
	 * @param body
	 *            - email body
	 * @throws Exception
	 */
	public void send(String subject, String body) throws Exception {
		init();
		mailer.sendEmail(subject, body);
	}

	/**
	 * send mail with attachments <br>
	 * parameters like: to,from,host user,password takes from jsystem.properties
	 * file
	 * 
	 * @param subject
	 *            email subject
	 * @param body
	 *            - email body
	 * @throws Exception
	 */
	public void send(String subject, String body, String[] attachments) throws Exception {
		init();
		mailer.sendEmail(subject, body, attachments);
	}

	/**
	 * @return the to
	 */
	public String[] getTo() {
		return to;
	}

	/**
	 * send to Addresses
	 * 
	 * @param to
	 *            the to to set
	 */
	public void setTo(String[] to) {
		this.to = to;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * email send from field
	 * 
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * email subject field
	 * 
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

//	/**
//	 * @return the emailsLike
//	 */
//	public emailPossibleContent getEmailsLike() {
//		return emailsLike;
//	}
//
//	/**
//	 * looking for mails contain this string (default all)
//	 *
//	 * @param emailsLike
//	 *            the emailsLike to set
//	 *
//	 */
//	public void setEmailsLike(emailPossibleContent emailsLike) {
//		this.emailsLike = emailsLike;
//	}

	/**
	 * @return the emailsLikeOpenText
	 */
	public String getEmailsLikeOpenText() {
		return emailsLikeOpenText;
	}

	/**
	 * put you text here what you want to look for
	 * 
	 * @param emailsLikeOpenText
	 *            the emailsLikeOpenText to set
	 */
	public void setEmailsLikeOpenText(String emailsLikeOpenText) {
		this.emailsLikeOpenText = emailsLikeOpenText;
	}

}
