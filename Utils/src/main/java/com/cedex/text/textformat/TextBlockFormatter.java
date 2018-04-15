package com.cedex.text.textformat;

import com.cedex.jsystem.ReporterLight;
import jsystem.framework.report.Reporter;

public class TextBlockFormatter implements ReporterLight {

    public static int printHeader(String headerToPrint, char borderChar, boolean boldText) {

        return printHeader(headerToPrint, borderChar, -1, boldText);
    }

    public static int printHeader(String headerToPrint, char borderChar, int longestHeaderCount, boolean boldText) {
        int additionHorisontCharsCount = 6;
        int extraChars = 20;
        int maxLength = headerToPrint.length() > longestHeaderCount ? headerToPrint.length() : longestHeaderCount;
        maxLength = maxLength + additionHorisontCharsCount + extraChars;
        String topBorder = "";
        String downBorder = "";
        String leftBorder = "";
        String rigthBorder = "";

        for (int i = 0; i < maxLength; i++) {
            topBorder += borderChar;
        }
        for (int i = 0; i < maxLength; i++) {
            downBorder += borderChar;
        }
//        for (int i = 0; i < additionHorisontCharsCount / 2; i++) {
//            leftBorder += "-";
//        }
//        for (int i = 0; i < additionHorisontCharsCount / 2; i++) {
//            rigthBorder += "-";
//        }

//        int spacesCount = maxLength - headerToPrint.length()-additionHorisontCharsCount;
        String spaces = "";
//        for (int i = 0; i < spacesCount / 2; i++) {
//            spaces += "-";
//        }
        headerToPrint = leftBorder + spaces + headerToPrint;
        headerToPrint = headerToPrint + spaces + rigthBorder;
        report.report("\n\n\n\n\n");
        report.report(topBorder);
        if (boldText)
            report.report(headerToPrint, Reporter.ReportAttribute.BOLD);
        else
            report.report(headerToPrint);
        report.report(downBorder);
        return maxLength;
    }
}
