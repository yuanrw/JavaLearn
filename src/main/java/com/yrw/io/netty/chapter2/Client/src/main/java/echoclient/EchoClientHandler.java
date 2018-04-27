package echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author yrw
 * @since 2018/4/23
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

  //在到服务器的连接已经建立之后将被调用

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks! --From Client", CharsetUtil.UTF_8));
  }

  //当从服务器接收到一条消息时被调用
  //由服务器发送的消息可能会被分块接收。
  //如果服务器发送了5字节，不能保证被一次性接收。
  //channelRead0()方法可能被调用两次,第一次一个持有3字节的ByteBuf，第二次一个持有2字节的ByteBuf。

  @Override
  public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
    System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
  }

  //在处理过程中引发异常时被调用

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}

