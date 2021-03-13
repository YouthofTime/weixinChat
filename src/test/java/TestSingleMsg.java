//import cn.itcast.dao.SingleMsgDao;
//import cn.itcast.domain.SingleMsg;
//import cn.itcast.service.SingleMsgService;
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
//public class TestSingleMsg {
//    @Autowired
//    SingleMsgDao singleMsgDao;
//    @Autowired
//    SingleMsgService smService;
//    @Test
//    public void testAll() throws ParseException {
//        System.out.println(singleMsgDao.findBySingleId(2));
//        System.out.println(singleMsgDao.findByUser(1,4));
//        // 插入
//        SingleMsg msg=new SingleMsg();
//        msg.setSendUid(1);
//        msg.setReceiveUid(4);
//        msg.setTime(new Date());
//        msg.setValue("插入一条记录");
//        singleMsgDao.saveSingleMsg(msg);
//        // 根据日期查找用户的聊天记录
//        String format="yyyy-MM-dd";
//        SimpleDateFormat sd=new SimpleDateFormat(format);
//        String start="2020-12-15";
//        String end="2020-12-24";
//        Date date1=sd.parse(start);
//        Date date2=sd.parse(end);
//        System.out.println(singleMsgDao.findByUserTime(1,4,date1,date2));
//    }
//    @Test
//    public void testLast(){
//        System.out.println(smService.findLastSingleMsg(3,4));
//        for(SingleMsg msg:smService.findByUser(2,3))
//            System.out.println(msg);
//    }
//}
