package com.yang.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 启动器
 * @author yangyuyang
 * @date 2019-09-30
 */
@Component
@Slf4j
public class NettyClient {

    private String IP = "192.168.129.42";
    private Integer PORT = 8090;

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture future = null;
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());

        try {
            future = bootstrap.connect(IP, PORT).sync();
            log.info("客户端成功....");
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //group.shutdownGracefully();
            if(null != future) {
                if (future.channel() != null && future.channel().isOpen()) {
                    future.channel().close();
                }
            }

            // 重连
            System.out.println("reConnection");
            start();
            System.out.println("connection success");
        }
    }

    public static void main(String[] args){

        new NettyClient().start();
    }

}
