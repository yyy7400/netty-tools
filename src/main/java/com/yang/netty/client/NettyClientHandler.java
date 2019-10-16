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

import java.net.InetSocketAddress;

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

//        byte[] req = "test sample".getBytes();
        Version version = new Version("1", "第一版");
        String str = JSON.toJSONString(version);
//        ByteBuf message = null;
//        for (int i = 100; i < 9000; i++) {
//            message = Unpooled.buffer(req.length);
//            String tmp = str + i;
//            str = str + i;
//            //SocketMessage socketMessage = new SocketMessage((short) 1, (short) 2, tmp);
//            //ctx.writeAndFlush(socketMessage);
//        }
        // 恢复超时时间

        SocketMessage socketMessage = new SocketMessage(SocketMessageType.CLIENT.getKey(), SocketMessageType.CLIENT_GET_VERSION.getKey(), str);
        ctx.writeAndFlush(socketMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端收到消息: {}", msg.toString());

        if (!(msg instanceof ByteBuf)) {
            return;
        }
        SocketMessage socketMessage = new SocketMessageHandler().getSocketMessage((ByteBuf) msg);
        if (socketMessage == null) {
            return;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                ctx.channel().writeAndFlush(new SocketMessage(SocketMessageType.HEARTBEAT.getKey(), SocketMessageType.HEARTBEAT_SEVER.getKey(), ""));
            }
        }
    }

    private String getIpPort(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ipPort = inetSocket.getAddress().getHostAddress() + ":" + inetSocket.getPort();
        return ipPort;

    }

}
