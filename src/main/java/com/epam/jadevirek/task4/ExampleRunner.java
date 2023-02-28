package com.epam.jadevirek.task4;

import java.util.function.Supplier;

public class ExampleRunner {
    public static void main(String[] args) {
        // Create a pool of 10 Integer objects
        GenericBlockingQueue<Integer> pool = new GenericBlockingQueue<Integer>(10,(Supplier<Integer>)() ->  Integer.valueOf(
                (int) (Math.random()+3)));

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    Integer object = pool.get();
                    System.out.println("Thread " + Thread.currentThread().getId() + " got object: " + object);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pool.take(object);
                    System.out.println("Thread " + Thread.currentThread().getId() + " took object: " + object);
                }
            }).start();
        }
    }
}
