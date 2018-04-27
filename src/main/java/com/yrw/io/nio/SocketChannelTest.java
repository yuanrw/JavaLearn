package com.yrw.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * SocketChannel是一个连接到TCP网络套接字的通道。可以通过以下2种方式创建SocketChannel：
 * 1.打开一个SocketChannel并连接到互联网上的某台服务器。
 * 2.一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。
 *
 * @author yrw
 * @since 2018/4/8
 */
public class SocketChannelTest {
  public void read() throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    //设置非阻塞模式，connect(),read(),write()都会在完成之前就返回，
    //最好配合selector使用，要验证它是否准备好
    socketChannel.configureBlocking(false);
    socketChannel.connect(new InetSocketAddress("www.google.com", 80));
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    while (socketChannel.finishConnect()) {
      int bytesRead = socketChannel.read(buffer);
      while (bytesRead != -1) {
        buffer.flip();
        System.out.println(bytesRead + "bytes has been read");
        while (buffer.hasRemaining()) {
          System.out.print((char) buffer.get());
        }
        buffer.clear();
        bytesRead = socketChannel.read(buffer);
      }
    }
    socketChannel.close();
  }

  public void write() throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);
    socketChannel.connect(new InetSocketAddress("www.google.com", 80));
    ByteBuffer buffer = ByteBuffer.allocate(48);
    buffer.put("hello!".getBytes("UTF-8"));
    while (socketChannel.finishConnect()) {
      while (buffer.hasRemaining()) {
        int bytesWrite = socketChannel.write(buffer);
        System.out.println(bytesWrite + " bytes has been written.");
      }
    }
    socketChannel.close();
  }

  public static void main(String[] args) throws IOException {
    SocketChannelTest test = new SocketChannelTest();
    test.read();
    test.write();
  }
}
