package com.epam.jadevirek.task3;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class MessageQueue {
    private final Queue<Message> queue = new LinkedList<>();
    private boolean isNotWritable;

    private static int idSequence = 0;
    ;

    public MessageQueue() {
        this.isNotWritable = true;
    }

    private void add(Message message) {
        synchronized (queue) {
            queue.add(message);
        }
    }

    private Message remove() {
        synchronized (queue) {
            return queue.poll();
        }
    }

    public synchronized void produce() throws InterruptedException {
        while (!isNotWritable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread()
                        .interrupt();
            }
        }
        final Message message = this.generateMessage();
        this.add(message);
    }

    public synchronized void consumeMessage() throws InterruptedException {
        while (!isNotWritable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread()
                        .interrupt();
            }
        }
        this.useMessage(this.remove());
    }

    public void stop(boolean isNotWritable) {
        isNotWritable = isNotWritable;
        notifyAll();
    }

    private Message generateMessage() throws InterruptedException {
        Message message = new Message(++idSequence, Math.random());
        System.out.printf("[%s] Generated Message. Id: %d, Data: %f\n", Thread.currentThread()
                .getName(), message.getId(), message.getData());
        //Sleeping on random time to make it realistic
        sleep((long) (message.getData() * 100));
        return message;
    }

    private void useMessage(Message message) throws InterruptedException {
        if (message != null) {
            System.out.printf("[%s] Consuming Message. Id: %d, Data: %f\n", Thread.currentThread()
                    .getName(), message.getId(), message.getData());

            //Sleeping on random time to make it realistic
            sleep((long) (message.getData() * 100));
        }
    }
}


