package com.yang.netty.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * lengthFieldBasedFrame自定义解码器
 * @author yangyuyang
 * @Date 2019-10-11
 */
public class SocketDecoder extends LengthFieldBasedFrameDecoder {

    private static final int MAX_FRAME_LENGTH = 1024 * 1024;

    // length字段的起始位置 4+8+2+2
    private static final int LENGTH_FIELD_OFFSET = 16;

    // length这个属性的大小
    private static final int LENGTH_FIELD_LENGTH = 4;

    // length的值
    private static final int LENGTH_ADJUSTMENT = 0;

    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public SocketDecoder() {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, false);
    }

    /**
     * @param maxFrameLength      解码时，处理每个帧数据的最大长度
     * @param lengthFieldOffset   该帧数据中，存放该帧数据的长度的数据的起始位置
     * @param lengthFieldLength   记录该帧数据长度的字段本身的长度
     * @param lengthAdjustment    修改帧数据长度字段中定义的值，可以为负数
     * @param initialBytesToStrip 解析的时候需要跳过的字节数
     * @param failFast            为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
     */
    public SocketDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                         int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

}
