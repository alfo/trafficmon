package com.trafficmon;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;

public class CongestionChargeSystemTest {

    private CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();
    private Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicleMap = congestionChargeSystem.getCrossingsByVehicle();
    private ArrayList<ZoneBoundaryCrossing> ZoneBoundaryCrossingList = new ArrayList<ZoneBoundaryCrossing>();
    private Vehicle vehicle1 = new Vehicle("A123 XYZ");
    private Vehicle vehicle2 = new Vehicle("J091 4PY");

//    @Rule
//
//    public JUnitRuleMockery context = new JUnitRuleMockery();
//
//    PenaltiesService penaltiesService = context.mock(PenaltiesService.class);
//
//    OperationsTeam operationsTeam = context.mock(OperationsTeam.class);
//
//    @Test
//
//
//    public void checkInvestigationIntoVehicleTriggered()
//    {
//
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("ABC"));
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("ABC"));
//        context.checking(new Expectations() {{
//            exactly(1).of(penaltiesService).triggerInvestigationInto(Vehicle.withRegistration("ABC"));
//        }});
//        congestionChargeSystem.calculateCharges();
//    }

    @Test

    public void checkVehicleAddedToEventLog()
    {
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
        assertThat(congestionChargeSystem.size(), is(2));
    }

    @Test

    public void checkVehicleNotAddedToEventLog()
    {
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
        assertThat(congestionChargeSystem.size(), is(0));
    }

    @Test

    public void checkSecondsBetween()

    {
        long startS = 1000000;
        long endS = 3000000;
        assertThat(congestionChargeSystem.secondsBetween(startS, endS), is((long)2000000));
    }

    @Test

    public void CheckIfNoCrossingThenNothingIsAddedToMap()
    {
        congestionChargeSystem.calculateCharges();
        assertThat(crossingsByVehicleMap.size(), is(0));
    }


    @Test

    public void IfTwoVehiclesEnterZoneBothLoggedInMap()
    {
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("J091 4PY"));
        congestionChargeSystem.calculateCharges();
        assertThat(crossingsByVehicleMap.size(), is(2));
    }


    @Test

    public void crossingAddedTwiceButVehicleOnlyAddedOnce()
    {
        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("A123 XYZ"));
        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("A123 XYZ"));
        congestionChargeSystem.calculateCharges();
        List<ZoneBoundaryCrossing> list1 = congestionChargeSystem.getCrossingsByVehicleRegistrationNumber("A123 XYZ");
        assertThat(list1.size(), is(2));
        assertThat(crossingsByVehicleMap.size(), is(1));
    }

    @Test

    public void checkThatVehicleDoesNotEnterBeforeExiting()
    {
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        assertFalse(congestionChargeSystem.checkOrderingOf(ZoneBoundaryCrossingList));
    }

    @Test

    public void checkThatVehicleCanEnterExitEnter()
    {
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        assertTrue(congestionChargeSystem.checkOrderingOf(ZoneBoundaryCrossingList));
    }

    @Test

    public void checkThatVehicleDoesNotExitTwiceBeforeEntering()
    {
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle2));
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle2));
        assertFalse(congestionChargeSystem.checkOrderingOf(ZoneBoundaryCrossingList));
    }

    @Test

    public void checkExitIsAfterEntry()
    {
        Clock.setFakeTime(10, 00 ,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(9,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertFalse(congestionChargeSystem.checkOrderingOf(ZoneBoundaryCrossingList));
    }

    @Test

    public void testingChargingAfter2pm()
    {
        Clock.setFakeTime(15, 25, 27);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(16, 0, 0);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(4.00)));
    }

    @Test

    public void testingChargingBefore2pm()
    {

        Clock.setFakeTime(10, 30, 00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle2));
        Clock.setFakeTime(12, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle2));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(6.00)));
    }

    @Test

    public void testChargingWholeDay()
    {

        Clock.setFakeTime(10, 30, 00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(16, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(12.00)));
    }

    @Test

    public void checkEnterAndExitTwoTimesButNotWithin4Hours()
    {
        Clock.setFakeTime(10,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(12, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(17,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(19,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(10.00)));
    }

    @Test

    public void checkEnterAndExitTwoTimesWithin4Hours()
    {
        Clock.setFakeTime(10,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(12, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(14,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(15,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(6.00)));
    }

    @Test

    public void testReenteringWithin4HoursButTotalDurationIsGreaterThan4Hours()
    {
        Clock.setFakeTime(10,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(12, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(14,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(17,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(12.00)));
    }

    @Test

    public void testThreeTimesEntryAndExitWithinLessThan4Hours()
    {
        Clock.setFakeTime(10,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(11, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(13,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(14,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(16, 00, 00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(17,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(6.00)));
    }

    @Test

    public void testThreeTimesEntryAndExitGreaterThan4Hours()
    {
        Clock.setFakeTime(8,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(9, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(14,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(15,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(16, 00, 00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(17,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(10.00)));
    }

    @Test

    public void testThreeTimesEntryAndExitWithGapGreaterThan4HoursBothTimes()
    {
        Clock.setFakeTime(8,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(9, 00, 00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(14,00,00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(15,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        Clock.setFakeTime(20, 00, 00);
        ZoneBoundaryCrossingList.add(new EntryEvent(vehicle1));
        Clock.setFakeTime(21,00,00);
        ZoneBoundaryCrossingList.add(new ExitEvent(vehicle1));
        assertThat(congestionChargeSystem.calculateChargeForTimeInZone(ZoneBoundaryCrossingList), is(new BigDecimal(14.00)));
    }

}
