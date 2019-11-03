package com.yang.netty.server;

import org.springframework.stereotype.Component;

/**
 * 服务端消息处理
 * @author yangyuyang
 * @date 2019-11-03
 */
@Component
public class HandleServer extends MessageHandleFactory {

    @Override
    public void recvHandle() {
        // Do nothing.
    }
}
