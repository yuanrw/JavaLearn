package com.yrw.innerclass;

/**
 * @author yrw
 * @since 2018/3/28
 * <p>
 * 为什么在Java中需要内部类？总结一下主要有以下四点：
 * 1.每个内部类都能独立的继承一个接口的实现，所以无论外部类是否已经继承了某个(接口的)实现，对于内部类都没有影响。内部类使得多继承的解决方案变得完整，
 * 2.方便将存在一定逻辑关系的类组织在一起，又可以对外界隐藏。
 * 3.方便编写事件驱动程序
 * 4.方便编写线程代码
 * <p>
 * 内部类分类：成员内部类、静态内部类、匿名内部类、局部内部类
 */
public class MyClass {

  private int a = 1;

  // 成员内部类，
  // private只有外部类能访问
  // protected同一个包或继承外部类,
  // 成员内部类中不能有静态变量和方法，静态内部类可以
  protected class People {
    private String name = "name";

    public void introduce() {
      System.out.println(a);
      System.out.println(name);
    }
  }

  public People getWoman() {
    int notFinal = 10;
    //局部内部类，不能有修饰符，就像方法里的一个局部变量
    class Woman extends People {
      public void print() {
        //此处不能使用notFinal，只能用final的局部变量
        System.out.println(a);
      }
    }
    return new Woman();
  }

  //和静态成员属性有点类似，并且它不能使用外部类的非static成员变量或者方法
  //比如HashMap
  static class Inner {
    public Inner() {

    }
  }
}
