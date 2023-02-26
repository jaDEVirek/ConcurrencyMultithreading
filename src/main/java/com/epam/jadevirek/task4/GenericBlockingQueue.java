package com.epam.jadevirek.task4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

public class GenericBlockingQueue<T> {
    private BlockingQueue<T> pool;
    private final Supplier<T> factory;

    public GenericBlockingQueue(int size, Supplier<T> factory) {
        pool = new LinkedBlockingQueue<>(size);
        this.factory = factory;
        for (int i = 0; i < size; i++) {
            try {
                pool.put(factory.get());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create object of type " + factory.getClass().getName(), e);
            }
        }
    }

    public T get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
            return null;
        }
    }

    public void take(T object) {
        try {
            pool.put(object);
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
        }
    }

    public int size() {
        return pool.size();
    }

    /**
     * Allow the pool to be dynamically resized and refilled with new objects of the same type.
     *
     * @param numObjectsToAdd specifies the number of new objects to add to the pool
     * @param clazz           specifies the type of the objects to add.
     */
    public void refill(int numObjectsToAdd, Class<T> clazz) {
        pool.stream()
                .filter(object -> object.getClass()
                        .equals(clazz))
                .limit(numObjectsToAdd)
                .forEach(object -> pool.offer(object));

        int numObjectsAdded = (int) pool.stream()
                .filter(object -> object.getClass()
                        .equals(clazz))
                .count();

        for (int i = numObjectsAdded; i < numObjectsToAdd; i++) {
            try {
                pool.put(clazz.getDeclaredConstructor()
                        .newInstance());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create object of type " + clazz.getName(), e);
            }
        }
    }
}
