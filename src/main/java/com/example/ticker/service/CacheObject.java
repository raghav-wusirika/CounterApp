package com.example.ticker.service;

import java.lang.ref.SoftReference;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class CacheObject implements Delayed {
    private final String key;
    private final long expiryTime;
    private final int count;

    public CacheObject(String key, int count, long expiryTime) {
        this.key = key;
        this.expiryTime = System.currentTimeMillis() + expiryTime;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public int getCount() {
        return count;
    }

    public int getDecrementCount() {
        return (~(count - 1));
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(expiryTime, ((CacheObject) o).expiryTime);
    }

    @Override
    public String toString() {
        return "CacheObject{" +
                "key='" + key + '\'' +
                ", expiryTime=" + expiryTime +
                ", count=" + count +
                '}';
    }
}
