package com.cedex.text;

import com.cedex.jsystem.ReporterLight;
import com.cedex.numbers.Randomalizator;
import jsystem.framework.report.Reporter;

import java.util.HashMap;
import java.util.Map;

/**
 * string utils class
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 *
 * @author $Author: romang $
 * @version $Revision: 12123 $
 */
public class StringUtils extends jsystem.utils.StringUtils implements ReporterLight {

    static final int ALL_ASCII = 1;
    static final int MOSTLY_ASCII = 2;
    static final int MOSTLY_NONASCII = 3;


    public static enum StringOperator {
            EQUALS("EQUALS"),NOT_EQUALS("NOT_EQUALS"),CONTAINS("CONTAINS"),NOT_CONTAINS("NOT_CONTAINS"),START_WITH("START_WITH"),NOT_START_WITH("NOT_START_WITH"),END_WITH("END_WITH"),NOT_END_WITH("NOT_END_WITH");


        private String stringOperator;

        private StringOperator(String stringOperator) {
            this.stringOperator = stringOperator;
        }

        private String getStringOperator() {
            return stringOperator;
        }

        private static final Map<String, StringOperator> lookup = new HashMap<String, StringOperator>();
        static {
            for (StringOperator currStatus : StringOperator.values())
                lookup.put(currStatus.getStringOperator(), currStatus);
        }

        public static StringOperator getEnumFromString(String dgStatus) {
            return lookup.get(dgStatus);
        }


    }

    public static boolean  stringCompare(String str1 , String str2 , StringOperator stringOperator) {
        boolean isStringAreTrue=false;

        switch (stringOperator) {

            case CONTAINS: {
                if (str1.contains(str2)) {
                    isStringAreTrue=true;
                }
                break;
            }
            case NOT_CONTAINS: {
                if (!str1.contains(str2)) {
                    isStringAreTrue=true;
                }
                break;
            }
            case NOT_EQUALS: {
                if (!str1.equals(str2)) {
                    isStringAreTrue=true;
                }
                break;
            }
            case START_WITH: {
                if (str1.startsWith(str2)) {
                    isStringAreTrue=true;
                }
                break;
            }
            case NOT_START_WITH: {
                if (!str1.startsWith(str2)) {
                    isStringAreTrue=true;
                }
                break;
            }
            case END_WITH: {
                if (str1.endsWith(str2)) {
                    isStringAreTrue=true;
                }
                break;
            }
            case NOT_END_WITH: {
                if (!str1.endsWith(str2)) {
                    isStringAreTrue=true;
                }
                break;
            }
            default: {
                //Equals case
                if (str1.equals(str2)) {
                    isStringAreTrue=true;
                }
            }
        }
        report.report(" Compare: "+str1+" and "+str2 +" should be: "+stringOperator +" result is: "+isStringAreTrue);
        return isStringAreTrue;
    }
    static public boolean nonascii(int b) {
//		report.report("char int: "+b);
        return b >= 0177 || (b < 040 && b != '\r' && b != '\n' && b != '\t');
    }

    static public String replaceNonAssciis(String s) {
        int l = s.length();
        StringBuilder stringBuilder = new StringBuilder(s);

        for (int i = 0; i < l; i++) {
            if (nonascii((int) s.charAt(i))) {// non-ascii
                stringBuilder.setCharAt(i, ' ');
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Check if the given string contains non US-ASCII characters.
     *
     * @param s string
     * @return ALL_ASCII if all characters in the string
     * belong to the US-ASCII charset. MOSTLY_ASCII
     * if more than half of the available characters
     * are US-ASCII characters. Else MOSTLY_NONASCII.
     */
    static public int checkAscii(String s) {
        int ascii = 0, non_ascii = 0;
        int l = s.length();

        for (int i = 0; i < l; i++) {
            if (nonascii((int) s.charAt(i))) {// non-ascii
                report.report("not ASCI CHAR:" + s.charAt(i));
                non_ascii++;
            } else
                ascii++;
        }

        if (non_ascii == 0)
            return ALL_ASCII;
        if (ascii > non_ascii)
            return MOSTLY_ASCII;

        return MOSTLY_NONASCII;
    }

    /**
     * validate ip v4 address
     *
     * @param ipAddress ip address from type v4
     * @return
     * @throws Exception
     */
    public static boolean ipValidationV4(String ipAddress) throws Exception {
        String ipRegular =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        RegExpr regExpr = new RegExpr(ipAddress, ipRegular);
        if (regExpr.isFound()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * get all chars of english alphabet
     *
     * @return
     */
    public static String getAlphabetEnglish() {
        return "abcdefghijklmnopqrstuvwxyz";
    }

    /**
     * the same as replaseFirst of String class but do ut from the end of
     * string
     *
     * @param string
     * @param substring
     * @param replacement
     * @return
     */
    public static String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1)
            return string;
        return string.substring(0, index) + replacement + string.substring(index + substring.length());
    }

    /**
     * converts hexa string to string of integers <br>
     * <p>
     * read every 2 chars as one Hexa converts it to integer <br>
     * and append it to String
     *
     * @param stringInHexa
     * @return
     */
    public static String convertHexToASCIIString(String stringInHexa) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < stringInHexa.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = stringInHexa.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);

            // convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        // System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }

    /**
     * converts string of hexa chars from little endian to big and vice versa <br>
     * example: <br>
     * 12ABF4 --> F4AB12
     *
     * @param littleHexa
     * @return
     */
    public static String littleEndian2BigEndian(String littleHexa) {
        String bigHexa = "";
        int stringSize = littleHexa.length();
        if (stringSize % 2 == 0) {
            for (int i = littleHexa.length() - 2; i >= 0; i = i - 2) {
                bigHexa = bigHexa + littleHexa.substring(i, i + 2);
            }
        } else {
            report.report("String:" + littleHexa + " have odd lenth");
        }
        return bigHexa;
    }

    /**
     * converts string to boolean value (true, false string are supported case not sensitive)
     *
     * @param stringToConvert
     * @return
     */
    public static boolean stringToBoolean(String stringToConvert) {
        if (stringToConvert.equalsIgnoreCase("true") || stringToConvert.equalsIgnoreCase("false")) {
            return Boolean.valueOf(stringToConvert);
            // do something
        }
        report.report("Failed to Convert string:" + stringToConvert + " to boolean value", Reporter.FAIL);
        return false;
    }

    /**
     * replace multiple spaces by one space
     *
     * @param stringToReplaceIn
     * @return
     * @throws Exception
     */
    public static String replaceMultipleWhiteSpaces(String stringToReplaceIn) throws Exception {
        return stringToReplaceIn.replaceAll("\\s+", " ");
    }

    /**
     * replace all spaces in string
     *
     * @param stringToReplaceIn - string to replace spaces into
     * @param replaceByChar     - all spaces will be replaced by this char
     * @return
     */
    public static String replaceAllWhiteSpaces(String stringToReplaceIn, String replaceByChar, boolean removeAllOrSequences) {
        // return stringToReplaceIn.replaceAll("\\s+", replaceByChar);
        return StringUtils.replaceAll(stringToReplaceIn, "\\s+", replaceByChar, removeAllOrSequences);
    }

    /**
     * replace all spaces in string
     *
     * @param stringToReplaceIn - string to replace spaces into
     * @param replaceByChar     - all spaces will be replaced by this char
     * @return
     */
    public static String replaceAllWhiteSpaces(String stringToReplaceIn, String replaceByChar) {
        // return stringToReplaceIn.replaceAll("\\s+", replaceByChar);
        return StringUtils.replaceAll(stringToReplaceIn, "\\s+", replaceByChar, true);
    }

    /**
     * replace all chars from string and replace it by others
     *
     * @param stringToReplaceIn
     * @param replaceRegexpr
     * @param replaceByChar
     *
     * @return
     */
    public static String replaceAll(String stringToReplaceIn, String replaceRegexpr, String replaceByChar) {
        return replaceAll(stringToReplaceIn, replaceRegexpr, replaceByChar, true);
    }

    /**
     * replace all chars from string and replace it by others
     *
     * @param stringToReplaceIn
     * @param replaceRegexpr
     * @param replaceByChar
     *
     * @return
     */
    public static String replaceAll(String stringToReplaceIn, String replaceRegexpr, String replaceByChar, boolean removeAllOrSequences) {
        String replaceBy = removeAllOrSequences ? "" : replaceByChar;
        replaceRegexpr = isLastChareEqualTo(replaceRegexpr, '+') || isLastChareEqualTo(replaceRegexpr, '*') ? replaceRegexpr : replaceRegexpr
                + "+";

        return stringToReplaceIn.replaceAll(replaceRegexpr, replaceBy).trim();
    }

    /**
     * get from full Folder path or full file path folder delimiter <br>
     * for example: for Win input: c:\roman.txt - return \ <br>
     * for Lin input: /roman.txt - return / <br>
     * <p>
     * OS INDIPENDET FUNC
     *
     * @param osPath - any full path to folder or file
     * @return
     */
    public static char getOSFileDelimeterFromString(String osPath) {
        char folderDelimiter = '\t';
        if (osPath.lastIndexOf("\\") > 0) {
            folderDelimiter = '\\';
        } else if (osPath.lastIndexOf("/") > 0) {
            folderDelimiter = '/';
        } else {
            report.report("no delimeters found");
        }

        return folderDelimiter;
    }

    /**
     * func receiving String contains special charachters and adds before
     * every special char \ (back slash)
     *
     * @param containsSpecialCharString
     * @return
     */
    public static String specialCharactersNormalizer(String containsSpecialCharString) {
        String normalizedString = "";
        String charNormalizer = "\\";

        for (int i = 0; i < containsSpecialCharString.length(); i++) {
            char currChar = containsSpecialCharString.charAt(i);

            if (StringArrayUtils.isStringArayContainValue(StringArrayUtils.getSpecialCharStringArray(), currChar + "")) {
                normalizedString = normalizedString + charNormalizer;
            }
            normalizedString = normalizedString + currChar;
        }
        return normalizedString;
    }

    /**
     * build string from the main one by retrieval random chars
     *
     * @param mainString     - the base string of characters
     * @param numOfRetrieval - number of randomly retrieval
     * @return
     */
    public static String createSubStringRandom(String mainString, int numOfRetrieval) {
        String builtSubString = "";
        for (int i = 0; i < numOfRetrieval; i++) {
            int currRandom = Randomalizator.getRandomNumber(mainString.length());
            builtSubString = builtSubString + mainString.charAt(currRandom);
        }
        return builtSubString;
    }

    /**
     * checking if last char of string equal to specific char
     *
     * @param lookingIn the string for looking into
     * @param lookFor   - the char to look for
     * @return
     */
    public static boolean isLastChareEqualTo(String lookingIn, char lookFor) {
        if (lookingIn.charAt(lookingIn.length() - 1) == lookFor) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * count of instances of specific character in any String
     *
     * @param stringToLookInto - string to looking the char into
     * @param lookForChar      - looking for char
     * @return
     */
    public static int countCharInString(String stringToLookInto, char lookForChar) {
        int charCount = 0;
        for (char c : stringToLookInto.toCharArray()) {
            if (c == lookForChar) {
                charCount++;
            }
        }
        return charCount;
    }

    /**
     * count of instances of specific sub string in any String
     *
     * @param stringToLookInto - string to looking the char into
     * @param lookForSubString - looking for char
     * @return
     */
    public static int countSubStringInString(String stringToLookInto, String lookForSubString) {

        int lastIndex = 0;
        int stringCount = 0;

        while (lastIndex != -1) {

            lastIndex = stringToLookInto.indexOf(lookForSubString, lastIndex);

            if (lastIndex != -1) {
                stringCount++;
                lastIndex += lookForSubString.length();
            }
        }
        return stringCount;
    }

}
