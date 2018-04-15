package com.cedex.time;

import com.cedex.GlobalParameters;
import com.cedex.jsystem.ReporterLight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Current Time class<br>
 * <p>
 * Last changed: $LastChangedDate: 2012-09-08 11:57:51$
 *
 * @author $Author: romang $
 * @version $Revision: 11458 $
 */
public class CurrentTime implements ReporterLight, GlobalParameters {

    // public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";

    public CurrentTime(String newFormat) {
        DATE_FORMAT_NOW = newFormat;

    }

    /**
     * function replays current time in format: dd-MM-yyyy HH:mm:ss
     *
     * @return
     * @throws Exception
     */
    public static Date nowDate() throws Exception {
        return new Date();
    }

    /**
     * function replays current time in format: dd-MM-yyyy HH:mm:ss
     *
     * @return
     * @throws Exception
     */
    public static String now() throws Exception {
        return now(DATE_FORMAT_NOW);
    }

    /**
     * function replays current time in format: dd-MM-yyyy HH:mm:ss
     *
     * @return
     * @throws Exception
     */
    public static String now(String formatDate) throws Exception {
        String currentTime = notFound;
        try {
            DateFormat dateFormat = new SimpleDateFormat(formatDate);
            Date date = new Date();
            currentTime = dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        } finally {
            report.report("current time is: " + currentTime);
        }

        return currentTime;

    }
}
