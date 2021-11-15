package com.horn.blockchain.chaincode.p2p.p2ptio;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.horn.blockchain.chaincode.p2p.p2ppbft.VoteEnum;
import com.horn.blockchain.chaincode.p2p.p2ppbft.VoteInfo;
import com.horn.blockchain.chaincode.util.merkle.SimpleMerkleTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AdminServerAioHandler implements ServerAioHandler {
    private Logger logger = LoggerFactory.getLogger(AdminServerAioHandler.class);
    /**
     * 解码：把接受到的ByteBuffer解码成应用可以识别的业务消息包
     * 总的消息结构：消息头+消息体
     * 消息头结构：4个字节，存储消息体的长度
     * 消息体结构：对象的JSON串的byte[]
     * */
    @Override
    public Packet decode(ByteBuffer byteBuffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {
        //提醒：buffer的开始位置并不一定是0，应用需要从buffer.posision()开始读取数据
        //若收到的数据无法组成业务包BlockaPacket,则返回null以表示数据长度不够。
        if(readableLength < BlockPacket.HEADER_LENGTH) return null;
        //读取消息体的长度
        int bodyLength = byteBuffer.getInt();
        if (bodyLength < 0) {
            throw new AioDecodeException("bodyLenght[" + bodyLength+"] is not right, remote:" + channelContext.getClientNode());
        }

        //计算本次需要的数据长度
        int neededLength = BlockPacket.HEADER_LENGTH+bodyLength;
        //收到的数据是否足够组包
        int isDataEnough = readableLength - neededLength;
        //不够消息体长度（剩下的buffer组不了消息体）
        if(isDataEnough < 0) return null;
        else{
            BlockPacket imPacket = new BlockPacket();
            if(bodyLength > 0){
                byte[] dst = new byte[bodyLength];
                byteBuffer.get(dst);
                imPacket.setBody(dst);
            }
            return imPacket;
        }
    }

    /**
     * 编码：把业务消息包编码为可以发送的ByteBuffer
     * 总的消息结构：消息头+消息体
     * 消息头结构：4个字节，存储消息体长度
     * 消息体结构：对象的JSON串的byte[]
     * */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        BlockPacket realPacket = (BlockPacket) packet;
        byte[] body = realPacket.getBody();
        int bodyLen = 0;
        if(body != null) bodyLen = body.length;
        //bytebuffer的总长度是（消息头的长度+消息体的长度）
        int allLen = BlockPacket.HEADER_LENGTH + bodyLen;
        //创建一个新的bytebuffer
        ByteBuffer buffer = ByteBuffer.allocate(allLen);
        buffer.order(groupContext.getByteOrder());
        //写入消息头，消息头的内容就是消息体的长度
        buffer.putInt(bodyLen);
        if(body != null) buffer.put(body);
        return buffer;
    }

    /**
     * 处理消息
     * */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        BlockPacket realPacket = (BlockPacket) packet;
        byte[] body = realPacket.getBody();
        if(body != null){
            String str = new String(body, BlockPacket.CHARSET);
            logger.info("北京服务端接收到消息：" + str);

            //如果收到的不是JSON数据，说明不是PFBT阶段
            if(!str.startsWith("{")){
                BlockPacket resppacket = new BlockPacket();
                resppacket.setBody(("北京服务端收到了客户端的消息，客户端的消息是" + str).getBytes(BlockPacket.CHARSET));
                Tio.send(channelContext, resppacket);
                return;
            }

            //如果收到的是JSON数据，咋说明是PBFT阶段
            JSONObject json = JSON.parseObject(str);
            if(!json.containsKey("code")){
                logger.info("北京服务端收到JSON化数据");
            }

            int code = json.getIntValue("code");
            if(code == VoteEnum.PREPARE.getCode()){
                VoteInfo voteInfo = JSON.parseObject(str, VoteInfo.class);
                if(!voteInfo.getHash().equals(SimpleMerkleTree.getTreeNodeHash(voteInfo.getList()))){
                    logger.info("服务端收到客户端错误的JSON化数据");
                    return;
                }
                //校验成功，发送下一个状态的数据
                VoteInfo vi = createVoteInfo(VoteEnum.PREPARE);
                BlockPacket resppacket = new BlockPacket();
                resppacket.setBody(JSON.toJSONString(vi).getBytes(BlockPacket.CHARSET));
                Tio.send(channelContext, resppacket);
                logger.info("北京服务端发送到客户端的PBFT消息：" + JSON.toJSONString(vi));
                return;
            }
            if(code == VoteEnum.COMMIT.getCode()){
                //校验哈希
                VoteInfo voteInfo = JSON.parseObject(str, VoteInfo.class);
                if(!voteInfo.getHash().equals(SimpleMerkleTree.getTreeNodeHash(voteInfo.getList()))){
                    logger.info("北京服务端收到北京客户端错误信息");
                    return;
                }
                if(getConnectedNodeCount() >= getleastNodeCount()){
                    BlockPacket resppacket = new BlockPacket();
                    resppacket.setBody("北京服务端开始区块入库啦".getBytes(BlockPacket.CHARSET));
                    Tio.send(channelContext, resppacket);
                    logger.info("北京服务端区块开始入库啦！");
                }
            }
        }
        return;
    }

    //已经连接的节点个数
    private int getConnectedNodeCount(){
        //测试环境中，只有一个连接节点，可以根据后续需求扩展
        return 1;
    }

    //PBFT消息节点最少确认个数计算
    private int getleastNodeCount(){
        //本机测试只采用一个节点，实际开发部署多个节点时，PBFT算法中拜占庭节点数量f,总节点数3f+1
        return 1;
    }

    //根据VoteEnum构建对应状态的VoteInfo
    private VoteInfo createVoteInfo(VoteEnum ve){
        VoteInfo vi = new VoteInfo();
        vi.setCode(ve.getCode());
        List<String> list = new ArrayList<>();
        list.add("AI");
        list.add("BlockChain");
        vi.setList(list);
        vi.setHash(SimpleMerkleTree.getTreeNodeHash(list));
        return vi;
    }
}
