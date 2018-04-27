package com.yrw.io.netty.Chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author yrw
 * @since 2018/4/25
 */
public class ChannelOperationExamples {
  private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

  public static void writingToChannel() {
    Channel channel = CHANNEL_FROM_SOMEWHERE;
    ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
    ChannelFuture cf = channel.writeAndFlush(buf);
    cf.addListener((future) -> {
      if (future.isSuccess()) {
        System.out.println("Write successful");
      } else {
        System.out.println("Write error");
        future.cause().printStackTrace();
      }
    });
  }

  public static void writingToChannelFromManyThreads() {
    final Channel channel = CHANNEL_FROM_SOMEWHERE;
    final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
    Runnable writer = () -> channel.write(buf.duplicate());
    //线程池
    Executor executor = Executors.newCachedThreadPool();

    executor.execute(writer);
    executor.execute(writer);
  }

  public static void main(String[] args) {
    ChannelOperationExamples.writingToChannel();
  }
}
