package com.example.ticker.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class CounterCacheInterfaceImpl implements CounterCacheInterface {

    private final ConcurrentHashMap<String, OrderObj> tickerCounterMap = new ConcurrentHashMap();

    private final ConcurrentSkipListSet<OrderObj> orderList = new ConcurrentSkipListSet<OrderObj>();

    private final DelayQueue<CacheObject> cleaningUpQueue = new DelayQueue<>();

    public CounterCacheInterfaceImpl() {
        Thread cacheCleaner = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    CacheObject dCacheObj = cleaningUpQueue.take();
                    tickerCounterMap.compute(dCacheObj.getKey(),(k,v)->{
                        orderList.remove(v);
                        v.getCount().getAndAdd(dCacheObj.getDecrementCount());
                        if(v.getCount().get() != 0){
                            orderList.add(v);
                        }
                        return v;
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cacheCleaner.setDaemon(true);
        cacheCleaner.start();
    }

    public ConcurrentHashMap<String, OrderObj> getTickerCounterMap() {
        return tickerCounterMap;
    }

    public ConcurrentSkipListSet<OrderObj> getOrderList() {
        return orderList.clone();
    }

    @Override
    public List<String> getMostReqData(int count) {
        return orderList.parallelStream().limit(count).map(o->o.getTicker()).collect(Collectors.toList());
    }

    @Override
    public synchronized void updateCounter(HashMap<String, Integer> newData, long periodInMillis){
        if(newData == null || newData.isEmpty())
            return;
        newData.forEach((key, value) ->
                tickerCounterMap.compute(key, (k, v) -> {
                    if(v == null) {
                        v = new OrderObj(new AtomicInteger(value),k);
                        orderList.add(v);
                    }else{
                        orderList.remove(v);
                        v.getCount().getAndAdd(value);
                        orderList.add(v);
                    }
                    cleaningUpQueue.add(new CacheObject(k,value,periodInMillis));
                    return v;
                })
        );
    }

}
