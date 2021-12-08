package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class MonitorStatus {
    @JsonProperty("Start")
    public Timestamp start;
    @JsonProperty("Bytes")
    public Long bytes;
    @JsonProperty("Samples")
    public Long samples;
    @JsonProperty("InstRate")
    public Long instRate;
    @JsonProperty("CurRate")
    public Long curRate;
    @JsonProperty("AvgRate")
    public Long avgRate;
    @JsonProperty("PeakRate")
    public Long peakRate;
    @JsonProperty("BytesRem")
    public Long bytesRem;
    @JsonProperty("Duration")
    public Long duration;
    @JsonProperty("Idle")
    public Long idle;
    @JsonProperty("TimeRem")
    public Long timeRem;
    @JsonProperty("Progress")
    public Long progress;
    @JsonProperty("Active")
    public Boolean active;
}
