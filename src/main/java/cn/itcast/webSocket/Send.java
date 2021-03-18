package cn.itcast.webSocket;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Random;


@WebServlet(name="Send",urlPatterns = "/Send",loadOnStartup=1) //标记为Servlet不是为了其被访问，而是为了便于伴随Tomcat一起启动
public class Send extends HttpServlet implements Runnable {

    public void init(ServletConfig config){
        startup();
    }

    public  void startup(){
        new Thread(this).start();
    }
    @Override
    public void run() {

        while(true) {
            // 广播当前连接数目
           // ServerManger.broadcast("{number:"+ServerManger.getServers().size()+"}");
        int duration = new Random().nextInt(1000);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            String msg=" ";
        // 广播最后输入的,保证对每个客户端的信息都是一致的,不能在broadcast内部传入各自的信息
            for(Chat chat:ServerManger.getServers()){
                //判断chat的消息是否有值,不然每次建立新连接,就重置为null了
                if(chat.getMessage()!=null&&chat.flag==true)
                    msg=chat.getMessage();
                chat.flag=false; // 新增标志位,每次发消息过来设置为true
//                System.out.println("客户端消息:"+msg);
//                System.out.println("结束");
            }
            MyMessage my=MyMessage.getInstance();
            // 如果没有新消息发送过来,就不要发送了
            if(my.getMessage()==msg)
                msg="";
            if(msg!="") {
//                System.out.println("临时保存消息:"+my.getMessage());
//                System.out.println("发送消息:"+msg);
                my.setMessage(msg);
                // 确认发送消息,更新message
                ServerManger.broadcast(msg);
            }

        }
    }
    }