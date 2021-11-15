package com.horn.blockchain.chaincode.p2p.p2ppbft;

import org.springframework.stereotype.Component;

@Component
public class P2pPointServer {
//    //日志记录
//    private Logger logger = LoggerFactory.getLogger(P2pPointServer.class);
//    //本机Server的WebSocket端口
//    //多机测试时可以改变该值
//    private int port = 7001;
//
//    //所有连接到服务端的WebSocket缓存器
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
//    //保证initServer在服务启动的时候就能加载
//    @PostConstruct
//    //保证服务端先于客户端加载
//    @Order(1)
//    public void initServer(){
//        /**
//         * 初始化WebSocket的服务端定义内部类对系那个socketServer,源于WebSocketServer; new
//         * InetSocketAddress(port)是WebSocketServer构造器的参数
//         * InetSocketAddress是（IP地址+端口号）类型，即端口地址类型
//         * */
//        final WebSocketServer socketServer = new WebSocketServer(new InetSocketAddress(port)) {
//            @Override
//            //创建连接成功时触发
//            public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
//                sendMessage(webSocket, "本地服务端创建连接成功");
//                //当创建一个WebSocket连接时，将该连接加入连接池
//                localSockets.add(webSocket);
//            }
//
//            @Override
//            //断开连接时触发
//            public void onClose(WebSocket webSocket, int i, String s, boolean b) {
//                logger.info(webSocket.getRemoteSocketAddress()+"客户端与服务器断开连接");
//                //当客户端断开连接时， 连接池删除该链接
//                localSockets.remove(webSocket);
//            }
//
//            @Override
//            //收到客户端发来的消息时触发
//            public void onMessage(WebSocket webSocket, String s) {
//               logger.info("服务端接受到客户端消息" + s);
//               sendMessage(webSocket, "收到消息");
//            }
//
//            @Override
//            //连接发生错误时调用，紧接着触发onClose方法
//            public void onError(WebSocket webSocket, Exception e) {
//                logger.info(webSocket.getRemoteSocketAddress()+"客户端连接错误");
//                localSockets.remove(webSocket);
//            }
//
//            @Override
//            public void onStart() {
//                logger.info("webSocket Server端启动...");
//            }
//        };
//
//        socketServer.start();
//        logger.info("服务端监听socketServer端口：" + port);
//    }
//
//    /**
//     * 相连接本机的某客户端发送消息
//     * @param ws
//     * @param message
//     * */
//    public void sendMessage(WebSocket ws, String message){
//        logger.info("发送给" + ws.getRemoteSocketAddress().getPort() + "的P2P消息是：" + message);
//        ws.send(message);
//    }
//
//    /**
//     * 向所有连接到本机客户端广播消息
//     *
//     * @param message:待广播内容
//     * */
//    public void broadcast(String message){
//        if(localSockets.size() == 0 || Strings.isNullOrEmpty(message)) return;
//        logger.info("Glad to say broadcast to clients has overred!");
//    }
}
