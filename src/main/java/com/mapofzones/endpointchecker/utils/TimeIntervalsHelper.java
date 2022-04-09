package com.mapofzones.endpointchecker.utils;

import com.mapofzones.endpointchecker.common.dto.TimeIntervalsDto;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeIntervalsHelper {

    private static final String TIME_INTERVAL_DELIMITER = ";";
    private static final String TIME_INTERVAL_VALUES_DELIMITER = ":";
    private static final LocalDateTime MIN_TIME = LocalDateTime.of(2000, 1, 1, 0,0);

    public static TimeIntervalsDto parseTimeIntervals(String timeIntervals) {
        TimeIntervalsDto timeIntervalsDto = new TimeIntervalsDto();

        String[] timeIntervalArray = timeIntervals.split(TIME_INTERVAL_DELIMITER);

        LocalDateTime previousLastWorkedTime = LocalDateTime.now();
        for (String currentTimeInterval : timeIntervalArray) {
            String[] timeIntervalValues = currentTimeInterval.split(TIME_INTERVAL_VALUES_DELIMITER);

            LocalDateTime dateTimeFrom = previousLastWorkedTime;
            Integer pageSize = Integer.parseInt(timeIntervalValues[2]);
            LocalDateTime timeToCheck = LocalDateTime.now().minus(Duration.parse(timeIntervalValues[0]));

            LocalDateTime dateTimeTo;
            if (timeIntervalValues[1].equals("-")) {
                dateTimeTo = MIN_TIME;
            } else {
                dateTimeTo = LocalDateTime.now().minus(Duration.parse(timeIntervalValues[1]));
            }

            timeIntervalsDto.addInterval(dateTimeFrom, dateTimeTo, timeToCheck, pageSize);
            previousLastWorkedTime = dateTimeTo;
        }
        return timeIntervalsDto;
    }
}
