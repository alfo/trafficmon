package com.trafficmon;

import java.math.BigDecimal; //used to deal with doubles accurately
import java.time.LocalTime;
import java.util.*;

public class CongestionChargeSystem {

    public static final BigDecimal CHARGE_RATE_POUNDS_PER_MINUTE = new BigDecimal(0.05);  //sets value of 5p

    private final List<ZoneBoundaryCrossing> eventLog = new ArrayList<ZoneBoundaryCrossing>(); //list of entry and exit events

    private Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicle = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();

    protected Map<Vehicle, List<ZoneBoundaryCrossing>> getCrossingsByVehicle() {
        return crossingsByVehicle;
    }


    protected List<ZoneBoundaryCrossing> getCrossingsByVehicleValue(String registration) {
        Vehicle key = new Vehicle(registration);
        return crossingsByVehicle.get(key);
    }


    public void vehicleEnteringZone(Vehicle vehicle) {
        eventLog.add(new EntryEvent(vehicle));
    }

    public void vehicleLeavingZone(Vehicle vehicle) {
        if (!previouslyRegistered(vehicle)) {
            return;
        }
        eventLog.add(new ExitEvent(vehicle));
    }

    //parent class
    public void calculateCharges() {

//        Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicle = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();

        for (ZoneBoundaryCrossing crossing : eventLog) {
            if (!crossingsByVehicle.containsKey(crossing.getVehicle())) {
                crossingsByVehicle.put(crossing.getVehicle(), new ArrayList<ZoneBoundaryCrossing>());
            } //if no existing crossings for vehicle, add a new crossing
            crossingsByVehicle.get(crossing.getVehicle()).add(crossing);
        }

        for (Map.Entry<Vehicle, List<ZoneBoundaryCrossing>> vehicleCrossings : crossingsByVehicle.entrySet()) {
            Vehicle vehicle = vehicleCrossings.getKey();
            List<ZoneBoundaryCrossing> crossings = vehicleCrossings.getValue();

            if (!checkOrderingOf(crossings)) {
                OperationsTeam.getInstance().triggerInvestigationInto(vehicle);
            } else {

                BigDecimal charge = calculateChargeForTimeInZone(crossings);

                try {
                    RegisteredCustomerAccountsService.getInstance().accountFor(vehicle).deduct(charge);
                } catch (InsufficientCreditException ice) {
                    OperationsTeam.getInstance().issuePenaltyNotice(vehicle, charge);
                } catch (AccountNotRegisteredException e) {
                    OperationsTeam.getInstance().issuePenaltyNotice(vehicle, charge);
                }
            } //do we need to check this for loop?
        }
        //need to check actions done by this function
        //create a hashmap matching a vehicle to its zone boundary crossing - this is the time it crossed?
        //for a crossing time in event log, if the hashmap does not contain a certain vehicle, add the vehicle and its zone boundary crossing to the hashmap
        //else, if the vehicle already exists in the hashmap, add the crossing time to the vehicle

    }

    // change the most
    private BigDecimal calculateChargeForTimeInZone(List<ZoneBoundaryCrossing> crossings) {

        BigDecimal charge = new BigDecimal(0);

        ZoneBoundaryCrossing lastEvent = crossings.get(0);

        for (ZoneBoundaryCrossing crossing : crossings.subList(1, crossings.size())) {

//            if (crossing instanceof ExitEvent) {
//                charge = charge.add(
//                        new BigDecimal(minutesBetween(lastEvent.timestamp(), crossing.timestamp()))
//                                .multiply(CHARGE_RATE_POUNDS_PER_MINUTE));
//            }
//
//            if(crossing instanceof EntryEvent && crossing.timestamp() == System.currentTimeMillis())
//            {
//                charge = charge.add(new BigDecimal(0.10));
//            }

            int duration = minutesBetween(lastEvent.timestamp(), crossing.timestamp());
            if(crossing instanceof ExitEvent)
            {
                if(lastEvent instanceof EntryEvent && lastEvent.timestampHour() < 14 && duration <= 4)
                {
                    charge = charge.add(new BigDecimal(6.00));
                }

                if(lastEvent instanceof EntryEvent && lastEvent.timestampHour() >= 14 && duration <= 4)
                {
                    charge = charge.add(new BigDecimal(4.00));
                }

                if(lastEvent instanceof EntryEvent && duration > 4)
                {
                    charge = charge.add(new BigDecimal(12.00));
                }
            }




            lastEvent = crossing;

        }

        return charge;
    }

//    protected void leaveAndComeBackWithin4Hours()
//    {
//        for (Vehicle vehicle : crossingsByVehicle.keySet())
//        {
//            //entryEvent.timestampHours() - exitEvent.timestampHours() < 4
//        }
//    }

    private boolean previouslyRegistered(Vehicle vehicle) {
        for (ZoneBoundaryCrossing crossing : eventLog) {
            if (crossing.getVehicle().equals(vehicle)) {
                return true;
            }
        }
        return false;
    }

    //makes sure exit is after entry
    protected boolean checkOrderingOf(List<ZoneBoundaryCrossing> crossings) {

        ZoneBoundaryCrossing lastEvent = crossings.get(0);
        //0th index is the first vehicle that entered, for example in the day
        for (ZoneBoundaryCrossing crossing : crossings.subList(1, crossings.size())) {
            if (crossing.timestamp() < lastEvent.timestamp()) {
                return false;
            } //this means that crossing.timestamp() should be greater than lastEvent.timestamp()
            if (crossing instanceof EntryEvent && lastEvent instanceof EntryEvent) {
                return false;
            } //can't have two entries for one vehicle
            if (crossing instanceof ExitEvent && lastEvent instanceof ExitEvent) {
                return false;
            }
            lastEvent = crossing;
        }

        return true;
    }

    //just does subtraction for minutes
    protected int minutesBetween(long startTimeMs, long endTimeMs) {
        return (int) Math.ceil((endTimeMs - startTimeMs) / (1000.0 * 60.0));  //ceil rounds up
    }

    public int size() {
        return eventLog.size();
    }




}
