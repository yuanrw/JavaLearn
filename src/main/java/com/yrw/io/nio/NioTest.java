package com.yrw.io.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 最简单的Reactor模式：注册所有感兴趣的事件处理器，单线程轮询选择就绪事件，执行事件处理器。
 * <p>
 * 打开一个Selector，注册一个通道注册到这个Selector上(通道的初始化过程略去),
 * 然后持续监控这个Selector的四种事件（接受，连接，读，写）是否就绪。
 * <p>
 * 与Selector一起使用时，Channel必须处于非阻塞模式下。
 * 所以不能将FileChannel与Selector一起使用，FileChannel不能切换到非阻塞模式，套接字通道都可以。
 *
 * @author yrw
 * @since 2018/4/4
 */
public class NioTest {
  public static void main(String[] args) throws IOException {
    SocketChannel channel = SocketChannel.open();
    Selector selector = Selector.open();
    channel.configureBlocking(false);
    //注册channel，并标注感兴趣的事件
    channel.register(selector, SelectionKey.OP_READ);
    //一个线程持续监控这个Selector的四种事件（接受，连接，读，写）是否就绪
    while (true) {
      //返回就绪的事件数量
      int readyChannels = selector.select();
      if (readyChannels == 0) continue;
      //得到就绪事件的selectedKeys
      Set selectedKeys = selector.selectedKeys();
      Iterator keyIterator = selectedKeys.iterator();
      //遍历
      while (keyIterator.hasNext()) {
        SelectionKey key = (SelectionKey) keyIterator.next();
        if (key.isAcceptable()) {
          // a connection was accepted by a ServerSocketChannel.
        } else if (key.isConnectable()) {
          // a connection was established with a remote server.
        } else if (key.isReadable()) {
          // a channel is ready for reading
        } else if (key.isWritable()) {
          // a channel is ready for writing
        }
        //每次迭代末尾调用keyIterator.remove()。
        //Selector不会自己从已选择键集中移除SelectionKey实例，必须在处理完通道时自己移除。
        //下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
        keyIterator.remove();
      }
    }
  }
}
