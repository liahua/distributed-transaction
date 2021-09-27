package com.chenahua.distributedtransaction.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO:同步阻塞IO
 * NIO:同步非阻塞IO
 * AIO:异步非阻塞 api表现为回调
 *
 *
 *
 * 同步异步的概念:
 *
 * 阻塞非阻塞的概念
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *          服务器             客户端
 *监听端口   8080
 *进程ID    5479              5540
 *
 *
 * /proc/5479/fd  --->  server占据一个socket位置 用于监听  139202  fd为3
 * 建立连接时 /proc/5479/fd ---> server再次生成一个socket位置 139203   同时启动随机一个端口号和client进行连接
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *r
 *
 */
public class RemoteServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);

    }

    public void process(Object object) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("clazzName");

    }

}
