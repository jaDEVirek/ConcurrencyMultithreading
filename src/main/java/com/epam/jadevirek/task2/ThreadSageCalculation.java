package com.epam.jadevirek.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class ThreadSageCalculation {
    private static final List<Integer> numbers = new ArrayList<>();
    private static final BlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<>();
    private static volatile boolean keepAdding = true;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void initializeThreads() throws InterruptedException {

        Thread addingThread = new Thread(() -> {
            while (keepAdding) {
                int num = (int) (Math.random() + 5);
                synchronized (numbers) {
                    numbers.add(num);
                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread summingThread = new Thread(() -> {
            while (keepAdding) {
                synchronized (numbers) {
                    int sum = 0;
                    for (int num : numbers) {
                        sum += num;
                    }
                    System.out.println("Current sum: " + sum);
                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread sqrtThread = new Thread(() -> {
            while (keepAdding) {
                double sumOfSquares = 0;
                synchronized (numbers) {
                    for (int num : numbers) {
                        sumOfSquares += Math.pow(num, 2);
                    }
                    double sqrt = Math.sqrt(sumOfSquares);
                    System.out.println("Square root of sum of squares: " + sqrt);
                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.interrupted();
                }
            }
        });
        threadQueue.add(addingThread);
        threadQueue.add(summingThread);
        threadQueue.add(sqrtThread);
        startThreads();
    }

    private static void startThreads() throws InterruptedException {
        System.out.println("Starting....");
        Thread.sleep(500);

        threadQueue.forEach(runnable -> new Thread(runnable).start());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        keepAdding = false;
    }
}

