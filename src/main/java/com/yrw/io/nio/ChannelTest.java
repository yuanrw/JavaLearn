package com.yrw.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Channel通道
 * Buffer缓冲区 本质上就是一块内存，可写数据、读数据
 *
 * @author yrw
 * @since 2018/4/4
 */
public class ChannelTest {
  public void read() throws IOException {
    RandomAccessFile file = new RandomAccessFile("/Users/yrw/Desktop/nio/channelTest.txt", "rw");
    FileChannel fileChannel = file.getChannel();
    //设定buffer的大小
    ByteBuffer buffer = ByteBuffer.allocate(48);
    //把数据从channel读到buffer，返回得到的字节数
    int byteReads = fileChannel.read(buffer);
    while (byteReads != -1) {
      System.out.println("read: " + byteReads);
      //首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据
      buffer.flip();
      //把buffer中的字打印出来
      while (buffer.hasRemaining()) {
        System.out.print((char) buffer.get());
      }
      //清空buffer
      buffer.clear();
      //继续读
      byteReads = fileChannel.read(buffer);
    }
    //关闭通道
    fileChannel.close();
    //关闭流
    file.close();
  }

  public void write() throws IOException {
    RandomAccessFile file = new RandomAccessFile("/Users/yrw/Desktop/nio/channelTest.txt", "rw");
    FileChannel fileChannel = file.getChannel();
    //设定buffer的大小
    ByteBuffer buffer = ByteBuffer.allocate(48);
    //把数据写到buffer
    buffer.put("going going going on!".getBytes("UTF-8"));
    //切换到读状态（读到channel）
    buffer.flip();
    //无法保证write()一次能向FileChannel写入多少字节，
    //因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
    while (buffer.hasRemaining()) {
      int byteWrites = fileChannel.write(buffer);
      System.out.println("write: " + byteWrites);
    }
    //清空buffer
    buffer.clear();
    //关闭通道
    fileChannel.close();
    //关闭流
    file.close();
  }

  public static void main(String[] args) throws IOException {
    ChannelTest test = new ChannelTest();
    test.write();
    test.read();
  }
}
