package echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author yrw
 * @since 2018/4/23
 */
public class EchoServer {
  private final int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws InterruptedException {
    if (args.length != 1) {
      System.err.println("Usage: " + EchoServer.class.getSimpleName() +
        " <port>"
      );
      return;
    }
    //设置端口值，如果不合法，抛出NumberFormatException
    int port = Integer.parseInt(args[0]);
    //调用服务器的start方法
    new EchoServer(port).start();
  }

  public void start() throws InterruptedException {
    final EchoServerHandler serverHandler = new EchoServerHandler();
    //用于事件处理，如接受新连接以及读/写数据
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      //一个服务器
      ServerBootstrap b = new ServerBootstrap();
      b.group(group)
        //指定channel
        .channel(NioServerSocketChannel.class)
        //指定ip地址和端口号，设置socket
        .localAddress(new InetSocketAddress(port))
        //添加一个EchoServerHandler到子Channel的ChannelPipeline
        //当一个新的连接被接受时，一个新的子Channel将会被创建，
        //而ChannelInitializer将会把一个你的EchoServerHandler的实例添加到该Channel的ChannelPipeline中。
        //这个ChannelHandler将会收到有关入站消息的通知。
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(serverHandler);
          }
        });

      //异步绑定服务器，调用sync()方法阻塞等待直到绑定完成
      ChannelFuture f = b.bind().sync();
      System.out.println(EchoServer.class.getName() +
        "started and listening for connenctions on" +
        f.channel().localAddress());
      //获取Channel的CloseFuture，并且阻塞当前线程它完成
      f.channel().closeFuture().sync();
    } finally {
      //关闭EventLoopGroup，释放所有的资源
      group.shutdownGracefully().sync();
    }
  }
}
