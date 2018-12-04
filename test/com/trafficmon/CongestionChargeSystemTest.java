package com.trafficmon;

import jdk.jfr.Timespan;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
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

    public void checkSecondsBetween()

    {
        long startS = 1000000;
        long endS = 3000000;
        assertThat(congestionChargeSystem.secondsBetween(startS, endS), is((long)2000000));
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

    @Test

    public void crossingAddedTwiceButVehicleOnlyAddedOnce()
    {
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("ABC"));
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("ABC"));
        congestionChargeSystem.calculateCharges();
        List<ZoneBoundaryCrossing> list1 = congestionChargeSystem.getCrossingsByVehicleValue("ABC");
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

    @Test

    public void testingChargingAfter2pm()
    {
        Clock.setFakeTime(15, 0, 0);

        Vehicle vehicle = new Vehicle("ABC");
        list.add(new EntryEvent(vehicle));

        Clock.setFakeTime(16, 0, 0);
        list.add(new ExitEvent(vehicle));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(4.00)));
    }
    @Test

    public void testingChargingBefore2pm()
    {

        Clock.setFakeTime(10, 30, 00);
        Vehicle vehicle = new Vehicle("DEF");
        list.add(new EntryEvent(vehicle));

        Clock.setFakeTime(12, 00, 00);
        list.add(new ExitEvent(vehicle));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(6.00)));
    }

    @Test

    public void testChargingWholeDay()
    {

        Clock.setFakeTime(10, 00, 00);
        Vehicle vehicle = new Vehicle("ABC");
        list.add(new EntryEvent(vehicle));
        Clock.setFakeTime(16, 00, 00);
        list.add(new ExitEvent(vehicle));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(12.00)));
    }

    @Test

    public void testChargingMultipleTimesMoreThan4Hours()
    {
        Clock.setFakeTime(10,00,00);
        Vehicle vehicle = new Vehicle("ABC");
        list.add(new EntryEvent(vehicle));
        Clock.setFakeTime(12, 00, 00);
        list.add(new ExitEvent(vehicle));
        Clock.setFakeTime(17,00,00);
        list.add(new EntryEvent(vehicle));
        Clock.setFakeTime(19,00,00);
        list.add(new ExitEvent(vehicle));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(10.00)));
    }

    @Test

    public void testChargingMultipleTimesLessThan4Hours()
    {
        Clock.setFakeTime(10,00,00);
        Vehicle vehicle = new Vehicle("ABC");
        list.add(new EntryEvent(vehicle));
        Clock.setFakeTime(12, 00, 00);
        list.add(new ExitEvent(vehicle));
        Clock.setFakeTime(14,00,00);
        list.add(new EntryEvent(vehicle));
        Clock.setFakeTime(15,00,00);
        list.add(new ExitEvent(vehicle));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(list), is(new BigDecimal(6.00)));
    }




}
