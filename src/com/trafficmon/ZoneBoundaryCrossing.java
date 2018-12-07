package com.trafficmon;

public abstract class ZoneBoundaryCrossing {

    private final Vehicle vehicle;
    private final int hour;
    private final int min;
    private final int sec;


    public ZoneBoundaryCrossing(Vehicle vehicle) {
        this.vehicle = vehicle;

        this.hour = Clock.getCurrentHour();
        this.min = Clock.getCurrentMinute();
        this.sec = Clock.getCurrentSecond();


    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long timestamp() {
        long time = (hour * 3600) + (min * 60) + sec;
        return time;
    }

    public int timestampHour()
    {
        return hour;
    }


}
