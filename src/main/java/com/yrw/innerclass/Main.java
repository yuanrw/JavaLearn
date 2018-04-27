package com.yrw.innerclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author yrw
 * @since 2018/3/28
 */
public class Main {

  private float f = 1.0f;
  private static float ff = 1.0f;

  private class InnerClass {
    public float func() {
      return f;
    }
  }

  abstract class InnerClass1 {
    public abstract float func();
  }

  //只有static内部类可以有static方法
  static class InnerClass2 {
    protected static float func() {
      //只能访问static域
      return ff;
    }
  }

  public static void main(String[] args) {
    //访问内部类必须先实例化一个外部类
    MyClass myClass = new MyClass();
    MyClass.People inClass = myClass.new People();
    inClass.introduce();
    List<Integer> list = new ArrayList<>();
    //匿名内部类
    Collections.sort(list, new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return Integer.compare(o1, o2);
      }
    });
    //lambda
    Collections.sort(list, (o1, o2) -> Integer.compare(o1, o2));
  }
}
