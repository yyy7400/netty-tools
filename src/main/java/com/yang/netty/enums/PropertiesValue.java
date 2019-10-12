package com.yang.netty.enums;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesValue {

    public static String NETTY_SERVER_IP;
    public static int NETTY_SERVER_PORT;

    public String getNettyServerIp() {
        return NETTY_SERVER_IP;
    }

    @Value("${netty.server.ip}")
    public void setNettyServerIp(String nettyServerIp) {
        PropertiesValue.NETTY_SERVER_IP = nettyServerIp;
    }

    public int getNettyServerPort() {
        return NETTY_SERVER_PORT;
    }

    @Value("${netty.server.port}")
    public void setNettyServerPort(int nettyServerPort) {
        PropertiesValue.NETTY_SERVER_PORT = nettyServerPort;
    }
}
