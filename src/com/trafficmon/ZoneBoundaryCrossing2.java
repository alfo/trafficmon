//package com.trafficmon;
//
//import org.joda.time.DateTime;
//
//import org.joda.time.Hours;
//
//import java.time.LocalTime;
//
//import static org.joda.time.Hours.parseHours;
//
//public abstract class ZoneBoundaryCrossing2 {
//
//    private final Vehicle vehicle;
//    private final long time;
//    private int hour;
//    private int min;
//    private int sec;
//    //private ControllableClock clock = new ControllableClock();
//
//
//
//    public ZoneBoundaryCrossing2(Vehicle vehicle) {
//        this.vehicle = vehicle;
//        this.time = System.currentTimeMillis();
////        this.time = LocalTime.fromMillisOfDay();
//
//        //https://www.joda.org/joda-time/userguide.html
////        DateTime dt = new DateTime();
////
////        ControllableClock clock = new ControllableClock();
////        LocalTime now = clock.now();
//        //this.hour = dt.getHourOfDay(now);
////        this.hour = dt.getHourOfDay();
////        this.min = dt.getMinuteOfHour();
////        this.sec = dt.getSecondOfMinute();
//
////        ControllableClock clock = new ControllableClock();
////        LocalTime now = clock.now();
////        String time = now.toString();
////        String[] arrSplit = time.split(":");
////        hour = Integer.parseInt(arrSplit[0]);
////        min = Integer.parseInt(arrSplit[1]);
////        sec = Integer.parseInt(arrSplit[2]);
//
//        //https://www.joda.org/joda-time/apidocs/org/joda/time/Hours.html
////        String time = now.toString(); //http://www.java2s.com/Tutorials/Java/java.time/LocalTime/2920__LocalTime.toString_.htm
////        parseHours(time);
//
//    }
//
//
//
//    public Vehicle getVehicle() {
//        return vehicle;
//    }
//
//    public long timestamp() {
//        long time = (hour * 3600) + (min * 60) + sec;
//        return time;
//    }
//
//    public int timestampHour()
//    {
//        hour = clock.getHour();
//        return hour;
//    }
//
//    public int timestampMin()
//    {
//        min = clock.getMin();
//        return min;
//    }
//
//    public int timestampSec()
//    {
//        sec = clock.getSec();
//        return sec;
//    }
//}
