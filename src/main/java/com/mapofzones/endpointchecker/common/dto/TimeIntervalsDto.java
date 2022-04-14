package com.mapofzones.endpointchecker.common.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class TimeIntervalsDto {

    public List<TimeInterval> timeIntervals;

    public TimeIntervalsDto() {
        timeIntervals = new ArrayList<>();
    }

    public void addInterval(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, LocalDateTime timeToCheck, Integer pageSize) {
        timeIntervals.add(new TimeInterval(dateTimeFrom, dateTimeTo, timeToCheck, pageSize));
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class TimeInterval {
        private LocalDateTime dateTimeFrom;
        private LocalDateTime dateTimeTo;
        private LocalDateTime timeToCheck;
        private Integer pageSize;
    }

}
