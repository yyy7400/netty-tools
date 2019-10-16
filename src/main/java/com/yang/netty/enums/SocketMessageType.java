package com.yang.netty.enums;

/**
 * socket消息类型
 */
public enum SocketMessageType {

    HEARTBEAT(21, "心跳"),
    HEARTBEAT_CLIENT(1001, "客户端心跳"),
    HEARTBEAT_SEVER(1002, "服务端心跳"),


    SERVER( 22, "由服务端发起, 客户端不需要回应"),
    SERVER_GET_VERSION( 2001, "服务端消息"),


    SERVER_REPLY( 23, "由服务端发起, 客户端需要回应"),
    SERVER_REPLY_GET_VERSION( 3001, " 获取版本"),


    CLIENT( 24, "由客户端发起，服务端不需要回应"),
    CLIENT_UPDATE_STATUS( 4001, "更新状态"),


    CLIENT_REPLY( 25, "由客户端发起，服务端需要回应"),
    CLIENT_GET_VERSION(5001, "获取版本");



    private SocketMessageType(int _key, String _name) {
        key = _key;
        name = _name;
    }

    int key;
    String name;

    public short getKey() {
        return (short) key;
    }

    public String getName() {
        return name;
    }

    public static String getName(int key) {
        for (SocketMessageType e : SocketMessageType.values()) {
            if (e.getKey() == key) {
                return e.getName();
            }
        }

        return "";
    }
    }
