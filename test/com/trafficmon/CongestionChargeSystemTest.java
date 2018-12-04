package com.trafficmon;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;

public class CongestionChargeSystemTest {

    CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();
    Map<Vehicle, List<ZoneBoundaryCrossing>> map1 = congestionChargeSystem.getCrossingsByVehicle();
    ArrayList<ZoneBoundaryCrossing> list = new ArrayList<ZoneBoundaryCrossing>();
    //private LocalTime now;
//NOTE our tests do not test any time related function yet, including in the checkOrderingOf function

    @Test

    public void checkVehicleAddedToEventLog()
    {
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("ABC"));
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("ABC"));
        assertThat(congestionChargeSystem.size(), is(2));
    }

    @Test

    public void checkVehicleNotAddedToEventLog()
    {
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("ABC"));
        assertThat(congestionChargeSystem.size(), is(0));  //O as vehicle b should not have been added to congestionChargeSystem as not previously registered
    }

    @Test

    public void checkMinutesBetween()

    {
        long startTimeMs = 1000000;
        long endTimeMs = 3000000;
        assertThat(congestionChargeSystem.minutesBetween(startTimeMs, endTimeMs), is(34));
        //having done maths manually, value should be 34
    }

    @Test

    public void checkNoCrossingNoMapping()
    {
        Vehicle a = new Vehicle("ABC");
        congestionChargeSystem.calculateCharges();
        assertThat(map1.size(), is(0));
    }
    //no vehicle enters zone, so value of map is 0

    @Test

    public void vehicleInMapAddCrossing()
    {
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("ABC"));
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("DEF"));
        congestionChargeSystem.calculateCharges();
        assertThat(map1.size(), is(2));
    }
    //shows that if two vehicles enter the zone, both logged in hashmap
    //why are there penalty notices?

    //no penalty notice if you use the car registrations they have used.
    @Test

    public void crossingAddedTwiceButVehicleOnlyAddedOnce()
    {
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
        congestionChargeSystem.calculateCharges();
        List<ZoneBoundaryCrossing> list1 = congestionChargeSystem.getCrossingsByVehicleValue("A123 XYZ");
        assertThat(list1.size(), is(2));
        assertThat(map1.size(), is(1));
    }

    @Test

    public void checkThatVehicleDoesNotEnterTwiceBeforeExiting()
    {

        Vehicle vehicle = new Vehicle("ABC");
        list.add(new EntryEvent(vehicle));
        list.add(new EntryEvent(vehicle));
        assertFalse(congestionChargeSystem.checkOrderingOf(list));
    }

    @Test

    public void checkThatVehicleCanEnterExitEnter()
    {
        Vehicle vehicle = new Vehicle("ABC");
        list.add(new EntryEvent(vehicle));
        list.add(new ExitEvent(vehicle));
        list.add(new EntryEvent(vehicle));
        assertTrue(congestionChargeSystem.checkOrderingOf(list));
    }

    @Test

    public void checkThatVehicleDoesNotExitTwiceBeforeEntering()
    {

        Vehicle vehicle = new Vehicle("DEF");
        list.add(new ExitEvent(vehicle));
        list.add(new ExitEvent(vehicle));
        assertFalse(congestionChargeSystem.checkOrderingOf(list));
    }

//    @Test
//
//    public void testingCharging1()
//    {
//        ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(15, 00, 00);
//        Vehicle vehicle = new Vehicle("ABC");
//        list.add(new EntryEvent(vehicle));
//        clock.currentTimeIs(16, 00, 00); //duration is 1 hr
//        list.add(new ExitEvent(vehicle));
//        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(4.00)));
//    }
//
//    //problem: charge seems to be whatever test is run first.
//    //need to make sure all tests below pass
//
//    @Test
//
//    public void testingCharging2()
//    {
//        ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(10, 30, 00);
//        Vehicle vehicle = new Vehicle("DEF");
//        list.add(new EntryEvent(vehicle));
//        clock.currentTimeIs(12, 00, 00);
//        list.add(new ExitEvent(vehicle));
//        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(6.00)));
//    }
//
//    @Test
//
//    public void testingCharging3()
//    {
//        ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(10, 00, 00);
//        Vehicle vehicle = new Vehicle("ABC");
//        list.add(new EntryEvent(vehicle));
//        clock.currentTimeIs(16, 00, 00);
//        list.add(new ExitEvent(vehicle));
//        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(12.00)));
//    }

    @Test

    public void test()
    {
//        ControllableClock clock = new ControllableClock();
//        clock.currentTimeIs(10, 30, 00);
        CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem(new ControllableClock());
        Vehicle vehicle = new Vehicle("DEF");
//        ZoneBoundaryCrossing zoneBoundaryCrossing = new ZoneBoundaryCrossing(vehicle);
//        zoneBoundaryCrossing.setTime(10, 30, 00);
//        zoneBoundaryCrossing.getTime();
        //call the public zone boundary function - this should s
        //problem: instance of clock is created here and current time is set. However, when timestampHour() is called:
        //this goes to the zone boundary class where a new instance of clock was created at the top
        //so need to find a way to get that instance to be the same as this instance
        //want the test and implementation to know about the same clock object
        //don't want to implement a singleton as if the tests are run in parallel, would cause a clash if it's the clock is set to 10am in one and 2pm in the other

        ControllableClock clock = new ControllableClock();
        clock.currentTimeIs(14, 00, 00);

        list.add(new EntryEvent(vehicle));
        ZoneBoundaryCrossing lastEvent = list.get(0);
        //assertThat(lastEvent.timestampHour(), is(14));
        assertThat(clock.timestampHour(lastEvent), is(14));
//        LocalTime trial = clock.now();
//        assertThat(trial, is(14));
    }

    private class ControllableClock implements Clock {

        private LocalTime now;
        private int hour;
        private int min;
        private int sec;

        @Override
        public LocalTime now() {
            return now;
        }

        public void currentTimeIs(int hour, int min, int sec) {
            now = LocalTime.of(hour, min, sec);
        }

        public int timestampHour(ZoneBoundaryCrossing crossing)
    {
        //currentTimeIs(15, 13, 20);  //if time set here, don't get null pointer exception but then current time can't be updated in tests.
        LocalTime now1 = now();
        String time = now1.toString();
        String[] arrSplit = time.split(":");
        hour = Integer.parseInt(arrSplit[0]);
        return hour;
    }

    public int timestampMin(ZoneBoundaryCrossing crossing)
    {
        LocalTime now1 = now();
        String time = now1.toString();
        String [] arrSplit = time.split(":");
        min = Integer.parseInt(arrSplit[1]);
        return min;

    }


    public int timestampSec(ZoneBoundaryCrossing crossing)
    {
        LocalTime now1 = now();
        String time = now1.toString();
        String [] arrSplit = time.split(":");
        sec = Integer.parseInt(arrSplit[2]);
        return sec;

    }

        public long timestamp(ZoneBoundaryCrossing crossing) {
            long time = (hour * 3600) + (min * 60) + sec;
            return time;
        }

    }

    //getter function for clock class to get the current value of the clock
    //pass in the clock as a parameter to the constructor in zone boundary


}
