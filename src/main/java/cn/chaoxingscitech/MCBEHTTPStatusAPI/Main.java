package cn.chaoxingscitech.MCBEHTTPStatusAPI;

import cn.chaoxingscitech.MCBEHTTPStatusAPI.handle.GetData;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/get", new GetData());
        server.start();
        System.out.println("服务启动成功,默认端口: "+port);
    }
}