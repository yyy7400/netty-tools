package com.yang.netty.codec;

import com.yang.netty.enums.Constants;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * SocketMessage 消息体处理
 */
@Slf4j
public class SocketMessageHandler {

    /**
     * (ByteBuf)msg 转化为 SocketMessage 消息体
     *
     * @param msg
     * @return SocketMessage
     */
    public SocketMessage getSocketMessage(Object msg) {
        try {
            if (!(msg instanceof ByteBuf)) {
                return null;
            }

            ByteBuf byteBuf = (ByteBuf)msg;
            if (byteBuf.readableBytes() < Constants.SOCKET_MESSAGE_HEADER_SIZE) {
                return null;
            }

            SocketMessage socketMessage = new SocketMessage();

            byte[] start = new byte[4];
            byteBuf.readBytes(start, 0, 4);
            if (!Arrays.equals(start, Constants.SOCKET_MESSAGE_HEADER_START)) {
                log.info("header start 不一致：{}", new String(start));
                return null;
            }
            socketMessage.setStart(start);

            socketMessage.setId(byteBuf.readLong());
            socketMessage.setMainType(byteBuf.readShort());
            socketMessage.setSubType(byteBuf.readShort());

            int length = byteBuf.readInt();
            if (byteBuf.readableBytes() < length) {
                return null;
            }
            socketMessage.setLength(length);

            byte[] req = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(req);
            socketMessage.setBody(new String(req, StandardCharsets.UTF_8));

            return socketMessage;

        } catch (Exception ex) {
            log.info(ex.getMessage(), ex);
            return null;
        }
    }
}
