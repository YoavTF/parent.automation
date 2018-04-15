package com.cedex.text;


import com.cedex.jsystem.ReporterLight;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regular expression simple class <br>
 * 
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 * 
 * @author $Author: romang $
 * @version $Revision: 12077 $
 * 
 */
public class RegExpr implements ReporterLight {

	protected boolean caseSensitive = false;

	protected String title = "";
	protected boolean status;
	protected String message = "";
	private boolean isSilent = false;

	/**
	 * Because we want to do more than simply search for literal pieces of text,
	 * we need to reserve certain characters for special use. In the regex
	 * flavors discussed in this tutorial, there are 11 characters with special
	 * meanings: the opening square bracket [, the backslash \, the caret ^, the
	 * dollar sign $, the period or dot ., the vertical bar or pipe symbol |,
	 * the question mark ?, the asterisk or star *, the plus sign +, the opening
	 * round bracket ( and the closing round bracket ). These special characters
	 * are often called "metacharacters".
	 */
	private static final String[] regexprSpecialCharacters = new String[] { "[", "\\", "^", "$", ".", "|", "?", "*",
			"+", "(", ")" };
	/**
	 * some chars are special , so if those chars incleded in the test the regExpr will fail
	 * to prevent this faileru use this parameter as true
	 */
	protected boolean usedSpecialCharNormalizer=false;
	/**
	 * The group we look for
	 */
	protected int group = 1;

	/**
	 * This is the value of the group we look for
	 */
	protected String counter = null;

	/**
	 * Did we find what we looked for
	 */
	protected boolean isFound = false;

	/**
	 * regexpr as regular expression (true) or regular string (false) function
	 * like: getGroupText,getGroupText,getGroupTextInstances no relevant
	 */
	protected boolean isRegularExpression = true;


	protected Matcher m = null;
	/**
	 * Reg expt to look for
	 */
	protected String regexpr = "";
	protected String text = "";

	public RegExpr(String text, String regexpr) {
//		this.text = text;
//		this.regexpr = regexpr;
//		this.analyze();
		this(text,regexpr,false);
	}

	public RegExpr(String text, String regexpr, boolean caseSesitive) {
//		this.text = text;
//		this.regexpr = regexpr;
//		this.caseSensitive = caseSesitive;
//		this.analyze();
		this(text,regexpr,caseSesitive,true);
	}

	public RegExpr(String text, String regexpr, boolean caseSesitive, boolean isRegularExpression) {
//		this.text = text;
//		this.regexpr = regexpr;
//		this.caseSensitive = caseSesitive;
//		this.isRegularExpression = isRegularExpression;
//		this.analyze();
		this(text,regexpr,caseSesitive,isRegularExpression,false);
		// convertSpecialCharactersFromRegexpr2Rerular();
	}

	public RegExpr(String text, String regexpr, boolean caseSesitive, boolean isRegularExpression, boolean isSilent) {
//		this.text = text;
//		this.regexpr = regexpr;
//		this.caseSensitive = caseSesitive;
//		this.isRegularExpression = isRegularExpression;
//		this.isSilent = isSilent;
//		this.analyze();
		// convertSpecialCharactersFromRegexpr2Rerular();
		this(text,regexpr,caseSesitive,isRegularExpression,isSilent,false);
	}
	public RegExpr(String text, String regexpr, boolean caseSesitive, boolean isRegularExpression, boolean isSilent, boolean useSpecialCharNormalizer) {
		this.text = text;
		this.regexpr = regexpr;
		this.caseSensitive = caseSesitive;
		this.isRegularExpression = isRegularExpression;
		this.isSilent = isSilent;
		if (useSpecialCharNormalizer)
			text=StringUtils.specialCharactersNormalizer(text);
		this.analyze();
		// convertSpecialCharactersFromRegexpr2Rerular();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see jsystem.framework.analyzer.AnalyzerParameter#analyze()
	 */
	public void analyze() {
		status = true; // use isFound instead
		if (text == null) {
			title = "Text to analyze is null";
			isFound = false;
			message = "Text to analyze is null";
		} else {
			message = "Text to find: " + regexpr + System.getProperty("line.separator")
					+ System.getProperty("line.separator") + "Actual text: " + text;
			String found = regexpr;
			Pattern p;
			int group1 = 1;
			// int caseCase = caseSensitive = true ? Pattern. :
			// Pattern.CASE_INSENSITIVE;
			if (this.isRegularExpression) {
				if (!this.caseSensitive) {
					p = Pattern.compile("(" + regexpr + ")", Pattern.CASE_INSENSITIVE);
				} else {
					p = Pattern.compile("(" + regexpr + ")");
				}
			} else {
				p = Pattern.compile(regexpr, Pattern.LITERAL);
				group = 0;
				group1 = 0;
			}

			m = p.matcher(text);
			isFound = m.find();
			if (isFound) {
				if (m.group(group) != null) {
					found = m.group(group);
					title = "The text:<" + regexpr + "> was found";
					counter = found;
				} else {
					found = m.group(group1);
					isFound = false;
					title = "The text:<" + regexpr + "> was found, but group " + group + " was invalid";
				}
				String messageSrc = message;
				String groupsStr = m.group(group1);
				try {
					message = message.replaceAll(regexpr, "<b>" + groupsStr + "</b>");
				} catch (Exception e) {
					// messageSrc =
					// Matcher.quoteReplacement(groupsStr);
					message = messageSrc;
				}
			} // (isFound)
			else {
				title = "The regular:<" + regexpr + "> wasn't found in Text:\n <" + text + ">";
				// message = message.replaceAll(regexpr, "<b>" + +
				// "</b>");
			}
		} // (found some text)
	}

	/**
	 * 
	 * Returns the offset after the last character of the subsequence captured
	 * by the given group during the previous match operation. <br>
	 * 
	 * Capturing groups are indexed from left to right, starting at one. Group
	 * zero denotes the entire pattern, so the expression m.end(0) is equivalent
	 * to m.end().
	 * 
	 * 
	 * Specified by: end in interface MatchResult Parameters: group - The index
	 * of a capturing group in this matcher's pattern Returns: The offset after
	 * the last character captured by the group, or -1 if the match was
	 * successful but the group itself did not match anything Throws:
	 * IllegalStateException - If no match has yet been attempted, or if the
	 * previous match operation failed IndexOutOfBoundsException - If there is
	 * no capturing group in the pattern with the given index
	 */
	public String end() {
		return text.substring(m.end());
	}

	/**
	 * 
	 * Returns the offset after the last character of the subsequence captured
	 * by the given group during the previous match operation. <br>
	 * 
	 * Capturing groups are indexed from left to right, starting at one. Group
	 * zero denotes the entire pattern, so the expression m.end(0) is equivalent
	 * to m.end().
	 * 
	 * 
	 * Specified by: end in interface MatchResult Parameters: group - The index
	 * of a capturing group in this matcher's pattern Returns: The offset after
	 * the last character captured by the group, or -1 if the match was
	 * successful but the group itself did not match anything Throws:
	 * IllegalStateException - If no match has yet been attempted, or if the
	 * previous match operation failed IndexOutOfBoundsException - If there is
	 * no capturing group in the pattern with the given index
	 */
	public String end(int group) {
		return text.substring(m.end(group));
	}

	/**
	 * After using the analyzer, we can get the text of the other groups as
	 * well.<br>
	 * getGroupText(2) would return the captured text of the second group in the
	 * regex.<br>
	 * Note: group 1 is the entire text, group 2 is the first parenthesis.
	 * 
	 * @param group
	 *            - the group to get the text of (for first group set 2)
	 * @param lastMatch
	 *            - if this group found multiple times <br>
	 *            for last match - put true <br>
	 *            for first match put false (the same as function {@Link
	 *            RegExpr#getGroupText(int)}
	 * @return the text of the specific group
	 */
	public String getGroupText(int group, boolean lastMatch) throws Exception {
		ArrayList<String> allGroupMatches = this.getGroupTextInstances(group);
		String foundGroupText = "";
		if (lastMatch) {
			foundGroupText = allGroupMatches.get(allGroupMatches.size() - 1);
		} else {
			foundGroupText = getGroupText(group);
		}
		return foundGroupText;
	}

	/**
	 * After using the analyzer, we can get the text of the other groups as
	 * well.<br>
	 * getGroupText(2) would return the captured text of the second group in the
	 * regex.<br>
	 * Note: group 1 is the entire text, group 2 is the first parenthesis.
	 * 
	 * @param group
	 *            - the group to get the text of (for first group set 2)
	 * @return the text of the specific group
	 */
	public String getGroupText(int group) {
		if (m == null) {
			return null;
		}
		if (group > m.groupCount()) {
			return null;
		}
		return m.group(group);
	}

	public int getGroupCount() {
		return m.groupCount();
	}

	/**
	 * Get specific group text instances
	 * 
	 * @return - all found occurrences of a specific group in our working
	 *         testText
	 */
	public ArrayList<String> getGroupTextInstances() {
		if (m == null) {
			return null;
		}
		ArrayList<String> groupTextList = new ArrayList<String>();
		m.reset();
		while (m.find()) {
			groupTextList.add(m.group(group));
		}
		return groupTextList;
	}

	/**
	 * Get specific group text instances
	 * 
	 * @return - all found occurrences of a specific group in our working
	 *         testText
	 */
	public ArrayList<String> getGroupTextInstances(int group) {
		if (m == null) {
			return null;
		}
		ArrayList<String> groupTextList = new ArrayList<String>();
		m.reset();
		while (m.find()) {
			groupTextList.add(m.group(group));
		}
		return groupTextList;
	}

	/**
	 * Get all groups text instances
	 * 
	 * @return - all found groups text in our working testText
	 */
	public ArrayList<String> getGroupsTextInstances() {
		int groupCount = group;
		int mGroupCount = m.groupCount();
		if (m == null) {
			return null;
		}
		ArrayList<String> groupsTextList = new ArrayList<String>();
		m.reset();
		while (m.find()) {
			while (groupCount <= mGroupCount) {
				groupsTextList.add(m.group(groupCount));
				groupCount++;
			}
		}
		return groupsTextList;
	}

	/**
	 * @return counter - the text we wanted to find
	 */
	public String getCounter() {
		return counter;
	}

	/**
	 * Get whether we found the group we looked for.
	 * 
	 * @return boolean isFound - to reduce log size set false <br>
	 *         (don't print test and regular expression if matched)
	 * @throws Exception
	 */
	public boolean isFound(boolean printIfFound) throws Exception {
		boolean printResult = false;
		if (!isFound) {
			printResult = true;
		} else if (printIfFound) {
			printResult = true;
		}

		if (printResult && !this.isSilent) {
			report.startLevel(this.title + " in text ");
			report.report(this.title + " in text: " + this.text);
			report.stopLevel();
		}
		return isFound;
	}

	/**
	 * Get whether we found the group we looked for.
	 * 
	 * @return boolean isFound
	 * @throws Exception
	 */
	public boolean isFound() throws Exception {
		// report.startLevel(this.title + " in text ");
		// report.report(this.title + " in text: " + this.text);
		// report.stopLevel();
		// return isFound;
		return isFound(true);
	}

	/**
	 * @return the regexprspecialcharacters
	 */
	public static String[] getRegexprspecialcharacters() {
		return regexprSpecialCharacters;
	}
}
