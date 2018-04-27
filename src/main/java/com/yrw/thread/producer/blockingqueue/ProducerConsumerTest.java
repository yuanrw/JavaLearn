package com.yrw.thread.producer.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author yrw
 * @since 2018/4/12
 */
public class ProducerConsumerTest {
  public static void main(String[] args) {

    BlockingQueue<String> queue = new LinkedBlockingDeque<>();
    Thread producer = new Thread(new Producer(queue, 2));
    producer.start();
    Thread consumer1 = new Thread(new Consumer(queue));
    consumer1.start();
    Thread consumer2 = new Thread(new Consumer(queue));
    consumer2.start();
  }
}
