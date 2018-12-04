//package com.trafficmon;
//
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
//
//import java.time.LocalTime;
//
////this was inner class in test in video
//
//public class ControllableClock implements Clock  {
//
//    private LocalTime now;
//    private int hour;
//    private int min;
//    private int sec;
//
//
//    @Override
//    public LocalTime now() {
//        return now;
//    }
//
//    public void currentTimeIs(int hour, int min, int sec)
//    {
//        now = LocalTime.of(hour, min, sec);
//        //System.out.println("settig th e time to " + now);
////        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH");
////        org.joda.time.LocalTime localtime = fmt.parseLocalTime("17:30:25");
////        System.out.print(fmt.print(localtime));
//    }
//
//    public int getHour()
//    {
//        //currentTimeIs(15, 13, 20);  //if time set here, don't get null pointer exception but then current time can't be updated in tests.
//        LocalTime now1 = now();
//        String time = now1.toString();
//        String[] arrSplit = time.split(":");
//        hour = Integer.parseInt(arrSplit[0]);
//        return hour;
//    }
//
//    public int getMin()
//    {
//        LocalTime now1 = now();
//        String time = now1.toString();
//        String [] arrSplit = time.split(":");
//        min = Integer.parseInt(arrSplit[1]);
//        return min;
//
//    }
//
//
//    public int getSec()
//    {
//        LocalTime now1 = now();
//        String time = now1.toString();
//        String [] arrSplit = time.split(":");
//        sec = Integer.parseInt(arrSplit[2]);
//        return sec;
//
//    }
//
//    public LocalTime getNow() {
//        return now;
//    }
//}
