package com.example.ticker.service;

import java.util.HashMap;
import java.util.List;

public interface CounterCacheInterface {

    public void updateCounter(HashMap<String, Integer> newData, long periodInMillis);

    public List<String> getMostReqData(int count);

}
