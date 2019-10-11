package com.yang.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * lengthFieldBasedFrame自定义编码器
 * @author yangyuyang
 * @Date 2019-10-11
 */
public class SocketEncoder extends MessageToByteEncoder<SocketMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SocketMessage socketMessage, ByteBuf byteBuf) throws Exception {

        if(null == socketMessage){
            throw new Exception();
        }

        String body = socketMessage.getBody();
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        byteBuf.writeBytes(socketMessage.getStart());
        byteBuf.writeLong(socketMessage.getId());
        byteBuf.writeShort(socketMessage.getMainType());
        byteBuf.writeShort(socketMessage.getSubType());
        byteBuf.writeInt(bodyBytes.length);
        byteBuf.writeBytes(bodyBytes);
    }
}
