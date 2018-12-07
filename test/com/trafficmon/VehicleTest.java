package com.trafficmon;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;


public class VehicleTest
{

    @Test

    public void vehiclesAreEqual() {

        Vehicle a = new Vehicle("A123 XYZ");
        Vehicle b = new Vehicle("A123 XYZ");
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

    }

    @Test
    public void vehiclesAreNotEqual()
    {
        Vehicle c = new Vehicle("A123 XYZ");
        Vehicle d = new Vehicle("J091 4PY");
        assertFalse(c.equals(d));
        assertFalse(d.equals(c));
    }
    

}


