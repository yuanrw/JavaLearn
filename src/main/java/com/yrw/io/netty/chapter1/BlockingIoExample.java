package com.yrw.io.netty.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yrw
 * @since 2018/4/23
 */
public class BlockingIoExample {
  private final int portNumber = 8888;

  public void SocketIoMethod() throws IOException {
    //创建一个新的 ServerSocket，用以监听指定端口上的连接请求
    ServerSocket serverSocket = new ServerSocket(portNumber);
    //对accept()方法的调用将被阻塞，直到一个连接建立
    Socket clientSocket = serverSocket.accept();
    //这些流对象都派生于该套接字的流对象
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

    String request, response;
    while ((request = in.readLine()) != null) {
      //请求被传递给服务器
      System.out.println(request);
      response = processRequest(request);
      //写入流，响应被发送给客户端
      out.println(response);
    }
  }

  private String processRequest(String request) {
    return "Processed";
  }
}
