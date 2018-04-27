package com.yrw.thread.producer;

/**
 * @author yrw
 * @since 2018/4/12
 */
public interface IBlockingQueue<T> {

  void produce(T data) throws InterruptedException;

  T consume() throws InterruptedException;
}
