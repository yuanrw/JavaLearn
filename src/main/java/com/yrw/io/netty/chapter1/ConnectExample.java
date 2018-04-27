package com.yrw.io.netty.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author yrw
 * @since 2018/4/23
 */
public class ConnectExample {
  private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

  public static void connect() {
    Channel channel = CHANNEL_FROM_SOMEWHERE;
    // Does not block
    ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
    //注册ChannelFutureListener
    future.addListener(new ChannelFutureListener() {
      //回调方法，将会在对应的操作完成时被调用
      //监听器可以判断该操作是成功地完成了还是出错了
      //如果该操作是成功的，那么将数据写到该Channel。否则，要从ChannelFuture中检索对应的Throwable。
      @Override
      public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()) {
          ByteBuf buffer = Unpooled.copiedBuffer(
            "Hello", Charset.defaultCharset());
          ChannelFuture wf = future.channel().writeAndFlush(buffer);
          //process
        } else {
          Throwable cause = future.cause();
          cause.printStackTrace();
        }
      }
    });

  }
}
