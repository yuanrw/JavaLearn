package com.yrw.concurrent.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yrw
 * @since 2018/3/28
 * <p>
 * 参数：
 * corePoolSize：核心池的大小
 * <p>
 * maximumPoolSize：线程池最大线程数
 * <p>
 * keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止，
 * 只有当线程池中的线程数大于corePoolSize时，才会起作用。
 * 但是如果调用了allowCoreThreadTimeOut(boolean)方法，
 * 在线程池中的线程数不大于corePoolSize时，也会起作用，知道线程数为0。
 * <p>
 * unit：参数keepAliveTime的时间单位，有7种取值。
 * <p>
 * workQueue：一个阻塞队列，用来存储等待执行的任务，会对线程池的运行过程产生重大影响：
 * LinkedBlockingQueue 常用
 * SynchronousQueue 常用
 * PriorityBlockingQueue
 * ArrayBlockingQueue
 * <p>
 * threadFactory：线程工厂，主要用来创建线程。
 * <p>
 * handler：表示当拒绝处理任务时的策略，有以下四种取值：
 * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
 * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）。
 * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务。
 * <p>
 * Executor是一个顶层接口，在它里面只声明了一个方法execute(Runnable)，返回值为void，就是用来执行传进去的任务的。
 * ExecutorService接口继承了Executor接口，并声明了一些方法：submit、invokeAll、invokeAny以及shutDown等。
 * 抽象类AbstractExecutorService实现了ExecutorService接口，基本实现了ExecutorService中声明的所有方法。
 * ThreadPoolExecutor继承了类AbstractExecutorService。
 * <p>
 * 方法：
 * void execute(Runnable)：向线程池提交一个任务，交由线程池去执行。
 * Future<?> submit(Runnable)：和execute一样，只是有返回值
 * shutdown()：线程池处于SHUTDOWN状态。
 * shutdownNow()：线程池处于STOP状态。
 * <p>
 * 线程池状态和工作原理在execute方法源码的注释里。
 * <p>
 * 不提倡直接使用ThreadPoolExecutor，而是使用Executors类中提供的几个静态方法：
 * newFixedThreadPool：corePoolSize和maximumPoolSize值是相等的，使用LinkedBlockingQueue；
 * newSingleThreadExecutor：corePoolSize和maximumPoolSize都设置为1，使用LinkedBlockingQueue；
 * newCachedThreadPool：corePoolSize设置为0，将maximumPoolSize设置为Integer.MAX_VALUE，使用SynchronousQueue，
 * 也就是说来了任务就创建线程运行，当线程空闲超过60秒，就销毁线程。
 */
public class ThreadPoolTest {
  public static void main(String[] args) {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
      new ArrayBlockingQueue<>(5));

    for (int i = 0; i < 15; i++) {
      MyTask myTask = new MyTask(i);
      executor.execute(myTask);
      System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
        executor.getQueue().size() + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
    }
    executor.shutdown();
  }
}


class MyTask implements Runnable {
  private int taskNum;

  public MyTask(int num) {
    this.taskNum = num;
  }

  @Override
  public void run() {
    System.out.println("正在执行task " + taskNum);
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("task " + taskNum + "执行完毕");
  }
}
