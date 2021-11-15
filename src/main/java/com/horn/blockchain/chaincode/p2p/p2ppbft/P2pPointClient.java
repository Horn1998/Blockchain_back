package com.horn.blockchain.chaincode.p2p.p2ppbft;

/**
 * 基于SpringBoot2.0的Web客户端*/
public class P2pPointClient {
//    //日志记录
//    private Logger logger = LoggerFactory.getLogger(P2pPointClient.class);
//
//    //P2P网络中的节点既是服务端，又是客户端，作为服务端运行在7001端口
//    //客户可以通过ws://localhost:7001连接点服务端
//    private String wsUrl = "http://localhost:7001/"; //这里可能需要改动
//    //所有客户端WebSocket的连接池缓存
//    private List<WebSocket> localSockets = new ArrayList<WebSocket>();
//
//    public List<WebSocket> getLocalSockets(){
//        return localSockets;
//    }
//
//    public void setLocalSockets(List<WebSocket> localSockets){
//        this.localSockets = localSockets;
//    }
//
//    /**
//     * 连接到服务端
//     * */
//    @PostConstruct
//    @Order(2)
//    public void connectPeer(){
//        try{
//            //创建WebSocket的客户端
//            final WebSocketClient socketClient = new WebSocketClient(new URI(wsUrl)) {
//                @Override
//                public void onOpen(ServerHandshake serverHandshake) {
//                   sendMessage(this, "客户端成功创建");
//                   localSockets.add(this);
//                }
//
//                @Override
//                public void onMessage(String s) {
//                    logger.info("客户端收到服务端发来的消息：" + s);
//                }
//
//                @Override
//                public void onClose(int i, String s, boolean b) {
//                    logger.info("客户端关闭");
//                    localSockets.remove(this);
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    logger.info("北京客户端报错");
//                    localSockets.remove(this);
//                }
//            };
//        } catch (URISyntaxException e) {
//            logger.info("连接错误：" + e.getMessage());
//        }
//    }
//
//    /**
//     * 向服务端发送消息，当前WebSocket的远程Socket地址就是服务端
//     * @param ws
//     * @param message
//     * */
//    public void sendMessage(WebSocket ws, String message){
//        logger.info("发送给" + ws.getRemoteSocketAddress().getPort() + "的p2p消息：" + message);
//        ws.send(message);
//    }
//    /**
//     * 向所有连接过的服务端广播消息
//     * @param message:待广播的消息
//     * */
//    public void broadcast(String message){
//        if(localSockets.size() == 0|| Strings.isNullOrEmpty(message)) return;
//        logger.info("Glad to say broadcast to servers being started!");
//        for(WebSocket socket: localSockets) this.sendMessage(socket, message);
//        logger.info("Glad to say broadcast to servers has overred!");
//    }
}
