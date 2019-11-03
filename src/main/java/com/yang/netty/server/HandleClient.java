package com.yang.netty.server;

import com.yang.netty.codec.SocketMessage;
import com.yang.netty.enums.SocketMessageType;

/**
 * 客户端消息处理
 * @author yangyuyang
 * @date 2019-11-03
 */
public class HandleClient extends MessageHandleFactory {

    public HandleClient(String ipPort, SocketMessage socketMessage) {
        super(ipPort, socketMessage);
    }

    @Override
    public void recvHandle() {
        switch (socketMessage.getSubType()){
            case SocketMessageType.CLIENT_GET_VERSION:
                sendHandle(socketMessage, "CLIENT_GET_VERSION");
                break;
            case SocketMessageType.CLIENT_UPDATE_STATUS:
                sendHandle(socketMessage, "CLIENT_UPDATE_STATUS");
                break;
            default:
                break;
        }
    }
}
