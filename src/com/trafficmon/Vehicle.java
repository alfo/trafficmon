package com.trafficmon;

import java.util.Objects;

public class Vehicle {

    private final String registration;

    Vehicle(String registration) {
        this.registration = registration;
    }

    public static Vehicle withRegistration(String registration) {
        return new Vehicle(registration);
    }

    @Override
    public String toString() {
        return "Vehicle [" + registration + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (Objects.equals(registration, vehicle.registration))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        return registration != null ? registration.hashCode() : 0;
    }
}

