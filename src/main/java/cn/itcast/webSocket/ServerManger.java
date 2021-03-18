package cn.itcast.webSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ServerManger {
    private static Collection<Chat> servers = Collections.synchronizedCollection(new ArrayList<Chat>());
    public static void add(Chat chat){
        servers.add(chat);
        //System.out.println("有连接加入！ 当前总连接数是："+ servers.size());
    }
    public static void remove(Chat server){

        servers.remove(server);
       // System.out.println("有连接退出！ 当前总连接数是："+ servers.size());
    }
    public static void broadcast(String msg){
        for (Chat chat : servers) {
            try {
                    chat.sendMessage(msg);
            } catch (IOException e) {
            }
        }
    }
    public static Collection<Chat> getServers(){
        return servers;
    }
}
