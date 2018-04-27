package com.yrw.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * incrementAndGet()用了CAS方法
 *
 * @author yrw
 * @since 2018/3/29
 */
public class AtomicIntegerTest {

  public static void main(String[] args) throws InterruptedException {
    final AtomicInteger value = new AtomicInteger(10);
    System.out.println(value.compareAndSet(1, 2));
    System.out.println(value.get());
    System.out.println(value.compareAndSet(10, 3));
    System.out.println(value.get());
    value.set(0);

    System.out.println(value.incrementAndGet());
    System.out.println(value.getAndAdd(2));
    System.out.println(value.getAndSet(5));
    System.out.println(value.get());

    final int threadSize = 10;
    Thread[] ts = new Thread[threadSize];
    for (int i = 0; i < threadSize; i++) {
      ts[i] = new Thread() {
        public void run() {
          value.incrementAndGet();
        }
      };
    }

    for (Thread t : ts) {
      t.start();
    }
    for (Thread t : ts) {
      t.join();
    }

    System.out.println(value.get());
  }
}
