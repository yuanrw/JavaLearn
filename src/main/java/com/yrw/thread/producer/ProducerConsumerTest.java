package com.yrw.thread.producer;

/**
 * @author yrw
 * @since 2018/4/12
 */
public class ProducerConsumerTest {

  public static void main(String[] args) {
    IBlockingQueue<Integer> queue = new TraditionalBlockingQueue<>();
    for (int i = 0; i < 10; i++) {
      //10个生产者线程
      Thread produceThread = new Thread(() -> {
        try {
          queue.produce(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
      //10个消费者线程
      Thread consumerThread = new Thread(() -> {
        try {
          queue.consume();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
      consumerThread.start();
      produceThread.start();
    }
  }
}
