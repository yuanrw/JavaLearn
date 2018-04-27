package com.yrw.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用Buffer读写数据一般遵循以下四个步骤：
 * 1.写入数据到Buffer
 * 2.调用flip()方法
 * 3.从Buffer中读取数据
 * 4.调用clear()方法或者compact()方法
 * <p>
 * 当向buffer写入数据时，buffer会记录下写了多少数据。
 * 一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
 * 在读模式下，可以读取之前写入到buffer的所有数据。
 * <p>
 * 一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
 * 两种方式：clear()或compact()。
 * clear()方法会清空整个缓冲区。
 * compact()方法只会清除已经读过的数据。
 * 任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
 *
 * @author yrw
 * @since 2018/4/4
 */
public class BufferTest {

  public static void main(String[] args) throws IOException {
    RandomAccessFile file = new RandomAccessFile("/Users/yrw/Desktop/nio/bufferTest.txt", "rw");
    FileChannel channel = file.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(48);
    //向buffer中写数据两种方法：
    //从channel里读数据放进去
    channel.read(buffer);
    //直接放
    buffer.put("aaa".getBytes("UTF-8"));

    //向buffer中写数据两种方法：
    channel.write(buffer);
    buffer.get();
  }

}
