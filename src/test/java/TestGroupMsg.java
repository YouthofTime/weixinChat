//import cn.itcast.dao.GroupMsgDao;
//import cn.itcast.domain.GroupMsg;
//import cn.itcast.service.GroupMsgService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@RunWith( SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
//public class TestGroupMsg {
//    @Autowired
//    GroupMsgDao msgDao;
//    @Autowired
//    GroupMsgService gmService;
//    @Test
//    public void testAll() throws ParseException {
//        GroupMsg gm=new GroupMsg();
////        gm.setReceiveGcid("gc031");
////        gm.setSendUid(5);
////        gm.setTime(new Date());
////        gm.setValue("插入一条记录");
////        msgDao.saveGroupMsg(gm);
//        System.out.println(msgDao.findByGcId("gc013"));
//        // 根据日期查找用户的聊天记录
//        String format="yyyy-MM-dd";
//        SimpleDateFormat sd=new SimpleDateFormat(format);
//        String start="2020-12-15";
//        String end="2020-12-24";
//        Date date1=sd.parse(start);
//        Date date2=sd.parse(end);
//        System.out.println(msgDao.findByGcTime("gc031",date1,date2));
//        System.out.println(msgDao.findByGmId(2));
//    }
//    @Test
//    public void testfind(){
//        System.out.println(gmService.findLastGroupMsg("gc11"));
//    }
//}
