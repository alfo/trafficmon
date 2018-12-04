package com.trafficmon;

import org.joda.time.DateTime;

import java.time.LocalTime;

public abstract class ZoneBoundaryCrossing{

    private final Vehicle vehicle;
    private final long time;
//    private final int hour;
//    private final int min;
//    private final int sec;
    private int hour;
    private int min;
    private int sec;
    //ControllableClock clock = new ControllableClock(); //this is an instance of clock different from the one in the tests so it doesn't have the current time, hence returns null pointer excpetion
    private LocalTime customTime;


    public ZoneBoundaryCrossing(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = System.currentTimeMillis();
//        this.time = LocalTime.fromMillisOfDay();

        //https://www.joda.org/joda-time/userguide.html
//        DateTime dt = new DateTime();
//
//        this.hour = dt.getHourOfDay();
//        this.min = dt.getMinuteOfHour();
//        this.sec = dt.getSecondOfMinute();


    }

    public Vehicle getVehicle() {
        return vehicle;
    }

//    public void setTime(int hour, int min, int sec)
//    {
//
//        customTime = LocalTime.of(hour, min, sec);
//        System.out.println("setting the time to " + customTime + " in setTime function");
//    }
//
//    public LocalTime getTime()
//    {
//        System.out.println("setting the time to " + customTime + "in getTime function");
//        return customTime;
//    }



    public long timestamp() {
        long time = (hour * 3600) + (min * 60) + sec;
        return time;
    }

//    public int timestampHour()
//    {
//
//        //clock.currentTimeIs(15, 30, 25);  //if time is set here, then no null pointer exception, uses this value of time
////        LocalTime trial = clock.getNow();
////        System.out.println(trial);
////        setTime(15, 34, 34);
//        //need to call the setTime function here for it to not be null, but this defeats the point...
////        LocalTime trial = getTime();
////        System.out.println("Setting the time to " + trial + " in timestampHour function");
////        String time = trial.toString();
////        String[] arrSplit = time.split(":");
////        hour = Integer.parseInt(arrSplit[0]);
//        //hour = clock.getHour();
//        return hour;
//    }
//
//    public int timestampMin()
//    {
////        String time = customTime.toString();
////        String[] arrSplit = time.split(":");
////        hour = Integer.parseInt(arrSplit[1]);
//        //min = clock.getMin();
//        return min;
//    }
//
//    public int timestampSec()
//    {
////        String time = customTime.toString();
////        String[] arrSplit = time.split(":");
////        hour = Integer.parseInt(arrSplit[2]);
//        //sec = clock.getSec();
//        return sec;
//    }
}
//zoneboundarycrossing.setClockTime(hour, min sec) - create this function here as public