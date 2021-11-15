package com.horn.blockchain.chaincode.p2p.p2ptio;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.horn.blockchain.chaincode.p2p.p2ppbft.VoteEnum;
import com.horn.blockchain.chaincode.p2p.p2ppbft.VoteInfo;
import com.horn.blockchain.chaincode.util.merkle.SimpleMerkleTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于t-io的区块链底层P2P网络平台客户端Handler*/
public class BlockChainClientAioHandler implements ClientAioHandler {
    private Logger logger = LoggerFactory.getLogger(AdminServerAioHandler.class);
    private static BlockPacket hearbeatPacket = new BlockPacket();

    /**
     * 解码：把接受到的ByteBuffer解码成应用可以识别的业务消息包，总的消息结构为消息头+消息体，其中消息体为
     * Json串的byte[]
     * */
    @Override
    public Packet decode(ByteBuffer byteBuffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {
        if(readableLength < BlockPacket.HEADER_LENGTH) return null;
        int bodyLength = byteBuffer.getInt();
        if(bodyLength < 0){
            throw new AioDecodeException(
                    "bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode()
            );
        }
        int neededLength = BlockPacket.HEADER_LENGTH + bodyLength;
        int isDataEnough = readableLength - neededLength;
        if(isDataEnough < 0) return null;
        else{
            BlockPacket imPackage = new BlockPacket();
            if(bodyLength > 0){
                byte[] dst = new byte[bodyLength];
                byteBuffer.get(dst);
                imPackage.setBody(dst);
            }
            return imPackage;
        }
    }

    @Override
    /**
     * 编码
     * */
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        BlockPacket realPacket = (BlockPacket) packet;
        byte[] body = realPacket.getBody();
        int bodyLen = 0;
        if(body != null) bodyLen = body.length;
        int allLen = BlockPacket.HEADER_LENGTH + bodyLen;
        ByteBuffer buffer = ByteBuffer.allocate(allLen);
        buffer.order(groupContext.getByteOrder());
        buffer.putInt(bodyLen);
        if(body != null) buffer.put(body);
        return buffer;
    }

    @Override
    //处理消息
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        BlockPacket realPacket = (BlockPacket) packet;
        byte[] body = realPacket.getBody();
        if(body != null){
            String str = new String(body, BlockPacket.CHARSET);
            logger.info("北京客户端收到消息"+str);

            //收到入库的消息则不再发送
            if("北京服务端开始区块入库啦".equals(str)) return;

            //发送PBFT投票消息
            //如果收到的不是JSON化数据，说明仍在双方建立连接的过程中。目前连接已经建立完毕，发起投票。
            if(!str.startsWith("{")){
                VoteInfo vi = createVoteInfo(VoteEnum.PREPREPARE);
                BlockPacket bp = new BlockPacket();
                bp.setBody(JSON.toJSONString(vi).getBytes(BlockPacket.CHARSET));
                Tio.send(channelContext, bp);
                logger.info("北京客户端发送到pbft消息：" + JSON.toJSONString(vi));
                return;
            }

            //如果是JSON化数据，则表明进入了PBFT投票阶段
            JSONObject json = JSON.parseObject(str);
            if(!json.containsKey("code")){
                logger.info("北京客户端收到非JSON化数据");
            }

            int code = json.getIntValue("code");
            if(code == VoteEnum.PREPARE.getCode()){
                //校验哈希
                VoteInfo voteInfo = JSON.parseObject(str, VoteInfo.class);
                if(!voteInfo.getHash().equals(SimpleMerkleTree.getTreeNodeHash(voteInfo.getList()))){
                    logger.info("北京客户端收到错误的JSON化信息");
                    return;
                }
                //校验成功，发送下一个状态的数据
                VoteInfo vi = createVoteInfo(VoteEnum.COMMIT);
                BlockPacket bp = new BlockPacket();
                bp.setBody(JSON.toJSONString(vi).getBytes(BlockPacket.CHARSET));
                Tio.send(channelContext, bp);
                logger.info("北京客户端发送到服务端的pbft消息:" + JSON.toJSONString(vi));
            }
        }
        return;
    }

    @Override
    public Packet heartbeatPacket() {
        return hearbeatPacket;
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
