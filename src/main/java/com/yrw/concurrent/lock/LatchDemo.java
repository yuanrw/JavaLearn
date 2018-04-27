package com.yrw.concurrent.lock;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁，多个线程等待一个条件
 *
 * @author yrw
 * @since 2018/4/19
 */
public class LatchDemo {
  public long timecost(final int times, final Runnable task) throws InterruptedException {
    if (times <= 0) {
      throw new IllegalArgumentException();
    }
    //startLatch满足一次
    final CountDownLatch startLatch = new CountDownLatch(1);
    //overLatch满足times次，相当于把overLatch拆分成times份
    final CountDownLatch overLatch = new CountDownLatch(times);
    for (int i = 0; i < times; i++) {
      new Thread(() -> {
        try {
          startLatch.await();
          task.run();
        } catch (InterruptedException ex) {
          Thread.currentThread().interrupt();
        } finally {
          overLatch.countDown();
        }
      }).start();
    }

    long start = System.nanoTime();
    //打开大门
    System.out.println("start all tasks!");
    startLatch.countDown();
    //overLatch没满足times次之前会阻塞
    overLatch.await();
    System.out.println("all tasks have been handled");
    return System.nanoTime() - start;
  }

  public static void main(String[] args) throws InterruptedException {
    LatchDemo latchDemo = new LatchDemo();
    Long time = latchDemo.timecost(5, () -> System.out.println("it's a task!"));
    System.out.println("takes time: " + time);
  }
}
