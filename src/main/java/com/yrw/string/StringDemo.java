package com.yrw.string;

/**
 * Java中String不是基本类型，但是有些时候和基本类型差不多，如String b = "a"; 可以对变量直接赋值，而不用new一个对象
 * Java中的变量和基本类型的值存放于栈内存，而new出来的对象本身存放于堆内存，指向对象的引用还是存放在栈内存。
 * 例如如下的代码：
 * int i=1;
 * String s = new String("Hello World");
 * 变量i和s以及1存放在栈内存，而s指向的对象”Hello World”存放于堆内存。
 * 栈内存的数据共享，前面定义了i=1，i和1都在栈内存内，如果再定义一个j=1，此时将j放入栈内存，然后查找栈内存中是否有1，如果有则j指向1。
 * 如果再给j赋值2，则在栈内存中查找是否有2，如果没有就在栈内存中放一个2，然后j指向2。
 * 如果j++，这时指向的变量并不会改变，而是在栈内寻找新的常量（比原来的常量大1），如果栈内存有则指向它，如果没有就在栈内存中加入此常量并将j指向它。
 * 如定义i和j是都赋值1，则i==j结果为true。==用于判断两个变量指向的地址是否一样。
 * 对于直接赋值的字符串常量（如String s=“Hello World”；中的Hello World）也是存放在栈内存中，而new出来的字符串对象（即String对象）是存放在堆内存中。
 * String s= "Hello World"和String w="Hello World"，s==w，因为他们指向同一个Hello World。
 *
 * @author yrw
 * @since 2018/3/29
 */
public class StringDemo {

  //常量池
  private static final String MESSAGE = "ab";

  public static void main(String[] args) {

    //相当于s1 = "ab"; 编译出来的字节码是一样的
    String s1 = "a" + "b";

    String s2 = "a";
    String s3 = "b";

    final String s4 = "a";
    final String s5 = "b";

    System.out.println(s1 == MESSAGE);   //true

    //在字符串相加中，只要有一个是非final类型的变量，编译器就不会优化，
    //因为这样的变量可能发生改变，编译器不可能将这样的变量替换成常量。
    System.out.println((s2 + s3) == MESSAGE);    //false
    System.out.println((s4 + s5) == MESSAGE);    //true

    //intern()方法会先检查String池 (或者说成栈内存)中是否存在相同的字符串常量，如果有就返回。
    System.out.println((s2 + s3).intern() == MESSAGE);    //true
  }
}


