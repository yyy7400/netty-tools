package com.yang.netty.server;

import com.yang.netty.codec.SocketMessage;
import com.yang.netty.codec.SocketMessageHandler;
import com.yang.netty.enums.Constants;
import com.yang.netty.enums.SocketMessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * netty服务端处理类
 *
 * @author yangyuyang
 * @date 2019-09-30
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 监听客户端不在线累计次数
    private int lossConnectCount = 0;
    // 监听客户端不在线累计最大次数，超过在关闭连接
    private int lossConnectCountMax = 6;

    /**
     * 客户端连接触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 客户端上线，ip、port、channel
        String ipPort = getIpPort(ctx);
        ChannelManager.getMap().put(ipPort, ctx.channel());

        log.info("{} 上线了", ipPort);
    }

    /**
     * 客户端下线触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        // 客户端下线、移除记录
        String ipPort = getIpPort(ctx);
        ChannelManager.remove(ipPort);

        ctx.fireChannelInactive();

        log.info("{} 下线了", ipPort);
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

        // 转换为协议消息结构
        SocketMessage socketMessage = new SocketMessageHandler().getSocketMessage(msg);
        if (socketMessage == null) {
            return;
        }

        // 管理客户端
        String ipPort = getIpPort(ctx);
        ChannelManager.put(ipPort, ctx.channel());

        MessageHandleFactory handle = null;
        switch ((socketMessage.getMainType())) {
            case SocketMessageType.HEARTBEAT:
                handle = new HandleHeartbeat(ipPort, socketMessage);
                break;
            case SocketMessageType.CLIENT:
                handle = new HandleClient(ipPort, socketMessage);
                break;
            default:
                break;
        }
        if (handle != null) {
            handle.recvHandle();
        }

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
        log.error(cause.getMessage(), cause);
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                String ipPort = getIpPort(ctx);
                // 累计监听次数
                lossConnectCount++;
                log.info("{} 秒没有接收到客户端的信息了{}", Constants.SOCKET_IDLE_TIME, ipPort);
                // 超过监听次数
                if (lossConnectCount >= lossConnectCountMax) {
                    log.info("关闭这个不活跃的channel{}", ipPort);
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private String getIpPort(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        return inetSocket.getAddress().getHostAddress() + ":" + inetSocket.getPort();

    }

}
