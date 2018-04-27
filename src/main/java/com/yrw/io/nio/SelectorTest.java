package com.yrw.io.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author yrw
 * @since 2018/4/4
 */
public class SelectorTest {
  public static void main(String[] args) throws IOException {
    //一个空的Selector
    Selector selector = Selector.open();
    SocketChannel channel = SocketChannel.open();
    //将channel切换到非阻塞模式
    channel.configureBlocking(false);
    //注册channel，标注对什么事件感兴趣，让selector管理
    SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
  }
}
