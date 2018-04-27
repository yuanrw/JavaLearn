package com.yrw.thread;

/**
 * @author yrw
 * @since 2018/3/28
 */

/**
 * Thread实现了Runable接口
 * 重要属性:
 * name 线程名字 可通过构造函数设置
 * priority 优先级
 * daemon 是否守护线程
 * target 要执行的任务
 * <p>
 * 方法：
 * start
 * 让线程进入就绪状态，不能对同一线程对象两次调用start()方法。
 * <p>
 * sleep
 * 让出cpu权限，进入阻塞状态，时间到了之后回到就绪状态
 * <p>
 * yield
 * 让当前线程交出CPU权限，它跟sleep方法类似，同样不会释放锁。
 * yield方法只能让拥有相同优先级的线程有获取CPU执行时间的机会。
 * yield方法并不会让线程进入阻塞状态，而是让线程重回就绪状态，它只需要等待重新获取CPU执行时间，
 * 所以不能控制时间。
 * CPU调度线程具有一定的随机性，可能A线程调用了yield()方法后，接下来CPU仍然调度了A线程。
 * <p>
 * getPriority和setPriority
 * 用来获取和设置线程优先级。
 * <p>
 * setDaemon和isDaemon
 * 用来设置线程是否成为守护线程和判断线程是否是守护线程。
 * 守护线程和用户线程的区别在于：守护线程依赖于创建它的线程，而用户线程则不依赖。
 * 如果在main线程中创建了一个守护线程，当main方法运行完毕之后，守护线程也会随着消亡。
 * 而用户线程则不会，用户线程会一直运行直到其运行完毕。
 * 在JVM中，像垃圾收集器线程就是守护线程。
 */
public class ThreadTest {

  private int i = 0;
  private Object object = new Object();

  public static void main(String[] args) {
    ThreadTest threadTest = new ThreadTest();
    ThreadTest.MyThread thread1 = threadTest.new MyThread();
    ThreadTest.MyThread thread2 = threadTest.new MyThread();
    thread1.run();
    thread2.run();
  }

  //sleep方法会把cpu让出来，但是不会释放锁
  //当线程睡眠时间满后，不一定会立即得到执行，因为此时可能CPU正在执行其他的任务。
  //所以说调用sleep方法相当于让线程进入阻塞状态。
  class MyThread extends Thread {

    @Override
    public void run() {
      synchronized (object) {
        i++;
        System.out.println("i:" + i);
        try {
          System.out.println("线程" + Thread.currentThread().getName() + "进入睡眠状态");
          Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.println("线程" + Thread.currentThread().getName() + "睡眠结束");
        i++;
        System.out.println("i:" + i);
      }
    }
  }
}
