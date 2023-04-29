package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonitorStatus {
    @JsonProperty("Start")
    private Timestamp start;
    @JsonProperty("Bytes")
    private Long bytes;
    @JsonProperty("Samples")
    private Long samples;
    @JsonProperty("InstRate")
    private Long instRate;
    @JsonProperty("CurRate")
    private Long curRate;
    @JsonProperty("AvgRate")
    private Long avgRate;
    @JsonProperty("PeakRate")
    private Long peakRate;
    @JsonProperty("BytesRem")
    private Long bytesRem;
    @JsonProperty("Duration")
    private Long duration;
    @JsonProperty("Idle")
    private Long idle;
    @JsonProperty("TimeRem")
    private Long timeRem;
    @JsonProperty("Progress")
    private Long progress;
    @JsonProperty("Active")
    private Boolean active;

    public Timestamp getStart() {
        return start;
    }

    public Long getBytes() {
        return bytes;
    }

    public Long getSamples() {
        return samples;
    }

    public Long getInstRate() {
        return instRate;
    }

    public Long getCurRate() {
        return curRate;
    }

    public Long getAvgRate() {
        return avgRate;
    }

    public Long getPeakRate() {
        return peakRate;
    }

    public Long getBytesRem() {
        return bytesRem;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getIdle() {
        return idle;
    }

    public Long getTimeRem() {
        return timeRem;
    }

    public Long getProgress() {
        return progress;
    }

    public Boolean getActive() {
        return active;
    }
}
