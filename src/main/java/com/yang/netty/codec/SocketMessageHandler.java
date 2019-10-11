package com.yang.netty.codec;

import com.yang.netty.enums.Constains;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * SocketMessage 消息体处理
 */
@Slf4j
public class SocketMessageHandler {

    /**
     * byteBuf 转化为 SocketMessage 消息体
     *
     * @param byteBuf
     * @return SocketMessage
     */
    public SocketMessage getSocketMessage(ByteBuf byteBuf) {
        try {
            if (byteBuf.readableBytes() < Constains.SOCKET_MESSAGE_HEADER_SIZE) {
                return null;
            }

            SocketMessage msg = new SocketMessage();

            byte[] start = new byte[4];
            byteBuf.readBytes(start, 0, 4);
            if (!Arrays.equals(start, Constains.SOCKET_MESSAGE_HEADER_START)) {
                log.info("header start 不一致：{}", new String(start));
                return null;
            }
            msg.setStart(start);

            msg.setId(byteBuf.readLong());
            msg.setMainType(byteBuf.readShort());
            msg.setSubType(byteBuf.readShort());

            int length = byteBuf.readInt();
            if (byteBuf.readableBytes() < length) {
                return null;
            }
            msg.setLength(length);

            byte[] req = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(req);
            msg.setBody(new String(req, "UTF-8"));

            return msg;

        } catch (Exception ex) {
            log.info(ex.getMessage(), ex);
            return null;
        }
    }
}
