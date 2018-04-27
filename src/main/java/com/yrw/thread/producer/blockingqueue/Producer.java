package com.yrw.thread.producer.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author yrw
 * @since 2018/4/12
 */
public class Producer implements Runnable {
  private BlockingQueue<String> blockingQueue;
  private int consumerNum;
  private final String FINAL = "FIN";

  public Producer(BlockingQueue<String> blockingQueue, int consumerNum) {
    this.blockingQueue = blockingQueue;
    this.consumerNum = consumerNum;
  }

  public void run() {
    for (int i = 0; i < 100; i++) {
      try {
        blockingQueue.put("data_" + i);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    //结束符
    for (int i = 0; i < consumerNum; i++) {
      try {
        blockingQueue.put(FINAL);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    System.out.println("Producer over");
  }
}
