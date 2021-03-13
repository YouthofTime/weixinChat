//import cn.itcast.dao.GroupSplitUserDao;
//import cn.itcast.dao.UserDao;
//import cn.itcast.domain.GroupSplit;
//import cn.itcast.domain.GroupSplitUser;
//import cn.itcast.domain.User;
//import cn.itcast.service.GroupSplitService;
//import cn.itcast.service.GroupSplitUserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith( SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
//public class TestGroupSplitUser {
//    @Autowired
//    GroupSplitUserDao gcuserdao;
//    @Autowired
//    GroupSplitUserService gsuService;
//    @Autowired
//    UserDao userDao;
//    @Test
//    public void testAll(){
//        User user=userDao.findByid(2);
//        for(GroupSplit gs:user.getGroupSplits()){
//            System.out.println(gs.getGsname()+gcuserdao.findByGsid(gs.getGsId()));
//        }
//        System.out.println(gcuserdao.findByUidAndGsId("gs31",1));
//        GroupSplitUser gs=new GroupSplitUser();
//        gs.setUserid(3);
//        gs.setRemark("郑宇明");
//        gs.setGsId("gs11");
//        gcuserdao.saveGroupChatUser(gs);
//
////        gcuserdao.deleteByGcid("gc11");
////        gcuserdao.deleteByGcIdUid("gc12",3);
////        GroupSplitUser gcuser=new GroupSplitUser();
////        gcuser.setGsId("gs21");
////        gcuser.setUserid(4);
////        gcuserdao.saveGroupChatUser(gcuser);
//    }
//    @Test
//    public void find(){
//        System.out.println(gsuService.isFriend(3,11));
//    }
//}
