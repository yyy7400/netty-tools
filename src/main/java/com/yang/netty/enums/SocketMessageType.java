package com.yang.netty.enums;

/**
 * socket消息类型, 默认消息都需要回应，不要回应在后面添加 NO_REPLY
 * @author yangyuyang
 * @date 2019-11-03
 */
public class SocketMessageType {

    //心跳
    public static final short HEARTBEAT = 21;
    //客户端心跳
    public static final short HEARTBEAT_CLIENT = 2001;
    //客户端心跳, 不需要回应
    public static final short HEARTBEAT_CLIENT_NO_REPLY = 2002;

    //由服务端发起
    public static final short SERVER = 22;
    //更新版本
    public static final short SERVER_UPDATE_VERSION = 3002;

    //由客户端发起
    public static final short CLIENT = 23;
    //获取版本
    public static final short CLIENT_GET_VERSION = 3001;
    //更新状态
    public static final short CLIENT_UPDATE_STATUS = 3002;

    private SocketMessageType() {
        throw new IllegalStateException(this.getClass().getName());
    }
}
