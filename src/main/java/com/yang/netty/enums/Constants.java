package com.yang.netty.enums;

/**
 * 系统变量
 *
 * @author yangyuyang
 * @date 2019-10-11
 */
public class Constants {

    // socket消息头头部开始，占用四个字节
    public static final byte[] SOCKET_MESSAGE_HEADER_START = "BBAN".getBytes();
    // socket header 长度  4 + 8 + 2 + 2 + 4
    public static final int SOCKET_MESSAGE_HEADER_SIZE = 20;

    // socket 空闲时间 5s
    public static final int SOCKET_IDLE_TIME = 30;

    private Constants() {
        throw new IllegalStateException(this.getClass().getName());
    }
}
