package com.epam.jadevirek.task1;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentHashMapExample implements Runnable {

    static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    static List<String> curentElementsOfLocalThread = new ArrayList<>();

    public synchronized void testMethodSynchronized() throws InterruptedException {

        threadLocal.set("String hashcode".concat(String.valueOf(Math.random()+5)));
        System.out.println("Setting from " + threadLocal.hashCode());
        String threadName = Thread.currentThread()
                .getName();
        System.out.println(threadName + " : Started....");
        Thread.sleep(1000);
        System.out.println(threadName + " : Finished....");
        curentElementsOfLocalThread.add(threadLocal.get());
        System.out.println("ThreadLocal: " + hashCode() + " value : " + threadLocal.get());

        final String s = curentElementsOfLocalThread.get(0);
        final String s1 = curentElementsOfLocalThread.get(1);
        System.out.println(s==s1);
        System.out.println(s.hashCode());
        System.out.println(s1.hashCode());
    }

    @Override public void run() {
        try {
            testMethodSynchronized();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread firstThread = new Thread(new ConcurrentHashMapExample());
        firstThread.start();
        Thread secondThread = new Thread(new ConcurrentHashMapExample());

        secondThread.start();
    }
}
