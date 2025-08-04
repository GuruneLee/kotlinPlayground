package com.gurunelee.application.speedometerimpl;

import java.util.HashMap;
import java.util.Map;

public record SpeedResult(Map<String, Long> responseTimes, long measurementTime) {
    public SpeedResult(Map<String, Long> responseTimes, long measurementTime) {
        this.responseTimes = new HashMap<>(responseTimes);
        this.measurementTime = measurementTime;
    }

    @Override
    public Map<String, Long> responseTimes() {
        return new HashMap<>(responseTimes);
    }
}
