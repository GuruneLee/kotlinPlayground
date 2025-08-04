package com.gurunelee.application.speedometerimpl;

import com.gurunelee.application.UrlSpeedometer;
import com.gurunelee.networking.UrlCaller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.gurunelee.util.Logger.threadLog;

public class ThreadUrlSpeedometer implements UrlSpeedometer {
    private final List<String> urls = new ArrayList<>();
    private final UrlCaller urlCaller = new UrlCaller();

    private final ConcurrentMap<String, Long> responseTimes = new ConcurrentHashMap<>();

    @Override
    public void setUrls(String... urls) {
        this.urls.clear();
        this.urls.addAll(Arrays.asList(urls));
    }

    @Override
    public SpeedResult getResponseTimesAsync() {
        List<Thread> list = this.urls.stream().map(this::createThread).toList();

        long startTime = System.currentTimeMillis();

        list.forEach(Thread::start);
        list.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted", e);
            }
        });
        long endTime = System.currentTimeMillis();

        return new SpeedResult(
                responseTimes,
                endTime - startTime
        );
    }

    private Thread createThread(String url) {
        return new Thread(() -> {
            long responseTime = urlCaller.callAndMeasure(url);

            responseTimes.put(url, responseTime);

            threadLog(
                    "URL: " + url + ", Response Time: " + responseTime + " ms"
            );
        });
    }

    @Override
    public SpeedResult getResponseTimesSync() {
        long startTime = System.currentTimeMillis();

        this.urls.forEach(url -> {
            long responseTime = urlCaller.callAndMeasure(url);

            responseTimes.put(url, responseTime);

            threadLog(
                    "URL: " + url + ", Response Time: " + responseTime + " ms"
            );
        });
        long endTime = System.currentTimeMillis();

        return new SpeedResult(
                responseTimes,
                endTime - startTime
        );
    }

    @Override
    public Map<String, Long> getResponseTimes() {
        return Map.copyOf(responseTimes);
    }
}
