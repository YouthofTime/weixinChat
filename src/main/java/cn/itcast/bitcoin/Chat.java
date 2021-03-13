package cn.itcast.bitcoin;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/ws/chat")
public class Chat {
    // (解决俩个连接,只有1个连接消息有效的问题)
    boolean flag;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    // 客户端发送过来的消息
    private String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        ServerManger.add(this);

    }
    // 发给客户端的消息
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose(){
        ServerManger.remove(this);
        //System.out.println("关闭连接");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        flag=true;
        this.message=message;
    }

    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
}
