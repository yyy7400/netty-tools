package com.yang.netty.controller;

import com.yang.netty.server.ChannelManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangyuyang
 */
@RestController
public class TestController {

    @GetMapping("index")
    public String index() {
        ChannelManager.getMap().forEach((k, v) -> {

            v.writeAndFlush("1");
        });
        return "go";
    }
}
