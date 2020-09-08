package com.example.ticker.service;

import org.springframework.core.annotation.Order;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderObj implements Comparable<OrderObj>{
    private AtomicInteger count;
    private String ticker;
    private Timestamp time;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public OrderObj(AtomicInteger count, String ticker) {
        this.count = count;
        this.ticker = ticker;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public AtomicInteger getCount(){
        return this.count;
    }

    public String getTicker(){
        return this.ticker;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderObj{" +
                "count=" + count +
                ", ticker='" + ticker + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public int compareTo(OrderObj o) {
        if (o.count.get() == count.get()) {
            if (o.time.compareTo(time) == 0) {
                return o.ticker.compareTo(ticker);
            }else{
                return o.time.compareTo(time);
            }
        }else{
            return ((Integer)o.count.get()).compareTo((Integer)count.get());
        }
        /*if(o.ticker.compareTo(ticker) != 0) {
            if (o.count.compareTo(count) == 0) {
                if (o.time.compareTo(time) == 0) {
                    return o.ticker.compareTo(ticker);
                }else{
                    return o.time.compareTo(time);
                }
            }else{
                return o.count.compareTo(count);
            }
        }else{
            return o.ticker.compareTo(ticker);
        }*/
    }
}
