package com.yang.netty.server;

import com.alibaba.fastjson.JSON;
import com.yang.netty.codec.SocketMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息-业务处理工厂类
 *
 * @author yangyuyang
 * date 2019-11-03
 */

@Slf4j
public abstract class MessageHandleFactory {

    private String ipPort;
    public SocketMessage socketMessage;

    public MessageHandleFactory() {
    }

    public MessageHandleFactory(String ipPort, SocketMessage socketMessage) {
        this.ipPort = ipPort;
        this.socketMessage = socketMessage;
    }


    /**
     * 接收消息处理
     */
    public abstract void recvHandle();

    /**
     * 接收消息后回应某ip, body 为新内容
     */
    public void sendHandle(SocketMessage socketMessage, Object body) {

        SocketMessage socketMessageNew = new SocketMessage();
        socketMessageNew.setStart(socketMessage.getStart());
        socketMessageNew.setId(socketMessage.getId());
        socketMessageNew.setMainType(socketMessage.getMainType());
        socketMessageNew.setSubType(socketMessage.getSubType());
        socketMessageNew.setBody(JSON.toJSONString(body));

        Channel channel = ChannelManager.get(ipPort);
        if (channel == null) {
            return;
        }
        log.info("server Send: {}: {}", ipPort, socketMessageNew.toString());
        channel.writeAndFlush(socketMessageNew);
    }


    /**
     * 接收消息后回应某ip, body 为新内容
     */
    public void sendAllHandle(SocketMessage socketMessage, Object body) {

        SocketMessage socketMessageNew = new SocketMessage();
        socketMessageNew.setStart(socketMessage.getStart());
        socketMessageNew.setId(socketMessage.getId());
        socketMessageNew.setMainType(socketMessage.getMainType());
        socketMessageNew.setSubType(socketMessage.getSubType());
        socketMessageNew.setBody(JSON.toJSONString(body));

        sendAllHandle(socketMessageNew);
    }

    public void sendAllHandle(SocketMessage socketMessage) {
        if (ChannelManager.size() == 0) {
            log.info("channel.size(): {}", 0);
            return;
        }
        ChannelManager.get().forEach(o -> o.writeAndFlush(socketMessage));
        log.info("server SendAll : {}", socketMessage.toString());
    }
}
