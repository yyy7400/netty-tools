package com.yang.netty.server;

import com.yang.netty.codec.SocketMessage;
import com.yang.netty.enums.SocketMessageType;

/**
 * 心跳处理
 * @author yangyuyang
 * @date 2019-11-03
 */
public class HandleHeartbeat extends MessageHandleFactory {

    public HandleHeartbeat(String ipPort, SocketMessage socketMessage) {
        super(ipPort, socketMessage);
    }

    @Override
    public void recvHandle() {
        switch (socketMessage.getSubType()){
            case SocketMessageType.HEARTBEAT_CLIENT:
                sendHandle(socketMessage, "");
                break;
            case SocketMessageType.HEARTBEAT_CLIENT_NO_REPLY:
            default:
                break;
        }
    }
}
