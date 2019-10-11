package com.yang.netty.server;

import com.yang.netty.codec.SocketMessage;
import com.yang.netty.codec.SocketMessageHandler;
import com.yang.netty.enums.Constains;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * netty服务端处理类
 *
 * @author yangyuyang
 * @date 2019-09-30
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        InetSocketAddress inetSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ipPort = inetSocket.getAddress().getHostAddress() + ":" + inetSocket.getPort();
        ChannelManager.getMap().put(ipPort, ctx.channel());
        log.info("Channel active......");
    }

    /**
     * 客户端下线触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        InetSocketAddress inetSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ipPort = inetSocket.getAddress().getHostAddress() + ":" + inetSocket.getPort();
        ChannelManager.remove(ipPort);

        ctx.fireChannelInactive();
    }

    /**
     * 客户端发消息触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (!(msg instanceof ByteBuf)) {
            return;
        }
        SocketMessage socketMessage = new SocketMessageHandler().getSocketMessage((ByteBuf) msg);
        if (socketMessage == null) {
            return;
        }
        log.info("body = {}, {}, {}", ChannelManager.size(), ctx.channel().id(), socketMessage.getBody());
    }

    /**
     * channelRead完成后触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
    }

    /**
     * 发生异常触发
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private int loss_connect_time = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                loss_connect_time++;
                System.out.println("60 秒没有接收到客户端的信息了" + ctx.channel().id());
                if (loss_connect_time > 30) {
                    System.out.println("关闭这个不活跃的channel");
                    //ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
