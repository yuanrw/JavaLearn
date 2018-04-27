package com.yrw.thread.producer.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author yrw
 * @since 2018/4/12
 */
public class Consumer implements Runnable {
  private BlockingQueue<String> queue;
  private final String FINAL = "FIN";

  public Consumer(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  public void run() {

    while (true) {
      String data;
      try {
        data = queue.take();
        System.out.println("Consumer " + Thread.currentThread().getName() + " consume:" + data);
        if (FINAL.equals(data)) {
          break;
        }
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    System.out.println("Consumer over");
  }
}
