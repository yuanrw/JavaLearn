package com.yrw.thread;

/**
 * @author yrw
 * @since 2018/3/28
 */

/**
 * 单独调用interrupt方法可以使得处于阻塞状态的线程抛出一个异常，
 * 它可以用来中断一个正处于阻塞状态的线程，
 * 通过interrupt方法和isInterrupted()方法来停止正在运行的线程，
 * 不能中断正在运行中的线程。
 */
public class InterruptTest1 {

  public static void main(String[] args) {
    InterruptTest1 InterruptTest1 = new InterruptTest1();
    MyThread thread = InterruptTest1.new MyThread();
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
      try {
        System.out.println("进入睡眠状态");
        Thread.currentThread().sleep(10000);
        System.out.println("睡眠完毕");
      } catch (InterruptedException e) {
        System.out.println("得到中断异常");
      }
      System.out.println("run方法执行完毕");
    }
  }
}
