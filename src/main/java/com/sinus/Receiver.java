package com.sinus;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sinus on 09.02.16.
 */
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <"+message+">");
        latch.countDown();
    }

    public void receiveMessageExtra(String message) {
        System.out.println("Received EXTRA <"+message+">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
