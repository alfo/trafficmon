package com.trafficmon;

import org.joda.time.LocalTime;

public class Clock {

    private static Integer fakeHour;
    private static Integer fakeMinute;
    private static Integer fakeSecond;

    protected static void setFakeTime(Integer hour, Integer minute, Integer second) {
        fakeHour = hour;
        fakeMinute = minute;
        fakeSecond = second;
    }

    // Used to remove a fakeTime in between tests
    protected static void clear() {
        fakeHour = null;
        fakeMinute = null;
        fakeSecond = null;
    }

    protected static Integer getCurrentHour() {
        if (fakeHour != null) {
            return fakeHour;
        } else {
            return LocalTime.now().getHourOfDay();
        }
    }

    protected static Integer getCurrentMinute() {
        if (fakeMinute != null) {
            return fakeMinute;
        } else {
            return LocalTime.now().getMinuteOfHour();
        }
    }

    protected static Integer getCurrentSecond() {
        if (fakeSecond != null) {
            return fakeSecond;
        } else {
            return LocalTime.now().getSecondOfMinute();
        }
    }

}
