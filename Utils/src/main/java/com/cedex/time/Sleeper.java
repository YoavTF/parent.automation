package com.cedex.time;


import com.cedex.jsystem.ReporterLight;
import com.cedex.numbers.Randomalizator;

import java.util.concurrent.TimeUnit;

/**
 * Pause / sleep class<br>
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 *
 * @author $Author: romang $
 * @version $Revision: 11697 $
 */
public abstract class Sleeper implements ReporterLight {


    /**
     * sleeping for random time in miliseconds and print some message
     *
     *
     * @throws Exception
     */
    public static void sleepForRandomNTime(int minSleepTime, int maxSleepTime, TimeUnit timeUnit) throws Exception {

        String message = null;
        sleepForRandomNTime(message, minSleepTime, maxSleepTime, timeUnit);

    }

    /**
     * sleeping for N miliseconds and print some message
     *
     * @param message
     * @param
     * @throws Exception
     */
    public static void sleepForRandomNTime(String message, int minSleepTime, int maxSleepTime, TimeUnit timeUnit) throws Exception {
        int random = Randomalizator.getRandomNumberInRange(minSleepTime, maxSleepTime);
        int randomTime = random;
        if (message == null || message.equals(""))
            message = "random sleep for: " + randomTime + " " + "timeUnit";
        sleepForNTime(message, randomTime, timeUnit);

    }

//    /**
//     * sleeping for random time in miliseconds and print some message
//     *
//     * @param minMiliseconds - minimum time to wait (i
//     * @param maxMiliseconds - maximum time to wait
//     * @throws Exception
//     */
//    public static void sleepForRandomNTime(long minMiliseconds, long maxMiliseconds) throws Exception {
//
//        String message = null;
//        sleepForRandomNTime(message, minMiliseconds, maxMiliseconds);
//
//    }
//
//    /**
//     * sleeping for N miliseconds and print some message
//     *
//     * @param message
//     * @param maxMiliseconds - generate randon value from0 to this number to wait
//     * @throws Exception
//     */
//    public static void sleepForRandomNTime(String message, long minMiliseconds, long maxMiliseconds) throws Exception {
//        int random = Randomalizator.getRandomNumberInRange((int) minMiliseconds, (int) maxMiliseconds);
//        long randomMilis = random;
//        if (message == null || message.equals(""))
//            message = "random sleep for: " + randomMilis + " miliseconds";
//        sleepForNMiliseconds(message, randomMilis);
//
//    }

    /**
     * sleeping for N miliseconds and print some message
     *
     * @param message
     * @param miliseconds
     * @throws Exception
     */
    private static void sleepForNMiliseconds(String message, long miliseconds) throws Exception {
        report.report(message);
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            report.report(e.getMessage());
        }
    }

    /**
     * Causes the currently executing thread to sleep <br>
     * (temporarily cease execution) for the specified number of seconds! <br>
     *
     * @param - message - print this message before sleeping
     * @throws Exception
     */
    private static void sleepForNSeconds(String message, int seconds) throws Exception {
        long miliseconds = seconds * 1000;
        sleepForNMiliseconds(message, miliseconds);
//        sleepForNTime(message, seconds, TimeUnit.SECONDS);

    }

//    /**
//     * Causes the currently executing thread to sleep <br>
//     * (temporarily cease execution) for the specified number of seconds! <br>
//     *
//     * @param minutes - time to sleep (in minutes)
//     * @throws Exception
//     */
//    public static void sleepForNMinutes(String message, int minutes) throws Exception {
////        int timeInSeconds = TimeConverter.minutes2Seconds(minutes);
////        Sleeper.sleepForNSeconds(message, timeInSeconds);
//        sleepForNTime(message, minutes, TimeUnit.MINUTES);
//    }

//    /**
//     * Causes the currently executing thread to sleep <br>
//     * (temporarily cease execution) for the specified number of seconds! <br>
//     *
//     * @param minutes - time to sleep (in minutes)
//     * @throws Exception
//     */
//    public static void sleepForNMinutes(int minutes) throws Exception {
////        int timeInSeconds = TimeConverter.minutes2Seconds(minutes);
//        sleepForNTime(minutes, TimeUnit.MINUTES);
////        Sleeper.sleepForNSeconds(timeInSeconds);
//    }

//    /**
//     * Causes the currently executing thread to sleep <br>
//     * (temporarily cease execution) for the specified number of seconds! <br>
//     *
//     * @throws Exception
//     */
//    public static void sleepForNSeconds(int seconds) throws Exception {
//        String message = "waiting for: " + seconds + " seconds ...";
//        sleepForNTime(message, seconds, TimeUnit.SECONDS);
////        sleepForNSeconds(message, seconds);
//    }

    public static void sleepForNTime(int timeToSleep, TimeUnit timeUnit) throws Exception {
        String message = "waiting for: " + timeToSleep + " " + timeUnit + "...";
        sleepForNTime(message, timeToSleep, timeUnit);
    }

    public static void sleepForNTime(String message, int timeToSleep, TimeUnit timeUnit) throws Exception {
        int convertedTimeToSleep = 0;
        switch (timeUnit) {
            case MINUTES: {
                convertedTimeToSleep = TimeConverter.minutes2Seconds(timeToSleep);
                break;
            }
            case HOURS: {
                convertedTimeToSleep = TimeConverter.hours2Seconds(timeToSleep);
                break;
            }
            case SECONDS: {
                convertedTimeToSleep = timeToSleep;
                break;
            }
            case DAYS: {
                convertedTimeToSleep = TimeConverter.days2Seconds(timeToSleep);
                break;
            }
            case MILLISECONDS: {
                convertedTimeToSleep = TimeConverter.miliseconds2Seconds(timeToSleep);
                break;
            }
            default: {

                throw new Exception("Not supported value: " + timeUnit);
            }
        }

        sleepForNSeconds(message, convertedTimeToSleep);
    }

    /**
     * Causes the currently executing thread to sleep <br>
     * (temporarily cease execution) for the specified number of seconds! <br>
     *
     * @param timeToSleep - could be 3m for 3 munites of sleep<br>
     *                    or <br>
     *                    - could be 1h for 1 hour of sleep
     * @throws Exception
     */
    public static void sleepForNTime(String timeToSleep) throws Exception {

        String convertedTime = TimeConverter.convertShortTimeStrToLongStrMinHour(timeToSleep);
        if (convertedTime.equalsIgnoreCase("NOT_FOUND_OPTION")) {
            report.report("sleepeng time: " + timeToSleep + " in illegal format: valid format 10m - for 10 minutes,3h for 3 houre");
            return;
        }

        int multiplier = 1000;
        String timeAsString = convertedTime.split(" ")[0];

        int timeAsInt = 0;
        try {
            timeAsInt = Integer.parseInt(timeAsString);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
        String timeUnits = convertedTime.split(" ")[1];
        if (timeUnits.equals("minute")) {
            multiplier = multiplier * 60;
        } else if (convertedTime.split(" ")[1].equals("hour")) {
            multiplier = multiplier * 60 * 60;
        }
        report.report("waiting for: " + convertedTime);
        try {
            Thread.sleep(timeAsInt * multiplier);
        } catch (InterruptedException e) {
            report.report(e.getMessage());
        }
    }
}
