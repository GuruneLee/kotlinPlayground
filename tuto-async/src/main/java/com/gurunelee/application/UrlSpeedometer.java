package com.gurunelee.application;

import java.util.Map;

public interface UrlSpeedometer {
    Long startAndMeasure(String... urls);

    Map<String, Long> getResponseTimes();
}
