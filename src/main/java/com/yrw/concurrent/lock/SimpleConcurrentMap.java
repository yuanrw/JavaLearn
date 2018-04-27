package com.yrw.concurrent.lock;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 公平性
 * 非公平锁（默认）
 * 公平锁 利用AQS的CLH队列，释放当前保持的锁（读或写）时，
 * 优先为等待时间最长的那个写线程分配写入锁，当前前提是写线程的等待时间要比所有读线程的等待时间要长。
 * 同样一个线程持有写入锁或者有一个写线程已经在等待了，那么试图获取公平锁的（非重入）所有线程（包括读写线程）都将被阻塞，直到最先的写线程释放锁。
 * 如果读线程的等待时间比写线程的等待时间还有长，那么一旦上一个写线程释放锁，这一组读线程将获取锁。
 * <p>
 * 重入性
 * 允许读线程和写线程按照请求锁的顺序重新获取读取锁或者写入锁。当然了只有写线程释放了锁，读线程才能获取重入锁。
 * 写线程获取写入锁后可以再次获取读取锁，但是读线程获取读取锁后却不能获取写入锁。
 * 读写锁最多支持65535个递归写入锁和65535个递归读取锁。
 * <p>
 * 锁降级
 * 写线程获取写入锁后可以获取读取锁，然后释放写入锁，这样就从写入锁变成了读取锁。
 * <p>
 * 锁升级
 * 读取锁是不能直接升级为写入锁的。因为获取一个写入锁需要释放所有读取锁，所以如果有两个读取锁视图获取写入锁而都不释放读取锁时就会发生死锁。
 * <p>
 * 锁获取中断
 * 读取锁和写入锁都支持获取锁期间被中断。这个和独占锁一致。
 * <p>
 * 条件变量
 * 写入锁提供了条件变量(Condition)的支持，读取锁不允许获取条件变量，将得到一个UnsupportedOperationException异常。
 * <p>
 * 用读写锁实现一个线程安全的HashMap
 *
 * @author yrw
 * @since 2018/4/19
 */
public class SimpleConcurrentMap<K, V> implements Map<K, V> {

  final ReadWriteLock lock = new ReentrantReadWriteLock();

  //读锁
  final Lock r = lock.readLock();

  //写锁
  final Lock w = lock.writeLock();

  final Map<K, V> map;

  public SimpleConcurrentMap(Map<K, V> map) {
    this.map = map;
    if (map == null) throw new NullPointerException();
  }

  public void clear() {
    w.lock();
    try {
      map.clear();
    } finally {
      w.unlock();
    }
  }

  public boolean containsKey(Object key) {
    r.lock();
    try {
      return map.containsKey(key);
    } finally {
      r.unlock();
    }
  }

  public boolean containsValue(Object value) {
    r.lock();
    try {
      return map.containsValue(value);
    } finally {
      r.unlock();
    }
  }

  public Set<Entry<K, V>> entrySet() {
    throw new UnsupportedOperationException();
  }

  public V get(Object key) {
    r.lock();
    try {
      return map.get(key);
    } finally {
      r.unlock();
    }
  }

  public boolean isEmpty() {
    r.lock();
    try {
      return map.isEmpty();
    } finally {
      r.unlock();
    }
  }

  public Set<K> keySet() {
    r.lock();
    try {
      return new HashSet<K>(map.keySet());
    } finally {
      r.unlock();
    }
  }

  public V put(K key, V value) {
    w.lock();
    try {
      return map.put(key, value);
    } finally {
      w.unlock();
    }
  }

  public void putAll(Map<? extends K, ? extends V> m) {
    w.lock();
    try {
      map.putAll(m);
    } finally {
      w.unlock();
    }
  }

  public V remove(Object key) {
    w.lock();
    try {
      return map.remove(key);
    } finally {
      w.unlock();
    }
  }

  public int size() {
    r.lock();
    try {
      return map.size();
    } finally {
      r.unlock();
    }
  }

  public Collection<V> values() {
    r.lock();
    try {
      return new ArrayList<V>(map.values());
    } finally {
      r.unlock();
    }
  }
}
