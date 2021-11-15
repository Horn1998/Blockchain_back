package com.horn.blockchain.chaincode.p2p.p2ptio;

import org.tio.core.intf.Packet;

public class BlockPacket extends Packet {
    //网络传输需要序列化，这里采用JAVA自带序列化方式
    private static final long serialVersionUID = -172060606924066412L;
    //消息头的长度
    public static final int HEADER_LENGTH = 4;
    //字符编码类型
    public static final String CHARSET = "utf-8";
    //传输内容的字节
    private byte[] body;


    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
