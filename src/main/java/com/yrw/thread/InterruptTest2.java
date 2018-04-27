package com.yrw.thread;

/**
 * @author yrw
 * @since 2018/3/28
 */

/**
 * 一般情况下不建议通过这种方式来中断线程，
 * 一般会在MyThread类中增加一个属性 isStop来标志是否结束while循环，
 * 然后再在while循环中判断isStop的值。
 * 那么就可以在外面通过调用setStop方法来终止while循环。
 */
public class InterruptTest2 {

  public static void main(String[] args) {
    InterruptTest2 test = new InterruptTest2();
    MyThread thread = test.new MyThread();
    thread.start();
    try {
      Thread.currentThread().sleep(2000);
    } catch (InterruptedException e) {

    }
    thread.interrupt();
  }

  class MyThread extends Thread {
    @Override
    public void run() {
      int i = 0;
      while (!isInterrupted() && i < Integer.MAX_VALUE) {
        System.out.println(i + " while循环");
        i++;
      }
    }
  }
}
