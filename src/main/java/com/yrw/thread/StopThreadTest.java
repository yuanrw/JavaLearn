package com.yrw.thread;

/**
 * @author yrw
 * @since 2018/3/28
 */

/**
 * 由于实际的业务需要，常常会遇到需要在特定时机终止某一线程的运行，使其进入到死亡状态。
 * 目前最通用的做法是设置一boolean型的变量，当条件满足时，使线程执行体快速执行完毕。
 */
public class StopThreadTest {

  public static void main(String[] args) {

    MyRunnable myRunnable = new MyRunnable();
    Thread thread = new Thread(myRunnable);

    for (int i = 0; i < 100; i++) {
      System.out.println(Thread.currentThread().getName() + " " + i);
      if (i == 30) {
        thread.start();
      }
      if(i == 40){
        myRunnable.stopThread();
      }
    }
  }
}

class MyRunnable implements Runnable {

  private boolean stop;

  @Override
  public void run() {
    for (int i = 0; i < 100 && !stop; i++) {
      System.out.println(Thread.currentThread().getName() + " " + i);
    }
  }

  public void stopThread() {
    this.stop = true;
  }

}
