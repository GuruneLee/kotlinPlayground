package com.gurunelee.application.speedometerimpl;

import com.gurunelee.application.UrlSpeedometer;

import java.util.Map;

public class CFUrlSpeedometer implements UrlSpeedometer {
    @Override
    public void setUrls(String... urls) {

    }

    @Override
    public SpeedResult getResponseTimesAsync() {
        return null;
    }

    @Override
    public SpeedResult getResponseTimesSync() {
        return null;
    }

    @Override
    public Map<String, Long> getResponseTimes() {
        return Map.of();
    }
}
