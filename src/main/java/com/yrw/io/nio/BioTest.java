package com.yrw.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统BIO模型（同步阻塞I/O处理）
 * 经典的每连接每线程的模型，使用多线程，
 * 原因是socket.accept()、socket.read()、socket.write()三个主要函数都是同步阻塞的
 * 1.利用多核。
 * 2.当I/O阻塞系统，但CPU空闲的时候，可以利用多线程使用CPU资源。
 *
 * @author yrw
 * @since 2018/4/4
 */
public class BioTest {

  //服务端
  public static void main(String[] args) throws IOException {
    ExecutorService executor = Executors.newFixedThreadPool(100);//线程池
    ServerSocket serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress(8088));
    while (!Thread.currentThread().isInterrupted()) {//主线程死循环等待新连接到来
      Socket socket = serverSocket.accept();
      executor.submit(new ConnectIOnHandler(socket));//为新的连接创建新的线程
    }
  }
}

class ConnectIOnHandler extends Thread {
  private Socket socket;

  public ConnectIOnHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
      //死循环处理读写事件
//      String someThing = socket.read();//读取数据
//      try {
//        someThing = socket.getInputStream().toString();
//        if (someThing != null) {
//          //处理数据
//          System.out.println(someThing);
//          //写数据
//          socket.write();
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
    }
  }
}
