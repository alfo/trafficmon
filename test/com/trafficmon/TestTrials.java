package com.trafficmon;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestTrials {

//    private Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicle = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();
//
//    CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();
//
//    @Rule
//    public JUnitRuleMockery context = new JUnitRuleMockery();
//
//    CongestionChargeSystem congestionChargeSystem1 = context.mock(CongestionChargeSystem.class);
//    CongestionChargeSystem congestionChargeSystem1 = context.mock(CongestionChargeSystem.class);
//
//    @Test
//
//    public void checkVehicleAddedToEventLog()
//    {
//        Vehicle a = new Vehicle("ABC");
//        context.checking(new Expectations() {{
//            exactly(1).of(congestionChargeSystem).vehicleEnteringZone(a);
//        }});
//        congestionChargeSystem.vehicleEnteringZone(a);
//
//        Vehicle a = new Vehicle("ABC");
//        congestionChargeSystem.vehicleEnteringZone(a);
//        congestionChargeSystem.vehicleLeavingZone(a);
//        assertThat(congestionChargeSystem.size(), is(2));
//
//    }
//
//    @Test
//
//    public void checkVehicleNotAddedToEventLog()
//    {
//        Vehicle b = new Vehicle("DEF");
//        congestionChargeSystem.vehicleLeavingZone(b);
//        assertThat(congestionChargeSystem.size(), is(0));  //O as vehicle b should not have been added to congestionChargeSystem as not previously registered
//    }
//
//    @Test
//    if there is no crossing, no mapping
//    if there is a vehicle already in the map and it crosses, add that crossing
//    if there is a crossing but vehicle not in map, then add the vehicle and the crossing
//
//    public void checkNoCrossingNoMapping()
//    {
//        Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicle = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();
//        Vehicle a = new Vehicle("ABC");
//        assertThat(crossingsByVehicle.size(), is(0));
//
//    }
//    problem, even though this test passes, it would still pass even if a crossing occurred as the hashmap created is not the one that is accessed by the call to the function
//
//    public void vehicleInMapAddCrossing()
//    {
//        Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicle = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();
//        List<ZoneBoundaryCrossing> eventLog = new ArrayList<ZoneBoundaryCrossing>();
//        Vehicle a = new Vehicle("ABC");
//        eventLog.add(new EntryEvent(a));
//        ArrayList<ZoneBoundaryCrossing> listZoneBoundaryCrossing = new ArrayList<ZoneBoundaryCrossing>();
//
//        crossingsByVehicle.put(a, crossing);
//
//        congestionChargeSystem.calculateCharges();
//
//    }
//
//    @Test
//
//    public void vehicleInMapAddCrossing2()
//    {
//
//        Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicle = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();
//        Vehicle a = new Vehicle("ABC");
//        congestionChargeSystem.calculateCharges();
//        assertThat(crossingsByVehicle.size(), is(1));
//
//    }
//
//
//    do we need to test the for loops or just the entire function??
//
//    @Test
//
//    public void vehicleInMapAddCrossing()
//    {
//
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("123"));
//        //congestionChargeSystem.calculateCharges();
//        assertThat(congestionChargeSystem.size(), is(1));
//
//    }
//




//    want to test exceptions
//    first look at where exceptions are called in code

//    @Test(expected = IndexOutOfBoundsException.class)
////  this is how to test exceptions in JUnit - since exception is thrown, test passes
//    public void empty()
//    {
//        new ArrayList<Object>().get(0);
//    }

//  can check if there is sufficient credit, if not, exception should be thrown
//  can check if account is registered, if not, exception should be thrown
//  Q: how to create an account that isn't registered or an account without sufficient credit?



//    @Test
//
//    public void test()
//    {
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("ABC"));
//        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("ABC"));
//
//        List<ZoneBoundaryCrossing> crossings = new ArrayList<ZoneBoundaryCrossing>();
//        assertTrue(congestionChargeSystem.checkOrderingOf(crossings));
//
//    }


    //    public void checkThatVehicleDoesNotEnterTwiceBeforeExiting()
//    {
//        congestionChargeSystem.vehicleEnteringZone(Vehicle.withRegistration("ABC"));
//        congestionChargeSystem.vehicleLeavingZone(Vehicle.withRegistration("ABC"));
//        congestionChargeSystem.calculateCharges();
//        List<ZoneBoundaryCrossing> list1 = congestionChargeSystem.getCrossingsByVehicleValue("ABC");
//        assertThat(list1.size(), is(2));
//    }


}
