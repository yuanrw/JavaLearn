package com.yrw.concurrent.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 周期性处理任务，在这个例子中有一对的任务（100个），
 * 希望每10个为一组进行处理，当前仅当上一组任务处理完成后才能进行下一组，
 * 另外在每一组任务中，当任务剩下50%，30%以及所有任务执行完成时向观察者发出通知。
 *
 * @author yrw
 * @since 2018/4/24
 */
public class CyclicBarrierDemo {

  final CyclicBarrier barrier;

  final int MAX_TASK;

  public CyclicBarrierDemo(int cnt) {
    barrier = new CyclicBarrier(cnt + 1);
    MAX_TASK = cnt;
  }

  public void doWork(final Runnable work) {
    new Thread(() -> {
      work.run();
      try {
        //返回准备好的线程的数量
        //索引时从任务数-1开始的，第一个执行完成的任务索引为parties-1,最后一个为0，
        //这个parties为总任务数，清单中是cnt+1
        int index = barrier.await();
        doWithIndex(index);
      } catch (InterruptedException e) {
        return;
      } catch (BrokenBarrierException e) {
        return;
      }
    }).start();
  }

  private void doWithIndex(int index) {
    if (index == MAX_TASK / 3) {
      System.out.println("Left 30%.");
    } else if (index == MAX_TASK / 2) {
      System.out.println("Left 50%");
    } else if (index == 0) {
      System.out.println("run over");
    }
  }

  public void waitForNext() {
    try {
      doWithIndex(barrier.await());
    } catch (InterruptedException e) {
      return;
    } catch (BrokenBarrierException e) {
      return;
    }
  }

  public static void main(String[] args) {
    final int count = 10;
    CyclicBarrierDemo demo = new CyclicBarrierDemo(count);
    for (int i = 0; i < 100; i++) {
      demo.doWork(() -> {
        //do something
        try {
          Thread.sleep(1000L);
        } catch (Exception e) {
          return;
        }
      });
      if ((i + 1) % count == 0) {
        demo.waitForNext();
      }
    }
  }
}
