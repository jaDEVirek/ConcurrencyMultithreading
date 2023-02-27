package com.epam.jadevirek.task1;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafeMap {
    private final Map<Integer,Integer> map;

    public ThreadSafeMap(Map<Integer, Integer> map) {
        this.map = Collections.synchronizedMap(map);
    }

    public static void main(String[] args) {
        Map<Integer,Integer> map = new ConcurrentHashMap<>();
        final ThreadSafeMap threadSafeMap = new ThreadSafeMap(map);

        threadSafeMap.initWriter().start();
        threadSafeMap.initSummer().start();
    }

   public Thread initWriter(){
      return new Thread(() -> {
           while(true) {
               final int number = (int) (Math.random() * (999 - 111 + 1)) + 5;
               map.put(number,number);
               System.out.println("Added element " + number);
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
       });
   }

    public Thread initSummer(){
        return new Thread(() -> {
            while(true) {
                final int sumOfElements = map.values()
                        .stream()
                        .mapToInt(val -> val)
                        .sum();
                System.out.println("Counter sum  " + sumOfElements);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
