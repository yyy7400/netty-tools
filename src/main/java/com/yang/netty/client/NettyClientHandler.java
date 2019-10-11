package com.yang.netty.client;

import com.yang.netty.codec.SocketMessage;
import com.yang.netty.codec.SocketMessageHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;

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
        log.info("客户端Active .....");
        byte[] req = "test sample".getBytes();
        String str = "端";
        ByteBuf message = null;
        for (int i = 100; i < 9000; i++) {
            message = Unpooled.buffer(req.length);
            String tmp = str + i;
            str = str + i;
            //SocketMessage socketMessage = new SocketMessage((short) 1, (short) 2, tmp);
            //ctx.writeAndFlush(socketMessage);
        }

        //SocketMessage socketMessage = new SocketMessage((short) 1, (short) 2, str);
        //ctx.writeAndFlush(socketMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端离线....");
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

    private static final int TRY_TIMES = 3;
    private int currentTime = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("循环触发时间：" + new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("currentTime:" + currentTime);
                ctx.channel().writeAndFlush(new SocketMessage((short) 3, (short) 4, "ping"+ currentTime));
                currentTime++;
//                if (currentTime <= TRY_TIMES) {
//                    System.out.println("currentTime:" + currentTime);
//                    currentTime++;
//                    ctx.channel().writeAndFlush(new SocketMessage((short) 3, (short) 4, "ping"));
//                }
            }
        }
    }

}
