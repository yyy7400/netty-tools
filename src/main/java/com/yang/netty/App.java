package com.yang.netty;

import com.yang.netty.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yangyuyang
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
        new NettyServer().start();

    }

}
