package com.yang.netty.server;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

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

    private final static Map<String, Channel> channelCache = new ConcurrentHashMap<>();

    public void put(String key, Channel value) {
        channelCache.put(key, value);
    }

    public static Map<String, Channel> getMap() {
        return channelCache;
    }

    public static List<Channel> get() {

        List<Channel> list = new ArrayList<>();
        channelCache.forEach((k, v) -> {
            list.add(v);
        });
        return list;
    }

    public static Channel get(String key) {
        return channelCache.get(key);
    }

    public static void remove(String key) {
        channelCache.remove(key);
    }

    public static int size() {
        return channelCache.size();
    }
}
