package com.yang.netty.server;

import com.yang.netty.codec.SocketDecoder;
import com.yang.netty.codec.SocketEncoder;
import com.yang.netty.enums.Constants;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * netty服务端初始化
 * @author yangyuyang
 * @date 2019-09-30
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 读取客户端数据 60s 超时, 查看在线状态
        socketChannel.pipeline().addLast("ping", new IdleStateHandler((long) Constants.SOCKET_IDLE_TIME * 2, 0, 0, TimeUnit.SECONDS));

        //添加自定义编码解码，解决粘包、拆包问题
        socketChannel.pipeline().addLast("decoder", new SocketDecoder());
        socketChannel.pipeline().addLast("encoder",new SocketEncoder());
        socketChannel.pipeline().addLast(new NettyServerHandler());

    }
}
