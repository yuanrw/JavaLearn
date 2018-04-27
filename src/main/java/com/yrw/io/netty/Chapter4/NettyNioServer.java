package com.yrw.io.netty.Chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * netty实现异步地服务器
 *
 * @author yrw
 * @since 2018/4/25
 */
public class NettyNioServer {
  public void server(int port) throws InterruptedException {
    final ByteBuf buf = Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8"));
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(group)
        .channel(NioServerSocketChannel.class)
        .localAddress(new InetSocketAddress(port))
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelActive(ChannelHandlerContext ctx) throws Exception {
                ctx.writeAndFlush(buf.duplicate())
                  .addListener(ChannelFutureListener.CLOSE);
              }
            });
          }
        });
      //绑定服务器以接收资源
      ChannelFuture f = b.bind().sync();
      f.channel().closeFuture().sync();
    } finally {
      //释放所有资源
      group.shutdownGracefully().sync();
    }
  }
}
