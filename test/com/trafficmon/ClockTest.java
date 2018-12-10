package com.trafficmon;

import org.joda.time.LocalTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ClockTest {

    @Test

    public void testHourRealTime() {

        Clock.clear();
        Integer hour = Clock.getCurrentHour();
        assertThat(hour, is(LocalTime.now().getHourOfDay()));

    }

    @Test

    public void testMinuteRealTime() {

        Clock.clear();
        Integer minute = Clock.getCurrentMinute();
        assertThat(minute, is(LocalTime.now().getMinuteOfHour()));

    }

    @Test

    public void testFakeClock() {

        Integer fakeHour = 14;
        Integer fakeMinute = 20;
        Integer fakeSecond = 10;

        Clock.setFakeTime(fakeHour, fakeMinute, fakeSecond);

        assertThat(Clock.getCurrentHour(), is(fakeHour));
        assertThat(Clock.getCurrentMinute(), is(fakeMinute));
        assertThat(Clock.getCurrentSecond(), is(fakeSecond));

    }

}
