package com.trafficmon;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.joda.time.Hours.parseHours;

public class Example {

    public static void main(String[] args) throws Exception {

//        CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();
////
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
//       // delaySeconds(15);
//
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("J091 4PY"));
////        delayMinutes(30);
//
//        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
////        delayMinutes(10);
//
//        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("J091 4PY"));
//        congestionChargeSystem.calculateCharges();

//        problem is not with tests, as this gives £6 as well.
//        CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();
//        ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(15, 00);
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
//        clock.currentTimeIs(16, 00);
//        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
//        congestionChargeSystem.calculateCharges();

//

//ControllableClock clock = new ControllableClock();
//clock.currentTimeIs(17, 30, 25);
////        LocalTime now = LocalTime.of(17, 30);
////        DateTime dt = new DateTime();
////        int hour = dt.getHourOfDay();
////        System.out.println(hour);
////        int min = dt.getMinuteOfHour();
////        System.out.println(min);
//        LocalTime now = clock.now();
//
//System.out.println(now);

//
//        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH");
//        org.joda.time.LocalTime localtime = fmt.parseLocalTime("17:30:25");
//        System.out.print(fmt.print(localtime));

//        ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(17, 30, 25);
//        LocalTime now = clock.now();
//        System.out.println(now);


//        ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(17,30,24);
//        LocalTime now = clock.now();
//        String time = now.toString(); //http://www.java2s.com/Tutorials/Java/java.time/LocalTime/2920__LocalTime.toString_.htm
//        System.out.println(time);
//        System.out.println(now.toString());
//        Hours hours = parseHours(time);
//        System.out.println(hours);


        //https://www.guru99.com/how-to-split-a-string-in-java.html
       // ArrayList<String> arrSplit = new ArrayList<ZoneBoundaryCrossing>(time.split(":"));

//        String[] arrSplit = time.split(":");
//        for (int i=0; i < arrSplit.length; i++)
//        {
//            System.out.println(arrSplit[i]);
//        }

        //https://www.mkyong.com/java/java-convert-string-to-int/


//        String timeHour = arrSplit[0];
//        int result = Integer.parseInt(timeHour);
//        System.out.println(result);

        //ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(17, 00, 24);
//        int result = clock.getHour();
//        int result = clock.getMin();
//        int result = clock.getSec();
//        System.out.println(result);


    }

    //ok what i have right now is three methods that returns integer hours, minutes and seconds of a custom time.

    private static void delayMinutes(int mins) throws InterruptedException {
        delaySeconds(mins * 60);
    }

    private static void delaySeconds(int secs) throws InterruptedException {
        Thread.sleep(secs * 1000);
    } }
