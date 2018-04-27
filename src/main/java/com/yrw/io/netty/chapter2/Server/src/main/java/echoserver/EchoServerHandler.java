package echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 针对不同类型的事件来调用 ChannelHandler;
 * 应用程序通过实现或者扩展 ChannelHandler 来挂钩到事件的生命周期，并且提供自
 * 定义的应用程序逻辑;
 * 在架构上，ChannelHandler 有助于保持业务逻辑与网络处理代码的分离。这简化了开
 * 发过程，因为代码必须不断地演化以响应不断变化的需求。
 *
 * @author yrw
 * @since 2018/4/23
 */
public class EchoServerHandler extends SimpleChannelInboundHandler {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf in = (ByteBuf) msg;
    System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
    //将接收到的消息写给发送者
    ctx.write(in);
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

}
