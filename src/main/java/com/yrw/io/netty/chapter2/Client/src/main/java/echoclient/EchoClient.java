package echoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Echo 客户端将会:
 * 1. 连接到服务器
 * 2. 发送一个或者多个消息
 * 3. 对于每个消息，等待并接收从服务器发回的相同的消息
 * 4. 关闭连接
 *
 * @author yrw
 * @since 2018/4/23
 */
public class EchoClient {
  private final String host;
  private final int port;

  public EchoClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 2) {
      System.err.println("Usage: " + EchoClient.class.getSimpleName() +
        " <host> <port>"
      );
      return;
    }

    final String host = args[0];
    final int port = Integer.parseInt(args[1]);
    new EchoClient(host, port).start();
  }

  public void start() throws InterruptedException {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
        .channel(NioSocketChannel.class)
        .remoteAddress(new InetSocketAddress(host, port))
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new EchoClientHandler());
          }
        });
      ChannelFuture f = b.connect().sync();
      f.channel().closeFuture().sync();
    } finally {
      group.shutdownGracefully().sync();
    }
  }
}
