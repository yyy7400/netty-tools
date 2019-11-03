package com.yang.netty.model;

/**
 * 测试实体
 * @author yangyuyang
 * @date 2019-11-03
 */
public class SocketMessagePost {
    private short mainType;
    private short subType;
    private Object body;

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

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
