package com.gurunelee.application;

import com.gurunelee.application.speedometerimpl.SpeedResult;

import java.util.Map;

public interface UrlSpeedometer {
    void setUrls(String... urls);

    SpeedResult getResponseTimesAsync();

    SpeedResult getResponseTimesSync();

    Map<String, Long> getResponseTimes();
}
