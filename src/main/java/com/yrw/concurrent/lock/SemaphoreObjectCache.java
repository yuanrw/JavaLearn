package com.yrw.concurrent.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Semaphore相当于一个计数器，计数器减少到0了，所有线程阻塞，不可重入
 * 基于信号量实现的对象池
 *
 * @author yrw
 * @since 2018/4/19
 */
public class SemaphoreObjectCache<T> {

  public interface ObjectFactory<T> {

    T makeObject();
  }

  class Node {

    T obj;

    Node next;
  }

  final int capacity;

  final ObjectFactory<T> factory;

  final Lock lock = new ReentrantLock();

  final Semaphore semaphore;

  private Node head;

  private Node tail;

  public SemaphoreObjectCache(int capacity, ObjectFactory<T> factory) {
    this.capacity = capacity;
    this.factory = factory;
    this.semaphore = new Semaphore(this.capacity);
    this.head = null;
    this.tail = null;
  }

  public T getObject() throws InterruptedException {
    //信号量部位0就可以，不然就阻塞
    semaphore.acquire();
    return getNextObject();
  }

  private T getNextObject() {
    lock.lock();
    try {
      if (head == null) {
        return factory.makeObject();
      } else {
        Node ret = head;
        head = head.next;
        if (head == null) {
          tail = null;
        }
        //help GC
        ret.next = null;
        return ret.obj;
      }
    } finally {
      lock.unlock();
    }
  }

  private void returnObjectToPool(T t) {
    lock.lock();
    try {
      Node node = new Node();
      node.obj = t;
      if (tail == null) {
        head = tail = node;
      } else {
        tail.next = node;
        tail = node;
      }

    } finally {
      lock.unlock();
    }
  }

  public void returnObject(T t) {
    returnObjectToPool(t);
    semaphore.release();
  }


}
