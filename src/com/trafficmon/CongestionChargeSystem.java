package com.trafficmon;

import java.math.BigDecimal;
import java.util.*;

public class CongestionChargeSystem {

    //list of entry and exit events
    private final List<ZoneBoundaryCrossing> eventLog = new ArrayList<ZoneBoundaryCrossing>();

    private Map<Vehicle, List<ZoneBoundaryCrossing>> crossingsByVehicle = new HashMap<Vehicle, List<ZoneBoundaryCrossing>>();

    protected int size() {
        return eventLog.size();
    }

    protected Map<Vehicle, List<ZoneBoundaryCrossing>> getCrossingsByVehicle() {
        return crossingsByVehicle;
    }


    protected List<ZoneBoundaryCrossing> getCrossingsByVehicleRegistrationNumber(String registration) {
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

    public void calculateCharges() {

        for (ZoneBoundaryCrossing crossing : eventLog) {
            if (!crossingsByVehicle.containsKey(crossing.getVehicle())) {
                crossingsByVehicle.put(crossing.getVehicle(), new ArrayList<ZoneBoundaryCrossing>());
            }
            crossingsByVehicle.get(crossing.getVehicle()).add(crossing);
        }

        checkCharges();

    }

    private void checkCharges() {
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
            }
        }
    }


    protected BigDecimal calculateChargeForTimeInZone(List<ZoneBoundaryCrossing> crossings) {

        BigDecimal charge = new BigDecimal(0);

        int counter = 1;
        boolean chargeThisTime = true;

        ZoneBoundaryCrossing lastEvent = crossings.get(0);

        long totalDuration = 0;


        // Loop over all the crossings for this vehicle, starting at the second one
        for (ZoneBoundaryCrossing crossing : crossings.subList(1, crossings.size())) {

            // Calculate the difference between the last crossing and this one
            long duration = secondsBetween(lastEvent.timestamp(), crossing.timestamp());

            // If we are entering after less than four hours, don't charge on the next exit event
            if (crossing instanceof EntryEvent && counter > 1) {
                if (duration < 14400) {
                    chargeThisTime = false;
                }
                else {
                    chargeThisTime = true;
                }
            }

            // Only process charging on each exit
            if (crossing instanceof ExitEvent) {

                // Keep track of the total amount of time inside the charge zone
                totalDuration += duration;

                // If inside for more than 4 hours in total, always charge the £12
                if (totalDuration > 14400) {
                    charge = new BigDecimal(12.00);
                    break;
                }


                if (crossings.size() <= 2 || chargeThisTime) {

                    if (lastEvent instanceof EntryEvent && duration > 14400) {
                        charge = charge.add(new BigDecimal(12.00));
                    }

                    if (lastEvent instanceof EntryEvent && lastEvent.timestampHour() < 14 && duration <= 14400) {
                        charge = charge.add(new BigDecimal(6.00));
                    }

                    if (lastEvent instanceof EntryEvent && lastEvent.timestampHour() >= 14 && duration <= 14400) {
                        charge = charge.add(new BigDecimal(4.00));
                    }
                }

            }

            lastEvent = crossing;
            counter++;

        }

        return charge;
    }

    private boolean previouslyRegistered(Vehicle vehicle) {
        for (ZoneBoundaryCrossing crossing : eventLog) {
            if (crossing.getVehicle().equals(vehicle)) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkOrderingOf(List<ZoneBoundaryCrossing> crossings) {

        ZoneBoundaryCrossing lastEvent = crossings.get(0);

        for (ZoneBoundaryCrossing crossing : crossings.subList(1, crossings.size())) {
            if (crossing.timestamp() < lastEvent.timestamp()) {
                return false;
            }
            if (crossing instanceof EntryEvent && lastEvent instanceof EntryEvent) {
                return false;
            }
            if (crossing instanceof ExitEvent && lastEvent instanceof ExitEvent) {
                return false;
            }
            lastEvent = crossing;
        }

        return true;
    }


    protected long secondsBetween(long startSeconds, long endSeconds) {
        return endSeconds - startSeconds;
    }

}
