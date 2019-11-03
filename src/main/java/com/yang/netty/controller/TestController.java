package com.yang.netty.controller;

import com.yang.netty.codec.SocketMessage;
import com.yang.netty.model.SocketMessagePost;
import com.yang.netty.server.HandleServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangyuyang
 * @date 2019-11-03
 */
@RestController
public class TestController {

    @Autowired
    HandleServer handleServer;

    /**
     * server 向 client发送消息
     *
     * @param obj
     * @return
     */
    @PostMapping("/test/server")
    public String index(@RequestBody SocketMessagePost obj) {

        SocketMessage socketMessage = new SocketMessage(obj.getMainType(), obj.getSubType(), obj.getBody());
        handleServer.sendAllHandle(socketMessage);

        return socketMessage.toString();
    }
}
