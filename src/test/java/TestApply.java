//import cn.itcast.domain.Apply;
//import cn.itcast.service.ApplyService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith( SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
//public class TestApply {
//    @Autowired
//    ApplyService applyService;
//    @Test
//    public void testAll(){
//        applyService.deleteById(3);
//    }
//    @Test
//    public void testAdd(){
//        Apply apply=new Apply();
//        apply.setGsId("gs21");
//        apply.setIsApprove(0);
//        apply.setReceiveId("3");
//        apply.setSendUid("1");
//        apply.setRemark("郑宇明");
//        apply.setTextConfirm("我是郑宇明");
//        applyService.addApply(apply);
//    }
//}
