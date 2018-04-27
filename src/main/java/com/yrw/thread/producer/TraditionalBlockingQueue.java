package com.yrw.thread.producer;

import java.util.LinkedList;

/**
 * 使用 Object.wait()/notifyAll() 来实现阻塞队列。
 * 判定 LinkedList大小为0或者大于等于queueSize时须使用 while (condition) {}，
 * 不能使用 if(condition) {}。它又被叫做“自旋锁”。
 * 为防止该线程没有收到notify()调用也从wait()中返回（也称作虚假唤醒），
 * 这个线程会重新去检查condition条件以决定当前是否可以安全地继续执行还是需要重新保持等待，
 * 而不是认为线程被唤醒了就可以安全地继续执行了。
 * <p>
 * 在take方法取走一个元素后须调用 lock.notifyAll();，如果使用 lock.notify();
 * 在某些情况下会导致 生产者-消费者 同时处于阻塞状态。
 * <p>
 * 一旦线程调用了wait()方法，它就释放了所持有的监视器对象上的锁。
 * 这将允许其他线程也可以调用wait()或者notify()。
 * <p>
 * 被唤醒的线程必须重新获得监视器对象的锁，才可以退出wait()的方法调用，因为wait方法调用运行在同步块里面。
 * 如果多个线程被notifyAll()唤醒，那么在同一时刻将只有一个线程可以退出wait()方法，
 * 因为每个线程在退出wait()前必须获得监视器对象的锁。
 *
 * @author yrw
 * @since 2018/4/12
 */
public class TraditionalBlockingQueue<T> implements IBlockingQueue<T> {

  //队列的最大大小
  private int queueSize;
  private LinkedList<T> list = new LinkedList<>();
  private Object lock = new Object();
  private static int taskSum = 0;

  public TraditionalBlockingQueue() {
    this(10);
  }

  public TraditionalBlockingQueue(int queueSize) {
    if (queueSize < 1) {
      throw new IllegalArgumentException("queueSize must be positive number");
    }
    this.queueSize = queueSize;
  }

  @Override
  public void produce(T data) throws InterruptedException {
    synchronized (lock) {
      while (list.size() >= queueSize) {
        lock.wait();
      }
      //一旦队列中加入了一个task
      list.offer(data);
      System.out.println("生产了一个任务，队列中剩余" + (++taskSum));
      //唤醒所有等待lock的线程，此时等待的只可能是消费者
      lock.notifyAll();
    }
  }

  @Override
  public T consume() throws InterruptedException {
    synchronized (lock) {
      while (list.size() < 1) {
        lock.wait();
      }
      T data = list.remove();
      System.out.println("消费了一个任务，队列中剩余" + (--taskSum));
      //唤醒所有等待lock的线程，此时等待的只可能是生产者
      lock.notifyAll();
      return data;
    }
  }
}
