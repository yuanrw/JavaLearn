package com.yrw.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * buffer首先被插入到数组，然后再将数组作为channel.read() 的输入参数。
 * read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，
 * 当一个buffer被写满后，channel紧接着向另一个buffer中写。
 * Scattering Reads在移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息。
 *
 * @author yrw
 * @since 2018/4/8
 */
public class ScatterGatherTest {
  public void scatterTest() throws IOException {
    RandomAccessFile file = new RandomAccessFile("/Users/yrw/Desktop/nio/ScatterGatherTest.txt", "rw");
    FileChannel fileChannel = file.getChannel();
    ByteBuffer header = ByteBuffer.allocate(6);
    ByteBuffer body = ByteBuffer.allocate(48);
    //会按顺序读到这些数组里
    ByteBuffer[] buffers = {header, body};
    long byteReads = fileChannel.read(buffers);
    while (byteReads != -1) {
      buffers[0].flip();
      System.out.println("header: ");
      while (buffers[0].hasRemaining()) {
        System.out.print((char) buffers[0].get());
      }
      System.out.println();
      buffers[1].flip();
      System.out.println("body: ");
      while (buffers[1].hasRemaining()) {
        System.out.print((char) buffers[1].get());
      }
      System.out.println();
      //清空buffer
      for (ByteBuffer b : buffers) {
        b.clear();
      }
      //继续读
      byteReads = fileChannel.read(buffers);
    }
    fileChannel.close();
    file.close();
  }

  public void gatherTest() throws IOException {
    RandomAccessFile file = new RandomAccessFile("/Users/yrw/Desktop/nio/ScatterGatherTest.txt", "rw");
    FileChannel fileChannel = file.getChannel();
    ByteBuffer header = ByteBuffer.allocate(48);
    ByteBuffer body = ByteBuffer.allocate(128);
    header.put("hello going on!".getBytes("UTF-8"));
    body.put("going going going on!".getBytes("UTF-8"));
    //write data into buffers
    ByteBuffer[] buffers = {header, body};
    for (ByteBuffer b : buffers) {
      b.flip();
    }
    long byteWrites = fileChannel.write(buffers);
    System.out.println(byteWrites + " bytes has been written");
    fileChannel.close();
    file.close();
  }

  public static void main(String[] args) throws IOException {
    ScatterGatherTest test = new ScatterGatherTest();
    //聚集写
    test.gatherTest();
    //聚集读
    test.scatterTest();
  }
}
