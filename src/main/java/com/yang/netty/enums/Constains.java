package com.yang.netty.enums;

/**
 * 系统变量
 * @author yangyuyang
 * @date 2019-10-11
 */
public class Constains {

    // socket消息头头部开始，占用四个字节
    public final static byte[] SOCKET_MESSAGE_HEADER_START = "yang".getBytes();
    // socket header 长度  4 + 8 + 2 + 2 + 4
    public final static int SOCKET_MESSAGE_HEADER_SIZE = 20;

    // socket 空闲时间 5s
    public final static int SOCKET_IDLE_TIME = 5;
}
