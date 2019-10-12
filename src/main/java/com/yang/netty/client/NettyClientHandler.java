package com.yang.netty.client;

import com.yang.netty.codec.SocketMessage;
import com.yang.netty.codec.SocketMessageHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

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
        String str = "端";
//        ByteBuf message = null;
//        for (int i = 100; i < 9000; i++) {
//            message = Unpooled.buffer(req.length);
//            String tmp = str + i;
//            str = str + i;
//            //SocketMessage socketMessage = new SocketMessage((short) 1, (short) 2, tmp);
//            //ctx.writeAndFlush(socketMessage);
//        }
        // 恢复超时时间

        SocketMessage socketMessage = new SocketMessage((short) 1, (short) 2, str);
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
                ctx.channel().writeAndFlush(new SocketMessage((short) 3, (short) 4, "ping"));
            }
        }
    }

    private String getIpPort(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ipPort = inetSocket.getAddress().getHostAddress() + ":" + inetSocket.getPort();
        return ipPort;

    }

}
