package com.horn.blockchain.backup.p2p;

import com.horn.blockchain.backup.p2p.p2ptio.AdminServerAioHandler;
import com.horn.blockchain.backup.p2p.p2ptio.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.tio.server.ServerGroupContext;
import org.tio.server.TioServer;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import javax.annotation.PostConstruct;

/**
 * 基于t-io的区块链底层P2P网络平台的服务端
 * */
@Component
public class AdminServerStarter {
   //日志记录
   private Logger logger = LoggerFactory.getLogger(AdminServerStarter.class);

   //handler，包括编码，解码，消息处理
   public static ServerAioHandler aioHandler = new AdminServerAioHandler();

   //事件监听器，可以为null,但建议自己实现该接口，可以参考showcase
   public static ServerAioListener aioListener = null;

   //一组共用的上下文对象
   public static ServerGroupContext serverGroupContext = new ServerGroupContext("tio-server", aioHandler, aioListener);

   //tioServer对象
   public static TioServer tioServer = new TioServer(serverGroupContext);

   //有时候需要绑定IP
   public static String serverIp = Const.SERVER;

   //监听的端口
   public static int serverPort = Const.PORT;

   @PostConstruct
   @Order(1)
   public void start(){
      try{
         logger.info("北京服务端即将开启");
         serverGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
         tioServer.start(serverIp, serverPort);
         logger.info("北京服务端启动完毕");
      }catch (Exception e){
         logger.error(e.getMessage());
      }
   }

}
