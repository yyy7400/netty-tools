package com.yang.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 启动器
 *
 * @author yangyuyang
 * @date 2019-09-30
 */
@Component
@Slf4j
public class NettyClient {

    public static final String IP = "192.168.129.42";

    public static final int PORT = 8090;

    // 重连时间
    private static int timeout = 0;

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
            timeout = 0;
            log.info("连接成功");
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            if (null != future && future.channel() != null && future.channel().isOpen()) {
                future.channel().close();
            }
            reStart();
        }
    }

    private void reStart() {
        //
        if (timeout <= 60) {
            timeout += 5;
        }
        log.info("重连间隔：{}", timeout);
        new NettyClient().start();
    }

    public static void main(String[] args) {

        new NettyClient().start();
    }

}
