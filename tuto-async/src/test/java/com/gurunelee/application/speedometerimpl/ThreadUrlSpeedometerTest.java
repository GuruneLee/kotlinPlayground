package com.gurunelee.application.speedometerimpl;

import com.gurunelee.application.UrlSpeedometer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.gurunelee.util.Logger.threadLog;

class ThreadUrlSpeedometerTest {
    @Test
    void measure_multiple_urls_async() {
        // given
        UrlSpeedometer speedometer = new ThreadUrlSpeedometer();
        List<String> urls = new ArrayList<>();
        IntStream.range(0, 1000).forEach(i -> {
            urls.add("http://example.com/" + i);
        });
        speedometer.setUrls(urls.toArray(new String[0]));

        // when
        SpeedResult result = speedometer.getResponseTimesAsync();

        // then
        result.responseTimes().forEach((url, responseTime) -> {
            threadLog("URL: " + url + ", Response Time: " + responseTime + " ms");
        });
        threadLog("Total Time: " + result.measurementTime() + " ms");
    }

    @Test
    void measure_multiple_urls_sync() {
        // given
        UrlSpeedometer speedometer = new ThreadUrlSpeedometer();
        List<String> urls = new ArrayList<>();
        IntStream.range(0, 100).forEach(i -> {
            urls.add("http://example.com/" + i);
        });
        speedometer.setUrls(urls.toArray(new String[0]));

        // when
        SpeedResult result = speedometer.getResponseTimesSync();

        // then
        result.responseTimes().forEach((url, responseTime) -> {
            threadLog("URL: " + url + ", Response Time: " + responseTime + " ms");
        });
        threadLog("Total Time: " + result.measurementTime() / 1000 + " s");
    }
}