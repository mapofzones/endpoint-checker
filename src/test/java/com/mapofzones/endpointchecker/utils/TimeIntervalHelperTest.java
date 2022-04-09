package com.mapofzones.endpointchecker.utils;

import com.mapofzones.endpointchecker.common.dto.TimeIntervalsDto;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeIntervalHelperTest {

    @Test
    public void parseTimeIntervalsTest() {

        LocalDateTime t = LocalDateTime.now().minus(Duration.parse("p3d"));
        TimeIntervalsDto dto = TimeIntervalsHelper.parseTimeIntervals("pt1m:p3d:300;pt5h:p10d:300;pt24h:p30d:300;pt48h:p60d:300;p90d:-:300");
        System.out.println();
    }

}
