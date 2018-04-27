package com.yrw.innerclass;

/**
 * @author yrw
 * @since 2018/3/28
 */
class WithInner {
  class Inner {

  }
}

class InheritInner extends WithInner.Inner {

  //InheritInner() 是不能通过编译的，一定要加上形参
  //wi是指向外部类的引用
  InheritInner(WithInner wi) {
    //如果继承内部类，必须有这句调用
    wi.super();
  }

  public static void main(String[] args) {
    WithInner wi = new WithInner();
    InheritInner obj = new InheritInner(wi);
  }
}
