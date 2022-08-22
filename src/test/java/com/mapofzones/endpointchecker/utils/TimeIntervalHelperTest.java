package com.mapofzones.endpointchecker.utils;

import com.mapofzones.endpointchecker.common.dto.TimeIntervalsDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeIntervalHelperTest {

    @Test
    public void parseTimeIntervalsTest() {

        TimeIntervalsDto dto = TimeIntervalsHelper.parseTimeIntervals("pt1m:p3d:300;pt5h:p10d:250;pt24h:p30d:200;pt48h:p60d:100;p90d:-:150");

        TimeIntervalsDto.TimeInterval timeInterval1 = dto.getTimeIntervals().get(0);
        TimeIntervalsDto.TimeInterval timeInterval2 = dto.getTimeIntervals().get(1);
        TimeIntervalsDto.TimeInterval timeInterval3 = dto.getTimeIntervals().get(2);
        TimeIntervalsDto.TimeInterval timeInterval4 = dto.getTimeIntervals().get(3);
        TimeIntervalsDto.TimeInterval timeInterval5 = dto.getTimeIntervals().get(4);

        assertAll(() -> {
            assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), timeInterval1.getDateTimeFrom().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval1.getDateTimeTo().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(1, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval1.getTimeToCheck().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(300, timeInterval1.getPageSize());

            assertEquals(LocalDateTime.now().minus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval2.getDateTimeFrom().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(10, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval2.getDateTimeTo().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(5, ChronoUnit.HOURS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval2.getTimeToCheck().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(250, timeInterval2.getPageSize());

            assertEquals(LocalDateTime.now().minus(10, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval3.getDateTimeFrom().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(30, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval3.getDateTimeTo().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(24, ChronoUnit.HOURS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval3.getTimeToCheck().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(200, timeInterval3.getPageSize());

            assertEquals(LocalDateTime.now().minus(30, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval4.getDateTimeFrom().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(60, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval4.getDateTimeTo().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(48, ChronoUnit.HOURS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval4.getTimeToCheck().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(100, timeInterval4.getPageSize());

            assertEquals(LocalDateTime.now().minus(60, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval5.getDateTimeFrom().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.of(2000, 1, 1, 0,0,0).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval5.getDateTimeTo().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(LocalDateTime.now().minus(90, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES),
                    timeInterval5.getTimeToCheck().truncatedTo(ChronoUnit.MINUTES));
            assertEquals(150, timeInterval5.getPageSize());
        });


    }

}
