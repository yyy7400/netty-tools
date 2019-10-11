package com.yang.netty.client;

import com.yang.netty.codec.SocketDecoder;
import com.yang.netty.codec.SocketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * netty初始化
 *
 * @author yangyuyang
 * @date 2019-09-30
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 读取服务端数据 60s 超时, 发送ping
        socketChannel.pipeline().addLast("ping", new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));

        //添加自定义编码解码，解决粘包、拆包问题
        socketChannel.pipeline().addLast("decoder", new SocketDecoder());
        socketChannel.pipeline().addLast("encoder",new SocketEncoder());
        socketChannel.pipeline().addLast(new NettyClientHandler());
    }
}
