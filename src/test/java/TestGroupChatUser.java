//import cn.itcast.dao.GroupChatUserDao;
//import cn.itcast.domain.GroupChatUser;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith( SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
//public class TestGroupChatUser {
//    @Autowired
//    GroupChatUserDao gcuserdao;
//    @Test
//    public void testAll(){
//        System.out.println(gcuserdao.findByGcid("gc33").size());
//        System.out.println(gcuserdao.findByUid(3));
//        int i=(int)(Math.random()*4+1);
//        System.out.println(i);
////        gcuserdao.deleteByGcid("gc11");
////        gcuserdao.deleteByGcIdUid("gc12",3);
//        GroupChatUser gcuser=new GroupChatUser();
////        gcuser.setGcId("gc11");
////        gcuser.setUserid(1);
////        gcuserdao.saveGroupChatUser(gcuser);
//    }
//}
