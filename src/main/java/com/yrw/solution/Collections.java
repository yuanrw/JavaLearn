package com.yrw.solution;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yrw
 * @since 2018/4/23
 */
public class Collections {

  public static void main(String[] args) {

    HashMap hashMap = new HashMap();

    /**LinkedHashMap是HashMap的一个子类，保存了记录的插入顺序，
     * 在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的，
     * 也可以在构造时带参数，按照访问次序排序。
     */
    HashMap linkedHashMap = new LinkedHashMap();

    /**
     * TreeMap实现SortedMap接口，能够把它保存的记录根据键排序，
     * 默认按键值的升序排序，也可以指定排序的比较器，
     * 当用Iterator遍历TreeMap时，得到的记录是排过序的。
     * 如果使用排序的映射，建议使用TreeMap。
     * 在使用TreeMap时，key必须实现Comparable接口或者在构造TreeMap传入自定义的Comparator，
     * 否则会在运行时抛出java.lang.ClassCastException类型的异常。
     */
    TreeMap treeMap = new TreeMap();

    ConcurrentMap concurrentMap = new ConcurrentHashMap();

    List linkedList = new LinkedList();
    List arrayList = new ArrayList();
    TreeSet treeSet = new TreeSet();
    List<String> list = new ArrayList<>();
  }
}
