package com.horn.blockchain.chaincode.p2p;

import com.horn.blockchain.chaincode.p2p.p2ptio.BlockPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

/**
 * 基于t-io的区块链底层P2P网络平台的客户端*/
@Component
public class BlockChainClientStarter {
    private Logger logger = LoggerFactory.getLogger(BlockChainClientStarter.class);

    //服务端节点
    private Node serverNode;

    //handler，包括编码，解码，消息处理
    private ClientAioHandler tioClientHandler;

    //事件监听器，可以为null
    private ClientAioListener aioListener = null;

    //断链后自动连接
    private ReconnConf reconnConf = new ReconnConf(5000L);

    private ClientGroupContext clientGroupContext;

    private TioClient tioClient = null;
    private ClientChannelContext clientChannelContext = null;


//    /**
//     * 启动程序入口
//     * */
//    @PostConstruct
//    @Order(2)
//    public void start(){
//        try{
//            logger.info("北京客户端即将启动");
//            //初始化
//            serverNode = new Node(Const.SERVER, Const.PORT);
//            tioClientHandler = new BlockChainClientAioHandler();
//            clientGroupContext = new ClientGroupContext(tioClientHandler, aioListener, reconnConf);
//            clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
//            tioClient = new TioClient(clientGroupContext);
//            clientChannelContext = tioClient.connect(serverNode);
//            //发起测试
//            sendMessage();
//            logger.info("北京客户端启动完毕");
//        }catch(Exception e){
//            logger.error("服务端启动失败，等待中");
//        }
//    }

    private void sendMessage() throws Exception{
        BlockPacket packet = new BlockPacket();
        packet.setBody("tal say hello world to blockchian!".getBytes(BlockPacket.CHARSET));
        Tio.send(clientChannelContext, packet);
    }
}
