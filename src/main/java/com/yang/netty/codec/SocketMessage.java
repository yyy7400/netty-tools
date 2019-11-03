package com.yang.netty.codec;

import com.alibaba.fastjson.JSON;
import com.yang.netty.enums.Constants;

import java.util.Arrays;

/**
 * Scoket消息协议，
 * @author yangyuyang
 * @date 2019-10-11
 */
public class SocketMessage {

    // 4 byte 开头是ABED
    private byte[] start = Constants.SOCKET_MESSAGE_HEADER_START;
    // 8 byte 已时间戳作为唯一标识
    private long id = System.currentTimeMillis();
    // 2 Byte 主消息类型
    private short mainType;
    // 2 Byte 子消息类型
    private short subType;

    //主题信息的长度
    private int length;
    //主题信息
    private String body;

    public SocketMessage() {
    }

    public SocketMessage(short mainType, short subType, String body) {
        this.mainType = mainType;
        this.subType = subType;
        this.body = body;
    }

    public SocketMessage(short mainType, short subType, Object body) {
        this.mainType = mainType;
        this.subType = subType;
        this.body = JSON.toJSONString(body);
    }

    public byte[] getStart() {
        return start;
    }

    public void setStart(byte[] start) {
        this.start = start;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getMainType() {
        return mainType;
    }

    public void setMainType(short mainType) {
        this.mainType = mainType;
    }

    public short getSubType() {
        return subType;
    }

    public void setSubType(short subType) {
        this.subType = subType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "start=" + Arrays.toString(start) +
                ", id=" + id +
                ", mainType=" + mainType +
                ", subType=" + subType +
                ", length=" + length +
                ", body='" + body + '\'' +
                '}';
    }
}
