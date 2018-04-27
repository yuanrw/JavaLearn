package com.yrw.io.netty.chapter1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yrw
 * @since 2018/4/23
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {
  //回调方法
  @Override
  public void channelActive(ChannelHandlerContext ctx)
    throws Exception {
    System.out.println(
      "Client " + ctx.channel().remoteAddress() + " connected");
  }
}
