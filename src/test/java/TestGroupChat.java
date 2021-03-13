//import cn.itcast.dao.GroupChatDao;
//import cn.itcast.domain.GroupChat;
//import cn.itcast.service.GroupChatService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import java.util.Date;
//import java.util.LinkedList;
//
//@RunWith( SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
//public class TestGroupChat {
//    @Autowired
//    GroupChatDao gcdao;
//    @Autowired
//    GroupChatService gcService;
//    @Test
//    public void testAll(){
//        //System.out.println(gcdao.findByUid(1));
//        GroupChat g=new GroupChat();
//        int uid=3;
//        LinkedList<GroupChat> gcs= (LinkedList<GroupChat>) gcService.findByUid(uid);
//        GroupChat last=gcs.getLast();
//        String lastGcId=last.getGcId();
//        int postfix=Integer.parseInt(last.getGcId().substring(lastGcId.length()-1))+1;
//        String gcid="gc"+uid+"_"+postfix;
//        g.setBelongId(uid);
//        g.setGcId(gcid);
//        g.setBuildtime(new Date());
//        g.setGcname("2018级软件01官方群");
//        System.out.println(g);
//        gcdao.saveGroupChat(g);
//    }
//    @Test
//    public void testfind(){
//        for(GroupChat gc:gcService.joinGroups(3)){
//            System.out.println(gc.getGroupMsgs());
//        }
//       // gcService.dissolve("gc31");
//        GroupChat gc=gcService.findByGcid("gc3_1");
//        gc.setHeadImg("12");
//        gcService.updateGroupChat(gc);
//    }
//    @Test
//    public void testFindByName(){
//        System.out.println(gcService.findGcUsers("gc3_6"));
//        System.out.println(gcService.findByName("相亲相爱一家人"));
//    }
//}
