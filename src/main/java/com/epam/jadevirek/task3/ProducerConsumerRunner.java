package com.epam.jadevirek.task3;

public class ProducerConsumerRunner {

    public static void main(String[] args) {
        final MessageQueue producer = new MessageQueue();

        final Thread producerThread = new Thread(() -> {
            try {
                while (true) {
                    producer.produce();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        final Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    producer.consumeMessage();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
