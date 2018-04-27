package com.yrw.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author yrw
 * @since 2018/3/29
 * <p>
 * AtomicIntegerFieldUpdater<T>/AtomicLongFieldUpdater<T>/AtomicReferenceFieldUpdater<T,V>是基于反射的原子更新字段的值。
 * 约束：
 * （1）字段必须是volatile类型的。
 * （2）字段的描述类型（修饰符public/protected/default/private）是与调用者与操作对象字段的关系一致。
 * 也就是说调用者能够直接操作对象字段，那么就可以反射进行原子操作。
 * 但是对于父类的字段，子类是不能直接操作的，尽管子类可以访问父类的字段。
 * （3）只能是实例变量，不能是类变量，也就是说不能加static关键字。
 * （4）只能是可修改变量，不能使final变量，因为final的语义就是不可修改。实际上final的语义和volatile是有冲突的，这两个关键字不能同时存在。
 * （5）对于AtomicIntegerFieldUpdater和AtomicLongFieldUpdater只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。
 * 如果要修改包装类型就需要使用AtomicReferenceFieldUpdater。
 * <p>
 * AtomicMarkableReference/AtomicStampedReference在解决“ABA问题”上很有用
 */
public class AtomicFieldUpdaterTest {

  class DemoData {
    public volatile int value1 = 1;
    volatile int value2 = 2;
    //protected/private 是不行的
//    protected volatile int value3 = 3;
//    private volatile int value4 = 4;
  }

  AtomicIntegerFieldUpdater<DemoData> getUpdater(String fieldName) {
    return AtomicIntegerFieldUpdater.newUpdater(DemoData.class, fieldName);
  }

  void doit() {
    DemoData data = new DemoData();
    System.out.println("1 ==> " + getUpdater("value1").getAndSet(data, 10));
    System.out.println("3 ==> " + getUpdater("value2").incrementAndGet(data));
//    System.out.println("2 ==> " + getUpdater("value3").decrementAndGet(data));
//    System.out.println("true ==> " + getUpdater("value4").compareAndSet(data, 4, 5));
  }

  public static void main(String[] args) {
    AtomicFieldUpdaterTest demo = new AtomicFieldUpdaterTest();
    demo.doit();
  }
}
