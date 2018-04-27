package com.yrw.concurrent.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 用ConcurrentHashMap实现单例模式
 *
 * @author yrw
 * @since 2018/4/3
 */
public class ConcurrentMapSingle {

  private static final ConcurrentMap<String, ConcurrentMapSingle> map = new ConcurrentHashMap<String, ConcurrentMapSingle>();
  private static ConcurrentMapSingle instance;

  public static ConcurrentMapSingle getInstance() {
    if (instance == null) {
      map.putIfAbsent("INSTANCE", new ConcurrentMapSingle());
      instance = map.get("INSTANCE");
    }
    return instance;
  }

  public static void main(String[] args) {
    ConcurrentMap<String, String> safeMap = new ConcurrentHashMap<>();
    /**
     * 等价操作：
     * if (!map.containsKey(key))
     * return map.put(key, value);
     * else
     * return map.get(key);
     */
    safeMap.putIfAbsent("key1", "value1");
    System.out.println(safeMap.get("key1"));
    /**
     * if (map.containsKey(key) && map.get(key).equals(value)) {
     * map.remove(key);
     * return true;
     * }
     * return false;
     */
    safeMap.remove("key1", "value2");
    System.out.println(safeMap.get("key1"));

    /**
     * if (map.containsKey(key) && map.get(key).equals(oldValue)) {
     * map.put(key, newValue);
     * return true;
     * }
     * return false;
     */
    safeMap.replace("key1", "value1", "value2");
    System.out.println(safeMap.get("key1"));

    safeMap.remove("key1", "value2");
    System.out.println(safeMap.get("key1"));
  }
}
