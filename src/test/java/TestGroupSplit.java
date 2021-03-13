//import cn.itcast.dao.GroupSplitDao;
//import cn.itcast.domain.GroupSplit;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith( SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
//public class TestGroupSplit {
//    @Autowired
//    GroupSplitDao gsdao;
//    @Test
//    public void testAll(){
//        System.out.println(gsdao.findByUid(2));
//
//        GroupSplit gsplit=new GroupSplit();
//        int uid=4;
//        gsplit.setBelongId(uid);
//
//        int lgsid=gsdao.findCountByUid(uid);
//        lgsid+=1;
//        gsplit.setGsId("gs"+uid+lgsid);
//        gsplit.setGsname("新朋友");
//        System.out.println(gsplit);
//        gsdao.saveGroupSplit(gsplit);
//
//    }
//}
