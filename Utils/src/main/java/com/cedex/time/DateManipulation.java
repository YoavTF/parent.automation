package com.cedex.time;


import com.cedex.jsystem.ReporterLight;
import org.pojava.datetime.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date Manipulation class<br>
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 *
 * @author $Author: romang $
 * @version $Revision: 11458 $
 */
public abstract class DateManipulation implements ReporterLight {

    public enum DateUnits {
        MILISECOND("ms"), SECOND("s"), MINUTE("m"), HOUR("h"), DAY("d"), WEEK("w"), MONTH("M"), YEAR("y");

        private String dateUnits;

        private DateUnits(String dateUnits) {
            this.dateUnits = dateUnits;
        }

        private String getDateUnits() {
            return dateUnits;
        }

        private static final Map<String, DateUnits> lookup = new HashMap<String, DateUnits>();
        static {
            for (DateUnits currDateUnit : DateUnits.values())
                lookup.put(currDateUnit.getDateUnits(), currDateUnit);
        }

        public static DateUnits getEnumFromString(String dateUnits) {
            return lookup.get(dateUnits);
        }


    }

    /**
     * get difference between to dates if first date less than a second one
     * (you will get a negative result)
     *
     * @param dateEarlier - first date to compare
     * @param dateOlder   - second date to compare
     * @param dateFormat  - format of received dateEarlier,dateOlder
     * @param diffUnits   - defference unites (supported values: MILISECOND, SECOND,
     *                    MINUTE, HOUR, DAY)
     * @return
     * @throws Exception
     */
    public static long dateDiff(Date dateEarlier, Date dateOlder, String dateFormat, DateUnits diffUnits) throws Exception {

        return dateDiff(date2String(dateEarlier, dateFormat), date2String(dateOlder, dateFormat), dateFormat, diffUnits);
    }

    /**
     * get difference between to dates if first date less than a second one
     * (you will get a negative result)
     *
     * @param dateEarlier - first date to compare
     * @param dateOlder   - second date to compare
     * @param dateFormat  - format of received dateEarlier,dateOlder
     * @param diffUnits   - difference unites (supported values: MILISECOND, SECOND,
     *                    MINUTE, HOUR, DAY)
     * @return
     * @throws Exception
     */
    public static long dateDiff(String dateEarlier, String dateOlder, String dateFormat, DateUnits diffUnits) throws Exception {
        long diffTime = 0;
        try {
            Calendar calendar1 = string2Calendar(dateEarlier, dateFormat); // Calendar.getInstance();string2Calendar
            Calendar calendar2 = string2Calendar(dateOlder, dateFormat); // Calendar.getInstance();
            long milliseconds1 = calendar1.getTimeInMillis();
            long milliseconds2 = calendar2.getTimeInMillis();
            long diff = Math.abs(milliseconds2 - milliseconds1);
            long diffSeconds = diff / 1000;
            long diffMinutes = diff / (60 * 1000);
            long diffHours = diff / (60 * 60 * 1000);
            long diffDays = diff / (24 * 60 * 60 * 1000);

            switch (diffUnits) {
                case MILISECOND:
                    diffTime = diff;
                    report.report("time/date diff between: " + dateEarlier + " to: " + dateOlder + " is: " + diffTime + " " + diffUnits.toString()
                            + " S");
                    break;
                case SECOND:
                    diffTime = diffSeconds;
                    report.report("time/date diff between: " + dateEarlier + " to:" + dateOlder + " is: " + diffTime + diffUnits.toString()
                            + " S");
                    break;
                case MINUTE:
                    diffTime = diffMinutes;
                    report.report("time/date diff between: " + dateEarlier + " to:" + dateOlder + " is: " + diffTime + diffUnits.toString()
                            + " S");
                    break;
                case HOUR:
                    diffTime = diffHours;
                    report.report("time/date diff between: " + dateEarlier + " to:" + dateOlder + " is: " + diffTime + diffUnits.toString()
                            + " S");
                    break;
                case DAY:
                    diffTime = diffDays;
                    report.report("time/date diff between: " + dateEarlier + " to:" + dateOlder + " is: " + diffTime + diffUnits.toString()
                            + " S");
                    break;
                case WEEK:
                    report.report("Not supported option:" + diffTime + diffUnits.toString());
                    break;
                case MONTH:
                    report.report("Not supported option:" + diffTime + diffUnits.toString());
                    break;
                case YEAR:
                    report.report("Not supported option:" + diffTime + diffUnits.toString());
                    break;
            }
            System.out.println("\nThe Date Different Example");
            System.out.println("Time in milliseconds: " + diff + " milliseconds.");
            System.out.println("Time in seconds: " + diffSeconds + " seconds.");
            System.out.println("Time in minutes: " + diffMinutes + " minutes.");
            System.out.println("Time in hours: " + diffHours + " hours.");
            System.out.println("Time in days: " + diffDays + " days.");
        } catch (Exception e) {
            throw new Exception("Cant format dates: " + dateEarlier + " or " + dateOlder);
        }
        return diffTime;
    }

    /**
     * add count x time units to data (to add time add positive countToAdd value ,
     * to reduce time add negative countToAdd value
     *
     * @param date          - date to increment
     * @param countToAdd    - int value of how to add
     * @param dateUnitToAdd - date units like min,hour,date to add
     * @return
     * @throws Exception
     */
    public static Date addTime2Date(Date date, int countToAdd, DateUnits dateUnitToAdd) throws Exception {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(date); // sets calendar time/date
        switch (dateUnitToAdd) {
            case HOUR: {
                cal.add(Calendar.HOUR_OF_DAY, countToAdd); // adds one hour
                break;
            }
            case DAY: {
                cal.add(Calendar.DAY_OF_MONTH, countToAdd); // adds one hour
                break;
            }
            case MONTH: {
                cal.add(Calendar.MONTH, countToAdd); // adds one hour
                break;
            }
            default: {
                throw new Exception("not supported option: " + dateUnitToAdd + " in func: addTime2Date");
            }
        }

        Date newDate = cal.getTime(); // returns new date object, one hour in the future

        return newDate;
    }

    /**
     * @param date
     * @param dateFormat
     * @param timeToAdd
     * @return
     * @throws Exception
     */
    public static Date addTime2Date(String date, String dateFormat, String timeToAdd) throws Exception {

        String timeRangeConverted = DateManipulation.convertShortDateStrToLongStrDate(timeToAdd);
        if (timeRangeConverted.equals("NOT_FOUND_OPTION")) {
            throw new Exception("user defined unrecognized time to add option: " + timeToAdd
                    + " possible options are: 12m - for 12 minutes \n or 12h - for 12 hours");
        }

        Calendar calendarOne = string2Calendar(date, dateFormat);
        Calendar calendarOneIncremented = (Calendar) calendarOne.clone();

        String[] timeRangeConvertedAsString = timeRangeConverted.split(" ");
        int timeRangeCount = Integer.parseInt(timeRangeConvertedAsString[0]);
        String timeRangeUnit = timeRangeConvertedAsString[1];

        if (timeRangeUnit.equalsIgnoreCase("second")) {
            calendarOneIncremented.add(Calendar.SECOND, timeRangeCount);
        } else if (timeRangeUnit.equalsIgnoreCase("minute")) {
            calendarOneIncremented.add(Calendar.MINUTE, timeRangeCount);
        } else if (timeRangeUnit.equalsIgnoreCase("hour")) {
            calendarOneIncremented.add(Calendar.HOUR, timeRangeCount);
        } else if (timeRangeUnit.equalsIgnoreCase("day")) {
            calendarOneIncremented.add(Calendar.DATE, timeRangeCount);
        } else if (timeRangeUnit.equalsIgnoreCase("week")) {
            calendarOneIncremented.add(Calendar.WEEK_OF_MONTH, timeRangeCount);
        } else if (timeRangeUnit.equalsIgnoreCase("month")) {
            calendarOneIncremented.add(Calendar.MONTH, timeRangeCount);
        } else if (timeRangeUnit.equalsIgnoreCase("year")) {
            calendarOneIncremented.add(Calendar.YEAR, timeRangeCount);
        }

        return calendar2Date(calendarOneIncremented, dateFormat);
    }

    /**
     * @param date         - the date for calculation (as String)
     * @param dateFormat   - date format
     * @param timeToRemove date to remove from parameter date
     * @return
     * @throws Exception
     */
    public static Date removeTimeFromDate(String date, String dateFormat, String timeToRemove) throws Exception {

        String timeRangeConverted = DateManipulation.convertShortDateStrToLongStrDate(timeToRemove);
        if (timeRangeConverted.equals("NOT_FOUND_OPTION")) {
            throw new Exception("user defined unrecognized time to remove option: " + timeToRemove
                    + " possible options are: 12m - for 12 minutes \n or 12h - for 12 hours");
        }

        Calendar calendarOne = string2Calendar(date, dateFormat);
        Calendar calendarOneIncremented = (Calendar) calendarOne.clone();

        String[] timeRangeConvertedAsString = timeRangeConverted.split(" ");
        int timeRangeCount = Integer.parseInt(timeRangeConvertedAsString[0]);
        String timeRangeUnit = timeRangeConvertedAsString[1];

        if (timeRangeUnit.equalsIgnoreCase("second")) {
            calendarOneIncremented.add(Calendar.SECOND, timeRangeCount * -1);
        } else if (timeRangeUnit.equalsIgnoreCase("minute")) {
            calendarOneIncremented.add(Calendar.MINUTE, timeRangeCount * -1);
        } else if (timeRangeUnit.equalsIgnoreCase("hour")) {
            calendarOneIncremented.add(Calendar.HOUR, timeRangeCount * -1);
        } else if (timeRangeUnit.equalsIgnoreCase("day")) {
            calendarOneIncremented.add(Calendar.DATE, timeRangeCount * -1);
        } else if (timeRangeUnit.equalsIgnoreCase("week")) {
            calendarOneIncremented.add(Calendar.WEEK_OF_MONTH, timeRangeCount * -1);
        } else if (timeRangeUnit.equalsIgnoreCase("month")) {
            calendarOneIncremented.add(Calendar.MONTH, timeRangeCount * -1);
        } else if (timeRangeUnit.equalsIgnoreCase("year")) {
            calendarOneIncremented.add(Calendar.YEAR, timeRangeCount * -1);
        }

        return calendar2Date(calendarOneIncremented, dateFormat);
    }

    /**
     * compares if the range between the date less or equal to received range <br>
     * <br>
     * limitation: <br>
     * 1. firstDate,secondDate should by in the same format
     *
     * @param firstDate       - first date
     * @param secondDate      -second date
     * @param bothDatesFormat - format of dates to future compare to Date
     * @param dateRange       date range possible values: <br>
     *                        3s(second) ,20m(minute) or 123h(hour) or <br>
     *                        13d(day) or 52w(week) or 2m(month) or 1y(year) <br>
     *                        <br>
     * @return true if first date + timeRande < second Date <br>
     * else returns false
     */
    public static boolean isDateInRange(String firstDate, String secondDate, String bothDatesFormat, String dateRange) throws Exception {

        boolean isDateiInRange = false;

        Calendar calendarOne = string2Calendar(firstDate, bothDatesFormat);
        Date dateOneInremented = addTime2Date(firstDate, bothDatesFormat, dateRange);

        String dateIncrementedStr = date2String(dateOneInremented, bothDatesFormat);
        Calendar calendarOneIncremented = string2Calendar(dateIncrementedStr, bothDatesFormat);

        Calendar calendarTwo = string2Calendar(secondDate, bothDatesFormat);

        String calendarOneStr = DateManipulation.calendar2String(calendarOne, bothDatesFormat);
        String calendarIncrementedStr = DateManipulation.calendar2String(calendarOneIncremented, bothDatesFormat);
        String calendarTwoStr = DateManipulation.calendar2String(calendarTwo, bothDatesFormat);

        if (!calendarOne.after(calendarTwo)) {
            if (calendarOneIncremented.after(calendarTwo) || calendarOneIncremented.equals(calendarTwo)) {
                report.report("Date: " + calendarOneStr + " plus: " + dateRange + " = " + calendarIncrementedStr
                        + " its greater (or equal) than " + calendarTwoStr + " so in expected time range");
                isDateiInRange = true;
            } else {
                report.report("Date: " + calendarOneStr + " plus: " + dateRange + " = " + calendarIncrementedStr + " its less then: "
                        + calendarTwoStr + " so not in date range of (" + dateRange + ")");
            }
        } else {
            report.report("Date: " + calendarOneStr + " its greater (future time , before adding time diff of:" + dateRange + ") than "
                    + calendarTwoStr + " so not in time range");
        }
        return isDateiInRange;
    }

    /**
     * converting Date to String
     *
     * @param theDateToConvert - date to converting
     * @param toFormat         - format date to this date format (string will be in this
     *                         format)
     * @return
     */
    public static String date2String(Date theDateToConvert, String toFormat) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat(toFormat);

        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String dateString = df.format(theDateToConvert);
        return dateString;
    }

    /**
     * converts String to Date (without knowing string format)
     *
     * @param dateString string to convert
     * @return
     * @throws Exception
     */
    public static Date string2Date(String dateString) throws Exception {

        Date dateConverted;
        try {
            DateTime dt = new DateTime(dateString);
            dateConverted = dt.toDate();
            report.report("converted date is: " + dateConverted.toString());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return dateConverted;
    }

    /**
     * converts String to Date
     *
     * @param dateString string to convert
     * @param dateFormat - date format that String will be converted to ex.
     *                   ("EEE MMM dd HH:mm:ss z yyyy" or "MMM dd HH:mm yyyy")
     * @return
     * @throws Exception
     */
    public static Date string2Date(String dateString, String dateFormat) throws Exception {
        DateFormat df = new SimpleDateFormat(dateFormat);

        Date dateConverted;
        try {
            dateConverted = df.parse(dateString);
            report.report("Today = " + df.format(dateConverted));

        } catch (ParseException e) {
            throw new Exception(e.getMessage());
        }
        return dateConverted;
    }

    /**
     * converts Calendar to String
     *
     * @param calendarDate Calendar to convert
     * @param newFormat    - date format that String will be converted to ex.
     *                     ("EEE MMM dd HH:mm:ss z yyyy" or "MMM dd HH:mm yyyy")
     * @return
     * @throws Exception
     */
    public static String calendar2String(Calendar calendarDate, String newFormat) {

        String convertedCalendar = new SimpleDateFormat(newFormat).format(calendarDate.getTime());
        return convertedCalendar;
    }

    /**
     * converts Calendar to Date
     *
     * @param calendarDate calendarDate to convert
     * @param newFormat    - date format that String will be converted to ex.
     *                     ("EEE MMM dd HH:mm:ss z yyyy" or "MMM dd HH:mm yyyy")
     * @return
     * @throws Exception
     */
    public static Date calendar2Date(Calendar calendarDate, String newFormat) throws Exception {

        String calendarAsString = calendar2String(calendarDate, newFormat);
        return string2Date(calendarAsString, newFormat);
    }

    /**
     * converts String to Calendar
     *
     * @param calendarString string to convert
     * @param calendarFormat - date format that String will be converted to ex.
     *                       ("EEE MMM dd HH:mm:ss z yyyy" or "MMM dd HH:mm yyyy")
     * @return
     * @throws Exception
     */
    private static Calendar string2Calendar(String calendarString, String calendarFormat) throws Exception {
        Calendar calendarFormatted = Calendar.getInstance();
        try {

            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat(calendarFormat);
            date = formatter.parse(calendarString);
            calendarFormatted.setTime(date);
            report.report("Today is " + date);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return calendarFormatted;
    }

    /**
     * converts String to Calendar in default format: "dd-MM-yyyy HH:mm:ss"
     *
     * @param calendarString string to convert
     * @return
     * @throws Exception
     * @deprecated - sometimes converting to wrong date (use instead:
     * {@link DateManipulation#string2Calendar(String calendarString, String calendarFormat)}
     */
    @Deprecated
    public static Calendar string2Calendar(String calendarString) throws Exception {
        Calendar calendarFormatted = Calendar.getInstance();
        try {
            DateTime dt = new DateTime(calendarString);
            Date date = dt.toDate();
            report.report("Today is " + date);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return calendarFormatted;
    }

    /**
     * function splitting time parameter to string <br>
     * example user defines parameter like: <br>
     * <p>
     * 3s(second) ,20m(minute) or 123h(hour) or <br>
     * 13d(day) or 52w(week) or 2m(month) or 1y(year) <br>
     * <p>
     * will return String minute,hour <br>
     *
     * @param timeString - time string parameter (Ex. 1h or 5m)
     */
    public static String convertShortDateStrToLongStrDate(String timeString) {
        Pattern p = Pattern.compile("([0-9]+)([s|m|h|d|w|m|y])", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(timeString);
        String foundString = "";
        if (m.find()) {
            if (m.group(0) != null) {
                foundString = m.group(1);
                if (m.group(2).equalsIgnoreCase("s")) {
                    foundString = foundString + " second";
                } else if (m.group(2).equalsIgnoreCase("m")) {
                    foundString = foundString + " minute";
                } else if (m.group(2).equalsIgnoreCase("h")) {
                    foundString = foundString + " hour";
                } else if (m.group(2).equalsIgnoreCase("d")) {
                    foundString = foundString + " day";
                } else if (m.group(2).equalsIgnoreCase("w")) {
                    foundString = foundString + " week";
                } else if (m.group(2).equalsIgnoreCase("m")) {
                    foundString = foundString + " month";
                } else if (m.group(2).equalsIgnoreCase("y")) {
                    foundString = foundString + " year";
                } else {
                    foundString = "NOT_FOUND_OPTION";
                }
                System.out.println("converted time range param: " + foundString);
            }
        } else {
            report.report("Error: can't recognize time range parameter: " + timeString);
        }
        return foundString;
    }

    /**
     * converts date string to user defined format <br>
     * <br>
     * limitation: <br>
     * sometimes function will fail , try to use other format function
     *
     * @param unFormattedDate - unformatted date's string -
     * @param newDateFormat   new format ex. ("EEE MMM dd HH:mm:ss z yyyy" or
     *                        "MMM dd HH:mm yyyy")
     * @return formatted string
     */
    public static String format(String unFormattedDate, String newDateFormat) {
        String formattedDate = "";
        try {
            DateTime dt = new DateTime(unFormattedDate);
            Date date = dt.toDate();
            DateFormat df = new SimpleDateFormat(newDateFormat);
            formattedDate = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * converts date string to user defined format <br>
     * <br>
     * limitation: <br>
     * sometimes function will fail , try to use other format function
     *
     * @param unFormattedDate         - unformatted date string (should be formatted to any date
     *                                format)
     * @param formatOfUnformattedDate - format of unformatted date's string
     * @param newDateFormat           new format ex. ("EEE MMM dd HH:mm:ss z yyyy" or
     *                                "MMM dd HH:mm yyyy")
     * @return formatted string
     */
    public static String format(String unFormattedDate, String formatOfUnformattedDate, String newDateFormat) throws ParseException {
        String formattedDate = "";

        DateFormat readFormat = new SimpleDateFormat(formatOfUnformattedDate, Locale.US);

        DateFormat writeFormat = new SimpleDateFormat(newDateFormat, Locale.US);
        Date date = null;
        try {
            date = readFormat.parse(unFormattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            formattedDate = writeFormat.format(date);
        }

        System.out.println(formattedDate);

        return formattedDate;
    }
}
