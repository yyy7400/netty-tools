package com.yang.netty.server;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * channel manager
 * @author yangyuyang
 * @date 2019-10-08
 */
public class ChannelManager {

    private ChannelManager() {
        throw new IllegalStateException(this.getClass().getName());
    }

    protected static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public static void put(String key, Channel value) {
        channelMap.put(key, value);
    }

    public static Map<String, Channel> getMap() {
        return channelMap;
    }

    public static List<Channel> get() {

        List<Channel> list = new ArrayList<>();
        channelMap.forEach((k, v) -> list.add(v));
        return list;
    }

    public static Channel get(String key) {
        return channelMap.get(key);
    }

    public static void remove(String key) {
        channelMap.remove(key);
    }

    public static int size() {
        return channelMap.size();
    }
}
