package com.cedex.time;

import com.cedex.jsystem.ReporterLight;
import com.cedex.text.RegExpr;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Author YoavL on 25/07/2016.
 */
public class TimeRange implements ReporterLight {

    private Timestamp rangeStartTime;
    private Timestamp rangeEndTime;
    private int rangeSize;
    private TimeUnit rangeTimeUnits;

    /**
     * Constructor for TimeRange
     * rangeEndTime is set to NOW.
     * @param stringTime - 10m, 1H, 100s, 2d
     * @Param latency - Added test time due to test result latency of mail for instance
     * @throws Exception
     */
    public TimeRange(String stringTime, String latency) throws Exception {
        this(getRangeFromString(stringTime).getRangeSize(),getRangeFromString(stringTime).getRangeTimeUnits());
        this.rangeStartTime.setTime(this.rangeStartTime.getTime() - TimeUnit.MILLISECONDS.convert(getRangeFromString(latency).getRangeSize(), getRangeFromString(latency).getRangeTimeUnits()));
    }

    public TimeRange(Date startTestTime, Date endTestTime) {
        this.rangeStartTime = new Timestamp(startTestTime.getTime());
        this.rangeEndTime = new Timestamp(endTestTime.getTime());
    }

    /**
     * Add time to test end time in the form:
     * @param latency - - 10m, 1H, 100s, 2d
     * @throws Exception
     */
    public void addToEndTime(String latency) throws Exception {
        TimeRange tempTimeRange = new TimeRange(getRangeFromString(latency).getRangeSize(),getRangeFromString(latency).getRangeTimeUnits());
        this.rangeEndTime.setTime(this.getRangeEndTime().getTime() + TimeUnit.MILLISECONDS.convert(tempTimeRange.getRangeSize(), tempTimeRange.getRangeTimeUnits()));
    }

    /**
     * Constructor for TimeRange
     * rangeEndTime is set to NOW.
     * @param stringTime - 10m, 1H, 100s, 2d
     * @throws Exception
     */
    public TimeRange(String stringTime) throws Exception {
        this(getRangeFromString(stringTime).getRangeSize(),getRangeFromString(stringTime).getRangeTimeUnits());
    }

    /**
     * Constructor for TimeRange
     * rangeEndTime is set to NOW.
     * @param rangeSize - a number for the time back from range end time to calculate
     * @param rangeTimeUnits - SECONDS / MINUTES / HOURS / DAYS
     */
    public TimeRange(int rangeSize, TimeUnit rangeTimeUnits) {
        this(new Timestamp(new Date().getTime()), rangeSize, rangeTimeUnits);
    }

    /**
     * Constructor for TimeRange
     * @param rangeEndTime - range end time
     * @param rangeSize - a number for the time back from range end time to calculate
     * @param rangeTimeUnits - SECONDS / MINUTES / HOURS / DAYS
     */
    public TimeRange(Date rangeEndTime, int rangeSize, TimeUnit rangeTimeUnits) {
        this(new Timestamp(rangeEndTime.getTime()), rangeSize, rangeTimeUnits);
    }

    /**
     * onstructor for TimeRange
     * @param rangeEndTime - range end time as String in the format: "yyy-MM-dd HH:mm:ss"
     * @param rangeSize - a number for the time back from range end time to calculate
     * @param rangeTimeUnits - SECONDS / MINUTES / HOURS / DAYS
     */
    public TimeRange(String rangeEndTime, int rangeSize, TimeUnit rangeTimeUnits) throws Exception {
        this(getTimeStampFromString(rangeEndTime), rangeSize, rangeTimeUnits);
    }

    /**
     * Constructor for TimeRange
     * @param rangeEndTime - range end time
     * @param rangeSize - a number for the time back from range end time to calculate
     * @param rangeTimeUnits - SECONDS / MINUTES / HOURS / DAYS
     */
    public TimeRange(Timestamp rangeEndTime, int rangeSize, TimeUnit rangeTimeUnits) {
        this.rangeEndTime = rangeEndTime;
        this.rangeSize = rangeSize;
        this.rangeTimeUnits = rangeTimeUnits;
        this.rangeStartTime = getRangeStartTime(this.rangeEndTime,this.rangeSize,this.rangeTimeUnits);
    }

    /**
     * Check whether or not a specified date/time is within the TimeRange
     * @param testingDate - The requested timestamp to check
     * @return boolean - True for in range, false for not.
     */
    public boolean isDateInRange(Date testingDate) {
        return isDateInRange(new Timestamp(testingDate.getTime()));
    }

    /**
     * Check whether or not a specified date/time is within the TimeRange
     * @param testingDate - The requested timestamp to check
     * @return  boolean - True for in range, false for not.
     */
    public boolean isDateInRange(Timestamp testingDate) {
        boolean isInRange = testingDate.after(rangeStartTime) && testingDate.before(rangeEndTime);
        report.report("The testing date: [" + testingDate + "] is "  + (isInRange ? "in range.":"NOT in range. (after +"+rangeStartTime+" and before+ "+rangeEndTime));
        return isInRange;
    }

    /**
     * Retrieve the range start-time given + an end reference + diff back + it time units (sec/min/hour...etc)
     * @param timestampEndTime - The range end time
     * @param rangeSize - The range size for time units back
     * @param timeUnit - - SECONDS / MINUTES / HOURS / DAYS
     * @return Timestamp that represents the start time
     */
    private Timestamp getRangeStartTime(Timestamp timestampEndTime, int rangeSize, TimeUnit timeUnit)  {
        rangeStartTime = new Timestamp(timestampEndTime.getTime());
        rangeStartTime.setTime(timestampEndTime.getTime() - TimeUnit.MILLISECONDS.convert(rangeSize, timeUnit));
        return rangeStartTime;
    }

    private static Timestamp getTimeStampFromString(String time) throws Exception {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        try
        {
            date = simpleDateFormat.parse(time);
            report.report("date : "+simpleDateFormat.format(date));
        }
        catch (ParseException ex)
        {
            report.report("Exception converting String date: "+ ex);
            throw new Exception("Exception converting String date [" + time  + "] the format should have been: [\"yyy-MM-dd HH:mm:ss\"]: "+ ex);
        }
        return new Timestamp(date.getTime());
    }

    private static TimeRange getRangeFromString(String time) throws Exception {
        int rangeSize;
        TimeUnit rangeTimeUnits;


        RegExpr regExpr =new RegExpr(time,"(\\d*)([s|m|h])",false);
        if (regExpr.isFound()) {
            String countStr = regExpr.getGroupText(2);
            String unit = regExpr.getGroupText(3).toLowerCase();

            try {
                rangeSize = Integer.parseInt(countStr);
            } catch (Exception e) {
                report.report("Exception Parsing range from string [" + time  + "] : " + e);
                throw new Exception("Exception Parsing range from string [" + time  + "] : " + e);
            }
            //todo: enum with values
            switch (unit) {
                case "s":
                    rangeTimeUnits = TimeUnit.SECONDS;
                    break;
                case "m":
                    rangeTimeUnits = TimeUnit.MINUTES;
                    break;
                case "h":
                    rangeTimeUnits = TimeUnit.HOURS;
                    break;
                case "d":
                    rangeTimeUnits = TimeUnit.DAYS;
                    break;
                default:
                    report.report("Exception Parsing range from string [" + time + "] ");
                    throw new Exception("Exception Parsing range from string [" + time + "] ");
            }
        } else {
            throw new Exception("Exception Parsing range from string [" + time + "] ");
        }


        return new TimeRange(rangeSize, rangeTimeUnits);
    }

    public Timestamp getRangeStartTime() {
        return rangeStartTime;
    }

    public void setRangeStartTime(Timestamp rangeStartTime) {
        this.rangeStartTime = rangeStartTime;
    }

    public Timestamp getRangeEndTime() {
        return rangeEndTime;
    }

    public void setRangeEndTime(Timestamp rangeEndTime) {
        this.rangeEndTime = rangeEndTime;
    }

    public int getRangeSize() {
        return rangeSize;
    }

    public void setRangeSize(int rangeSize) {
        this.rangeSize = rangeSize;
    }

    public TimeUnit getRangeTimeUnits() {
        return rangeTimeUnits;
    }

    public void setRangeTimeUnits(TimeUnit rangeTimeUnits) {
        this.rangeTimeUnits = rangeTimeUnits;
    }
}