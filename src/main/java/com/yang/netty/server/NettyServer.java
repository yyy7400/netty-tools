package com.yang.netty.server;

import com.yang.netty.enums.PropertiesValue;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * netty 服务启动器
 * @author yangyuyang
 * @date 2019-09-30
 */

@Component
@Slf4j
public class NettyServer {

    /**
     * 一个主线程
     */
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    /**
     * 一个工作线程组, 线程数默认是cup核心数两倍
     */
    EventLoopGroup workGroup = new NioEventLoopGroup();

    public void start() {

        log.info("{}:{}", PropertiesValue.NETTY_SERVER_IP, PropertiesValue.NETTY_SERVER_PORT);
        InetSocketAddress socketAddress = new InetSocketAddress(PropertiesValue.NETTY_SERVER_IP, PropertiesValue.NETTY_SERVER_PORT);

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer())
                .localAddress(socketAddress)
                // 设置队列大小
                .option(ChannelOption.SO_BACKLOG, 128)
                //.option(ChannelOption.TCP_NODELAY, true)
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        // 绑定端口,开始接收进来的连接
        try {
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            // 服务器开始监听
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
