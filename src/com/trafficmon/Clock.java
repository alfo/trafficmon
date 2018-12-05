package com.trafficmon;

import org.joda.time.LocalTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Clock {

    private static Integer fakeHour;
    private static Integer fakeMinute;
    private static Integer fakeSecond;

    public static void setFakeTime(Integer hour, Integer minute, Integer second) {
        fakeHour = hour;
        fakeMinute = minute;
        fakeSecond = second;
    }

    public static Integer getCurrentHour() {
        if (fakeHour != null) {
            return fakeHour;
        } else {
            return LocalTime.now().getHourOfDay();
        }
    }

    public static Integer getCurrentMinute() {
        if (fakeMinute != null) {
            return fakeMinute;
        } else {
            return LocalTime.now().getMinuteOfHour();
        }
    }

    public static Integer getCurrentSecond() {
        if (fakeSecond != null) {
            return fakeSecond;
        } else {
            return LocalTime.now().getSecondOfMinute();
        }
    }

}
