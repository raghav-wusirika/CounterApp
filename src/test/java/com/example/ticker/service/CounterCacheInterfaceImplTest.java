package com.example.ticker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class CounterCacheInterfaceImplTest {

    @Autowired
    CounterCacheInterface counterCacheInterface;
    static HashMap<String, Integer> addDataMap = new HashMap<>();
    static HashMap<String, Integer> delDataMap = new HashMap<>();

    static HashMap<String, Integer> addExactMap1 = new HashMap<>();
    static HashMap<String, Integer> addExactMap2 = new HashMap<>();
    static {

        addDataMap.put("ticker1", 10);
        addDataMap.put("ticker2", 10);
        addDataMap.put("ticker3", 11);
        addDataMap.put("ticker4", 11);
        addDataMap.put("ticker5", 12);
        addDataMap.put("ticker6", 13);
        addDataMap.put("ticker7", 14);
        addDataMap.put("ticker8", 15);
        addDataMap.put("ticker9", 16);
        addDataMap.put("ticker10", 16);
        addDataMap.put("ticker11", 17);
        addDataMap.put("ticker12", 18);
        addDataMap.put("ticker13", 0);


        delDataMap.put("ticker1", -10);
        delDataMap.put("ticker2", -10);
        delDataMap.put("ticker3", -11);
        delDataMap.put("ticker4", -11);
        delDataMap.put("ticker5", -12);
        delDataMap.put("ticker6", -13);
        delDataMap.put("ticker7", -14);
        delDataMap.put("ticker8", -15);
        delDataMap.put("ticker9", -16);
        delDataMap.put("ticker10", -16);
        delDataMap.put("ticker11", -17);
        delDataMap.put("ticker12", -18);
        delDataMap.put("ticker13", 0);

        addExactMap1.put("ticker1", 10);
        addExactMap1.put("ticker2", 10);
        addExactMap1.put("ticker3", 11);
        addExactMap1.put("ticker4", 11);
        addExactMap1.put("ticker5", 12);
        addExactMap1.put("ticker6", 13);
        addExactMap1.put("ticker7", 14);
        addExactMap1.put("ticker8", 15);
        addExactMap1.put("ticker9", 16);
        addExactMap1.put("ticker10", 16);

        addExactMap2.put("ticker11", 10);
        addExactMap2.put("ticker12", 10);
        addExactMap2.put("ticker13", 11);
        addExactMap2.put("ticker14", 11);
        addExactMap2.put("ticker15", 12);
        addExactMap2.put("ticker16", 13);
        addExactMap2.put("ticker17", 14);
        addExactMap2.put("ticker18", 15);
        addExactMap2.put("ticker19", 16);
        addExactMap2.put("ticker20", 16);

    }

    @Test
    void getMostReqData1() {
        List<String> expectedlist = new ArrayList<String>();
        expectedlist.add("ticker12");
        expectedlist.add("ticker11");
        expectedlist.add("ticker9");
        expectedlist.add("ticker10");
        expectedlist.add("ticker8");
        expectedlist.add("ticker7");
        expectedlist.add("ticker6");
        expectedlist.add("ticker5");
        expectedlist.add("ticker4");
        expectedlist.add("ticker3");

        Thread[] addDataThreads = new Thread[50];
        for (int i = 0; i < 50; i++) {
            addDataThreads[i] =new Thread(()->{
                counterCacheInterface.updateCounter(addDataMap, 10000);
            });
            addDataThreads[i].start();
        }

        Thread[] delDataThreads = new Thread[49];
        for (int i = 0; i < 49; i++) {
            delDataThreads[i] =new Thread(()->{
                counterCacheInterface.updateCounter(delDataMap, 10000);
            });
            delDataThreads[i].start();
        }

        for(Thread t : addDataThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Thread t : delDataThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*System.out.println(cacheInterface.getOrderList());
        System.out.println(cacheInterface.getTickerCounterMap().toString());*/
        List<String> list = counterCacheInterface.getMostReqData(10);
        Assertions.assertTrue(expectedlist.containsAll(list));
        Assertions.assertEquals(expectedlist,list);
    }

    @Test
    void getMostReqData2() {
        List<String> expectedlist = new ArrayList<String>();
        expectedlist.add("ticker13");
        expectedlist.add("ticker2");
        expectedlist.add("ticker1");
        expectedlist.add("ticker4");
        expectedlist.add("ticker3");
        expectedlist.add("ticker5");
        expectedlist.add("ticker6");
        expectedlist.add("ticker7");
        expectedlist.add("ticker8");
        expectedlist.add("ticker9");

        Thread[] addDataThreads = new Thread[50];
        for (int i = 0; i < 50; i++) {
            addDataThreads[i] =new Thread(()->{
                counterCacheInterface.updateCounter(addDataMap, 10000);
            });
            addDataThreads[i].start();
        }

        Thread[] delDataThreads = new Thread[52];
        for (int i = 0; i < 52; i++) {
            delDataThreads[i] =new Thread(()->{
                counterCacheInterface.updateCounter(delDataMap, 10000);
            });
            delDataThreads[i].start();
        }

        for(Thread t : addDataThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Thread t : delDataThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*System.out.println(cacheInterface.getOrderList());
        System.out.println(cacheInterface.getTickerCounterMap().toString());*/
        List<String> list = counterCacheInterface.getMostReqData(10);
        Assertions.assertTrue(expectedlist.containsAll(list));
        Assertions.assertEquals(expectedlist,list);
    }

    @Test
    void testExactMap() {
        List<String> expectedlist = new ArrayList<String>();
        expectedlist.add("ticker1");
        expectedlist.add("ticker2");
        expectedlist.add("ticker3");
        expectedlist.add("ticker4");
        expectedlist.add("ticker5");
        expectedlist.add("ticker6");
        expectedlist.add("ticker7");
        expectedlist.add("ticker8");
        expectedlist.add("ticker9");
        expectedlist.add("ticker10");

        Thread[] addDataThreads2 = new Thread[5];
        for (int i = 0; i < 5; i++) {
            addDataThreads2[i] =new Thread(()->{
                counterCacheInterface.updateCounter(addExactMap2, 10000);
            });
            addDataThreads2[i].start();
        }

        Thread forSleep = new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        forSleep.start();

        for(Thread t : addDataThreads2){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            forSleep.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread[] addDataThreads1 = new Thread[5];
        for (int i = 0; i < 5; i++) {
            addDataThreads1[i] =new Thread(()->{
                counterCacheInterface.updateCounter(addExactMap1, 10000);
            });
            addDataThreads1[i].start();
        }

        for(Thread t : addDataThreads1){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Thread forSleep2 = new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        forSleep2.start();
        try {
            forSleep2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> list = counterCacheInterface.getMostReqData(10);
        Assertions.assertTrue(expectedlist.containsAll(list));
    }
}