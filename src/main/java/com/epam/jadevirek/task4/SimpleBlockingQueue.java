package com.epam.jadevirek.task4;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  The LinkedBlockingQueue used in the implementation is a concurrent collection
 *  that is designed to be used in a multithreaded environment.
 */
public class SimpleBlockingQueue {

    private BlockingQueue<Object> pool;

    public SimpleBlockingQueue(int size) {
        pool = new LinkedBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(new Object());
        }
    }

    public Object get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void take(Object object) {
        try {
            pool.put(object);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
