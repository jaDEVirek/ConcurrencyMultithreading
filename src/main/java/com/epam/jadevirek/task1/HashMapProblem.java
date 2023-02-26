package com.epam.jadevirek.task1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class HashMapProblem {
    private static final int NUM_THREADS = 2;
    private static final int NUM_ELEMENTS = 10000;

    public static void main(String[] args) {
        final Map<Integer, Integer> map = new HashMap<>();
        // TODO with ConcurrentHashMap.
        final ConcurrentHashMap<Integer, Integer> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
        final List<Thread> threads = getThreadsForGivenMap(map);

        threads.get(0)
                .start();
        threads.get(1)
                .start();
        threads.forEach(thread ->
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static List<Thread> getThreadsForGivenMap(Map<Integer, Integer> map) {
        final Thread[] threads = new Thread[NUM_THREADS];
        // First thread adds elements to the map
        threads[0] = new Thread(() -> {
            for (int i = 0; i < NUM_ELEMENTS; i++) {
                map.put(i, i);
                System.out.println("Added element " + i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        threads[1] = new Thread(() -> {
            while (true) {
                Integer sum = map.keySet()
                        .stream()
                        .reduce(0, Integer::sum);
                System.out.println(Thread.currentThread()
                        .getName() + " sum: " + sum);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return Arrays.stream(threads)
                .collect(Collectors.toList());
    }
}