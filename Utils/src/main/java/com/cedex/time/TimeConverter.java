package com.cedex.time;


import com.cedex.GlobalParameters;
import com.cedex.converters.FloatFormatter;
import com.cedex.jsystem.ReporterLight;
import com.cedex.text.RegExpr;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * converting time manipulations
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 *
 * @author $Author: romang $
 * @version $Revision: 12235 $
 */
public abstract class TimeConverter implements ReporterLight, GlobalParameters {

    private static String sharedFormat = "dd-MM-yyyy HH:mm:ss";

    /**
     * converts totalTumRun to value in seconds <br>
     * (from stability,finisar,cable puller tests)
     *
     * @param timeString
     * @return
     * @throws Exception
     */
    public static int convertShortTimeStrToSeconds(String timeString) throws Exception {
        int timeInSeconds = 0;
        int timeAsInt = 0;
        String timeLong = "24 hour";
        if (!timeString.equalsIgnoreCase("n"))
            timeLong = TimeConverter.convertShortTimeStrToLongStrSecMinHour(timeString);

        String[] timeLongAsArr = timeLong.split(" ");
        if (timeLongAsArr.length != 2) {
            throw new Exception("Can't convert convertShortTimeStrToSeconds value:" + timeString);
        }
        String time = timeLongAsArr[0];
        String units = timeLongAsArr[1];
        try {
            timeAsInt = Integer.parseInt(time);
            if (timeAsInt < 0) {
                throw new Exception("received negative" + time + " from:" + timeString + " please check you time string");
            }
        } catch (Exception e) {
            throw new Exception("Can't convert time:" + time + " to Int from:" + timeString + " user defined string value");
        }

        if (units.equals("hour")) {
            timeInSeconds = timeAsInt * 60 * 60;
        } else if (units.equals("minute")) {
            timeInSeconds = timeAsInt * 60;
        } else {
            timeInSeconds = timeAsInt;
        }
        return timeInSeconds;
    }

    /**
     * function splitting time parameter to string <br>
     * example user defines parameter like: <br>
     * 1m or 1h <br>
     * will return String minute,hour <br>
     *
     * @param timeString - time string parameter (Ex. 1h or 5m)
     * @throws Exception
     */
    public static String convertShortTimeStrToLongStrSecMinHour(String timeString) throws Exception {
        String notFoundOptionStr = "NOT_FOUND_OPTION";
        Pattern p = Pattern.compile("(-*[0-9]+)([s|S|m|M|h|H])", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(timeString);
        String foundString = "";
        String foundTime = notFound;
        if (m.find()) {
            if (m.group(0) != null) {
                foundTime = m.group(1);
                if (m.group(2).equalsIgnoreCase("m")) {
                    foundString = foundTime + " minute";
                } else if (m.group(2).equalsIgnoreCase("h")) {
                    foundString = foundTime + " hour";
                } else if (m.group(2).equalsIgnoreCase("s")) {
                    foundString = foundTime + " second";
                } else {
                    foundString = notFoundOptionStr;
                }
                System.out.println("converted time range param: " + foundString);
                if (!foundTime.equalsIgnoreCase(notFoundOptionStr)) {
                    try {

                        int timeAsInt = Integer.parseInt(foundTime);
                        if (timeAsInt < 0) {
                            throw new Exception("received negative" + foundTime + " from:" + timeString
                                    + " please check you time string");
                        }
                    } catch (Exception e) {
                        throw new Exception("Can't convert time:" + foundTime + " to Int from:" + timeString
                                + " user defined string value");
                    }
                }
            }
        } else {
            report.report("Error: can't recognize time range parameter: " + timeString);
        }
        return foundString;
    }

    /**
     * function splitting time parameter to string <br>
     * example user defines parameter like: <br>
     * 1m or 1h <br>
     * will return String minute,hour <br>
     *
     * @param timeString - time string parameter (Ex. 1h or 5m)
     * @throws Exception
     */
    public static String convertShortTimeStrToLongStrSecMinHour(String timeString, boolean secondsToMin) throws Exception {
        String notFoundOptionStr = "NOT_FOUND_OPTION";
        Pattern p = Pattern.compile("(-*[0-9]+)([s|S|m|M|h|H])", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(timeString);
        String foundString = "";
        String foundTime = notFound;
        if (m.find()) {
            if (m.group(0) != null) {
                foundTime = m.group(1);
                if (m.group(2).equalsIgnoreCase("m")) {
                    foundString = foundTime + " minute";
                } else if (m.group(2).equalsIgnoreCase("h")) {
                    foundString = foundTime + " hour";
                } else if (m.group(2).equalsIgnoreCase("s")) {
                    if (secondsToMin) {
                        try {
                            int secInInt = Integer.parseInt(foundTime);
                            if (secInInt < 0) {
                                throw new Exception("Found negative time:" + secInInt + " to Int from:" + timeString
                                        + " user defined string value");
                            }
                            foundString = TimeConverter.seconds2Minutes(secInInt, 2) + " minute";
                        } catch (Exception e) {
                            throw new Exception("Can't convert time:" + foundTime + " to Int from:" + timeString
                                    + " user defined string value");
                        }

                    } else {
                        foundString = foundTime + " second";
                    }
                } else {
                    foundString = "NOT_FOUND_OPTION";
                }
                System.out.println("converted time range param: " + foundString);
                if (!foundTime.equalsIgnoreCase(notFoundOptionStr)) {
                    try {

                        int timeAsInt = Integer.parseInt(foundTime);
                        if (timeAsInt < 0) {
                            throw new Exception("received negative" + foundTime + " from:" + timeString
                                    + " please check you time string");
                        }
                    } catch (Exception e) {
                        throw new Exception("Can't convert time:" + foundTime + " to Int from:" + timeString
                                + " user defined string value");
                    }
                }
            }
        } else {
            report.report("Error: can't recognize time range parameter: " + timeString);
        }
        return foundString;
    }

    /**
     * function splitting time parameter to string <br>
     * example user defines parameter like: <br>
     * 1m or 1h <br>
     * will return String minute,hour <br>
     *
     * @param timeString - time string parameter (Ex. 1h or 5m)
     * @throws Exception
     */
    public static String convertShortTimeStrToLongStrMinHour(String timeString) throws Exception {
        String notFoundOptionStr = "NOT_FOUND_OPTION";
        String foundTime = notFound;
        Pattern p = Pattern.compile("(-*[0-9]+)([m|M|h|H])", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(timeString);
        String foundString = "";

        if (m.find()) {
            if (m.group(0) != null) {
                foundTime = m.group(1);
                if (m.group(2).equalsIgnoreCase("m")) {
                    foundString = foundTime + " minute";
                } else if (m.group(2).equalsIgnoreCase("h")) {
                    foundString = foundTime + " hour";
                } else {
                    foundString = "NOT_FOUND_OPTION";
                }
                System.out.println("converted time range param: " + foundString);
                if (!foundTime.equalsIgnoreCase(notFoundOptionStr)) {
                    try {

                        int timeAsInt = Integer.parseInt(foundTime);
                        if (timeAsInt < 0) {
                            throw new Exception("received negative" + foundTime + " from:" + timeString
                                    + " please check you time string");
                        }
                    } catch (Exception e) {
                        throw new Exception("Can't convert time:" + foundTime + " to Int from:" + timeString
                                + " user defined string value");
                    }
                }
            }
        } else {
            report.report("Error: can't recognize time range parameter: " + timeString);
        }
        return foundString;
    }

    /**
     * function splitting time parameter to string <br>
     * example user defines parameter like: <br>
     * 1s or 1m <br>
     * will return String minute,second <br>
     *
     * @param timeString - time string parameter (Ex. 1m or 5s)
     * @throws Exception
     */
    public static String convertShortTimeStrToLongStrMinSec(String timeString) throws Exception {
        String notFoundOptionStr = "NOT_FOUND_OPTION";
        String foundTime = notFound;
        Pattern p = Pattern.compile("(-*[0-9]+)([m|M|s|S])", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(timeString);
        String foundString = "";
        if (m.find()) {
            if (m.group(0) != null) {
                foundTime = m.group(1);
                if (m.group(2).equalsIgnoreCase("m")) {
                    foundString = foundTime + " minute";
                } else if (m.group(2).equalsIgnoreCase("s")) {
                    foundString = foundTime + " second";
                } else {
                    foundString = "NOT_FOUND_OPTION";
                }
                System.out.println("converted time range param: " + foundString);
                if (!foundTime.equalsIgnoreCase(notFoundOptionStr)) {
                    try {

                        int timeAsInt = Integer.parseInt(foundTime);
                        if (timeAsInt < 0) {
                            throw new Exception("received negative" + foundTime + " from:" + timeString
                                    + " please check you time string");
                        }
                    } catch (Exception e) {
                        throw new Exception("Can't convert time:" + foundTime + " to Int from:" + timeString
                                + " user defined string value");
                    }
                }
            }
        } else {
            report.report("Error: can't recognize time range parameter: " + timeString);
        }
        return foundString;
    }

    /**
     * converts received time minus time diff <br>
     * get actual date/time before received one by dimeDiff <br>
     * for example: <br>
     * if the Time= 14:20:30 and itimeDiff=20m <br>
     * so return 14:20:30 - 20minutes = 14:00:30
     *
     * @param theTime  - marked time in format (dd-MM-yyyy HH:mm:ss)
     * @param timeDiff - current time difference (possible format: 1m,5h,10s)
     * @return
     * @throws Exception
     */
    public static Date convertTimeDiffToTime(Date theTime, String timeDiff) throws Exception {

        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(theTime);
        RegExpr regexpr = new RegExpr(timeDiff, "(-*[0-9]+)([s|S|m|M|h|H|])");
        if (regexpr.isFound()) {
            String timeDiffValue = regexpr.getGroupText(2);
            String timeDiffUnit = regexpr.getGroupText(3);
            int timeDiffValueAsInt = Integer.parseInt(timeDiffValue);
            timeDiffValueAsInt *= -1;
            if (timeDiffUnit.equalsIgnoreCase("s")) {
                calendarTime.add(Calendar.SECOND, timeDiffValueAsInt);
            } else if (timeDiffUnit.equalsIgnoreCase("m")) {
                calendarTime.add(Calendar.MINUTE, timeDiffValueAsInt);
            } else if (timeDiffUnit.equalsIgnoreCase("h")) {
                calendarTime.add(Calendar.HOUR, timeDiffValueAsInt);
            } else {
                throw new Exception("un recognized unit: " + timeDiffUnit
                        + " possible values are: s - for second ,m- for minute or h for hour");
            }

        } else {
            throw new Exception("regular expression didnt found anything");
        }
        // convertedTime = DateManipulation.calendar2String(calendarTime,
        // TimeConverter.sharedFormat);
        Date updatedTemeAsDate = calendarTime.getTime();
        report.report("time " + theTime + " diff: " + timeDiff + " = new time: " + updatedTemeAsDate);

        return updatedTemeAsDate;
    }

    /**
     * converts received time minus time diff <br>
     * get actual date/time before received one by dimeDiff <br>
     * for example: <br>
     * if the Time= 14:20:30 and itimeDiff=20m <br>
     * so return 14:20:30 - 20minutes = 14:00:30
     *
     * @param theTime  - marked time in format (dd-MM-yyyy HH:mm:ss)
     * @param timeDiff - current time difference (possible format: 1m,5h,10s)
     * @return
     * @throws Exception
     */
    public static String convertTimeDiffToTime(String theTime, String timeDiff) throws Exception {
        String convertedTime = notSet;
        Calendar calendarTime = DateManipulation.string2Calendar(theTime);
        RegExpr regexpr = new RegExpr(timeDiff, "(-*[0-9]+)([s|S|m|M|h|H|])");
        if (regexpr.isFound()) {
            String timeDiffValue = regexpr.getGroupText(2);
            String timeDiffUnit = regexpr.getGroupText(3);
            int timeDiffValueAsInt = Integer.parseInt(timeDiffValue);
            timeDiffValueAsInt *= -1;
            if (timeDiffUnit.equalsIgnoreCase("s")) {
                calendarTime.add(Calendar.SECOND, timeDiffValueAsInt);
            } else if (timeDiffUnit.equalsIgnoreCase("m")) {
                calendarTime.add(Calendar.MINUTE, timeDiffValueAsInt);
            } else if (timeDiffUnit.equalsIgnoreCase("h")) {
                calendarTime.add(Calendar.HOUR, timeDiffValueAsInt);
            } else {
                throw new Exception("un recognized unit: " + timeDiffUnit
                        + " possible values are: s - for second ,m- for minute or h for hour");
            }

        } else {
            throw new Exception("regular expression didnt found anything");
        }
        convertedTime = DateManipulation.calendar2String(calendarTime, TimeConverter.sharedFormat);
        report.report("time " + theTime + " diff: " + timeDiff + " = new time: " + convertedTime);

        return convertedTime;
    }

    /**
     * function splitting time parameter to string <br>
     * example user defines parameter like: <br>
     * 1m - 1 minute or 1h - 1 hour or 1d - 1 day or 1w- 1 week<br>
     * will return String minute,hour <br>
     *
     * @param timeString - time string parameter (Ex. 1h or 5m)
     * @throws Exception
     */
    public static String convertShortTimeStrToLongRPAStrMinHourDayWeek(String timeString) throws Exception {
        String notFoundOptionStr = "NOT_FOUND_OPTION";
        String foundTime = notFound;
        Pattern p = Pattern.compile("(-*[0-9]+)([m|M|h|H|d|D|w|W])", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(timeString);
        String foundString = "";
        if (m.find()) {
            if (m.group(0) != null) {
                foundTime = m.group(1);
                if (m.group(2).equalsIgnoreCase("m")) {
                    foundString = foundTime + " mins";
                } else if (m.group(2).equalsIgnoreCase("h")) {
                    foundString = foundTime + " hrs";
                } else if (m.group(2).equalsIgnoreCase("d")) {
                    foundString = foundTime + " days";
                } else if (m.group(2).equalsIgnoreCase("w")) {
                    foundString = foundTime + " wks";
                } else {
                    foundString = "NOT_FOUND_OPTION";
                }
                System.out.println("converted time range param: " + foundString);
                if (!foundTime.equalsIgnoreCase(notFoundOptionStr)) {
                    try {

                        int timeAsInt = Integer.parseInt(foundTime);
                        if (timeAsInt < 0) {
                            throw new Exception("received negative" + foundTime + " from:" + timeString
                                    + " please check you time string");
                        }
                    } catch (Exception e) {
                        throw new Exception("Can't convert time:" + foundTime + " to Int from:" + timeString
                                + " user defined string value");
                    }
                }
            }
        } else {
            report.report("Error: can't recognize time range parameter: " + timeString);
        }
        return foundString;
    }

    public static int days2Hours(int timeInDays) {
        return timeInDays * 24;
    }

    public static int days2Minutes(int timeInDays) {
        return days2Hours(timeInDays) * 60;
    }

    public static int days2Seconds(int timeInDays) {
        return days2Minutes(timeInDays) * 60;
    }

    /**
     * function converts received time seconds to milliseconds
     *
     * @param timeInSeconds time in seconds seconds to convert
     */
    public static long seconds2Miliseconds(int timeInSeconds) {

        return (long) timeInSeconds * 1000;
    }

    /**
     * function converts received time in miliseconds to seconds
     *
     * @param timeInMiliseconds time in mili seconds to convert
     */
    public static int miliseconds2Seconds(long timeInMiliseconds) {

        return (int) timeInMiliseconds / 1000;
    }

    /**
     * function converts received time in miliseconds to seconds
     *
     * @param timeInMiliseconds time in miliseconds to convert
     */
    public static float miliseconds2Minutes(long timeInMiliseconds, int roundPlace) {

        // return (float) timeInMinutes / (1000 * 60);
        float timeInMinutes = (float) timeInMiliseconds / (1000 * 60);
        float timeFormatted = FloatFormatter.format(timeInMinutes, roundPlace);

        return timeFormatted;
    }

    /**
     * function converts received time in miliseconds to hours
     *
     * @param timeInMiliseconds time in miliseconds to convert
     * @param -                 roundPlace num of digits after the dot
     */
    public static float miliseconds2Hours(long timeInMiliseconds, int roundPlace) {

        float timeInHours = (float) timeInMiliseconds / (1000 * 60 * 60);
        float timeFormatted = FloatFormatter.format(timeInHours, roundPlace);

        return timeFormatted;
    }

    /**
     * function converts received time in seconds to minutes
     *
     * @param timeInSeconds time in seconds to convert
     * @param -             roundPlace num of digits after the dot
     */
    public static int seconds2Minutes(int timeInSeconds, int roundPlace) {

        float inMinutesLong = (float) timeInSeconds / 60;
        float timeFormatted = FloatFormatter.format(inMinutesLong, roundPlace);

        return Math.round(timeFormatted);

    }

    /**
     * converts minutes to seconds
     *
     * @param timeInMinutes
     * @return
     */
    public static long minutes2Miliseconds(int timeInMinutes) {
        return timeInMinutes * 60 * 1000;
    }

    /**
     * converts minutes to seconds
     *
     * @param timeInMinutes
     * @return
     */
    public static int minutes2Seconds(int timeInMinutes) {
        return timeInMinutes * 60;
    }

    /**
     * function converts received time in seconds to hours
     *
     * @param timeInSeconds time in seconds to convert
     * @param -             roundPlace num of digits after the dot
     */
    public static float seconds2Hours(int timeInSeconds, int roundPlace) {

        float inMinutesLong = (float) timeInSeconds / 60 / 60;
        float timeFormatted = FloatFormatter.format(inMinutesLong, roundPlace);

        return timeFormatted;

    }

    /**
     * converts time from hours to minutes
     *
     * @param timeInHours
     * @return
     */
    public static int hours2Minutes(int timeInHours) {
        return timeInHours * 60;
    }

    /**
     * converts time from hours to seconds
     *
     * @param timeInHours
     * @return
     */
    public static int hours2Seconds(int timeInHours) {
        return timeInHours * 60 * 60;
    }
}
