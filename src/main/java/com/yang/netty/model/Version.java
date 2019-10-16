package com.yang.netty.model;

/**
 * 消息实体，对应枚举，SERVER_GET_VERSION
 *
 * @author yangyuyang
 * @date 2019-10-14
 */
public class Version {
    private String id;
    private String Name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Version(String id, String name) {
        this.id = id;
        Name = name;
    }

    @Override
    public String toString() {
        return "Version{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
