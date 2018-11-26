package com.trafficmon;

import static junit.framework.TestCase.assertTrue;
//import static org.hamcrest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
//import static junit.*;
import org.junit.Test;


public class testVehicle{

    @Test

    public void vehiclesAreEqual() {

        //Vehicle a = new Vehicle.withRegistration("ABC");
        Vehicle a = new Vehicle("ABC");
        //Vehicle b = new Vehicle.withRegistration("ABC");
        Vehicle b = new Vehicle("ABC");

        //assertThat(a.equals(b), is(true));
        assertTrue(a.equals(b));
        //assertThat(b.equals(a), is(true));
        assertTrue(b.equals(a));

    }

    @Test
    public void vehiclesAreNotEqual()
    {
        Vehicle c = new Vehicle("DEF");
        Vehicle d = new Vehicle("GHI");
        assertFalse(c.equals(d));
        assertFalse(d.equals(c));
    }
    

}


