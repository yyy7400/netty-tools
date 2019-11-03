package com.yang.netty.client;

import com.alibaba.fastjson.JSON;
import com.yang.netty.codec.SocketMessage;
import com.yang.netty.codec.SocketMessageHandler;
import com.yang.netty.enums.SocketMessageType;
import com.yang.netty.model.Version;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端处理器
 *
 * @author yangyuyang
 * @date 2019-09-30
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Version version = new Version("1", "第一版");
        String str = JSON.toJSONString(version);

        SocketMessage socketMessage = new SocketMessage(SocketMessageType.CLIENT, SocketMessageType.CLIENT_GET_VERSION, str);
        ctx.writeAndFlush(socketMessage);
        log.info("client send : {}", socketMessage.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        SocketMessage socketMessage = new SocketMessageHandler().getSocketMessage((ByteBuf) msg);
        if (socketMessage == null) {
            return;
        }
        log.info("client recv: {}", socketMessage.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                ctx.channel().writeAndFlush(new SocketMessage(SocketMessageType.HEARTBEAT, SocketMessageType.HEARTBEAT_CLIENT, ""));
            }
        }
    }


}
