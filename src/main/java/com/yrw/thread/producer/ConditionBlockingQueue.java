package com.yrw.thread.producer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过Lock和Condition条件变量实现阻塞队列
 *
 * @author yrw
 * @since 2018/4/12
 */
public class ConditionBlockingQueue<T> implements IBlockingQueue<T> {

  //利用数组来实现
  private final Object[] items;
  int putptr, takeptr, count;

  private final Lock lock = new ReentrantLock();
  private final Condition notFull = lock.newCondition();
  private final Condition notEmpty = lock.newCondition();

  public ConditionBlockingQueue() {
    this(10);
  }

  public ConditionBlockingQueue(int queueSize) {
    if (queueSize < 1) {
      throw new IllegalArgumentException("queueSize must be positive number");
    }
    items = new Object[queueSize];
  }

  @Override
  public void produce(T data) throws InterruptedException {
    lock.lock();
    try {
      while (count == items.length) {
        notFull.await();
      }
      items[putptr] = data;
      if (++putptr == items.length) {
        putptr = 0;
      }
      count++;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  @Override
  public T consume() throws InterruptedException {
    lock.lock();
    try {
      while (count == 0) {
        notEmpty.wait();
      }
      T data = (T) items[takeptr];
      if (++takeptr == items.length) {
        takeptr = 0;
      }
      count--;
      notFull.signal();
      return data;
    } finally {
      lock.unlock();
    }
  }
}
