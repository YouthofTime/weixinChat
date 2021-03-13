//import cn.itcast.dao.UserDao;
//import cn.itcast.domain.*;
//import cn.itcast.service.*;
//import cn.itcast.utils.DateFormat;
//import cn.itcast.utils.Pingyin;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.*;
//
//import static cn.itcast.utils.Pingyin.convertFirstHanzi;
//import static cn.itcast.utils.Pingyin.convertHanzi2Pinyin;
//
//@RunWith( SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
//public class TestSSM {
//    @Autowired
//    UserService userService;
//    @Autowired
//    GroupSplitService gsService;
//    @Autowired
//    GroupChatService gcService;
//    @Autowired
//    GroupMsgService gmService;
//    @Autowired
//    SingleMsgService smService;
//    @Autowired
//    GroupSplitUserService gsuService;
//    @Autowired
//    UserDao userDao;
//    @Test
//    public void search(){
//        String val="c";
//        try {
//            //String CHINESE_REGEX = "[\\u4e00-\\u9fa5]+";// 纯中文匹配正则
//            String LETTER_REGEX = "[a-zA-Z]+";  // 纯英文
//            User user = userService.findByid(3);
//            // 一.查询出该用户的所有好友
//            List<User> friends = new ArrayList<>();       // 好友用户表
//            Map<User, String> remarkMap = new HashMap<>(); // 好友备注表
//            for (GroupSplit gs : user.getGroupSplits()) {
//                List<User> friend = userService.findFriendByGsId(gs.getGsId());
//                for (User u : friend) {
//                    String remark = gsuService.findFriendRemark(gs.getGsId(), u.getId());
//                    remarkMap.put(u, remark);
//                }
//                friends.addAll(friend);
//            }
//            // 二.开始搜索
//            Map<Integer, User> userMap = new HashMap<>(); // 最终返还给前端的数据(还要处理一下)
//            String regex = ".*" + val + ".*";
//            //1.根据名字和dsqId进行模糊查询
//            for (User friend : friends) {
//                String name = friend.getUsername();
//                String dsqId = friend.getDsqId();
//                String pingyinName = Pingyin.convertHanzi2Pinyin(name, true).toLowerCase();
//                String pingyinFirst = Pingyin.convertFirstHanzi(name, true).toLowerCase();
//                System.out.println(pingyinName);
//                // 如果输入的全是英文字母
//                if (val.matches(LETTER_REGEX)) {
//                    val = val.toLowerCase();
//                    regex = ".*" + val + ".*"; // 别忘了
//                }
//
//                if (name.matches(regex)) {
//                    userMap.put(friend.getId(), friend);
//                } else if (dsqId.matches(regex)) {
//                    userMap.put(friend.getId(), friend);
//                }
//                // 拼音匹配上(首字母,全部字母)
//                else if (val.matches(LETTER_REGEX) && (pingyinName.matches(regex) || pingyinFirst.matches(regex))) {
//                    userMap.put(friend.getId(), friend);
//                }
//            }
//            // 2.备注模糊查询
//            for (User u : remarkMap.keySet()) {
//                String s = remarkMap.get(u);
//                String pingyinName = Pingyin.convertHanzi2Pinyin(s, true).toLowerCase();
//                String pingyinFirst = Pingyin.convertFirstHanzi(s, true).toLowerCase();
//                // 如果输入的全是英文字母转换成小写
//                if (val.matches(LETTER_REGEX)) {
//                    val = val.toLowerCase();
//                    regex = ".*" + val + ".*"; // 别忘了
//                }
//                if (s.matches(regex)) {
//                    userMap.put(u.getId(), u);
//                } else if (val.matches(LETTER_REGEX) && (pingyinName.matches(regex) || pingyinFirst.matches(regex))) {
//                    userMap.put(u.getId(), u);
//                }
//            }
//            // 2.获得
//            List<User> users = new ArrayList<>(); // 查询出的好友
//            List<String> remarks = new ArrayList<>();// 查询出的好友对应的备注
//            for (User friend : userMap.values()) {
//                users.add(friend);
//                remarks.add(remarkMap.get(friend));
//                System.out.println(friend);
//                System.out.println(remarkMap.get(friend));
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public void testAll(){
//            User user=userService.findByBindEmail("269@qq.com");
//            System.out.println(user.getId()+":");
//            List<GroupChat> gcs=user.getGroupChats();
//            System.out.println("群聊:"+gcs);
//            List<GroupSplit> gss=user.getGroupSplits();
//            System.out.println("分组:"+gss);
//            System.out.println(user);
//}
//    @Test
//    public void testByName(){
//        List<User> users=userService.findByName("知乎用户");
//        for(User user:users)
//            System.out.println(user);
//        User user=userService.findByDsqId("zym");
//        System.out.println(user);
//    }
//    @Test
//    public void testPingyin(){
//        System.out.println(convertHanzi2Pinyin("陈聪", true).toLowerCase());
//        System.out.println(convertFirstHanzi("Zx吴超", true).toLowerCase());
//        String CHINESE_REGEX = "[\\u4e00-\\u9fa5]+";// 纯中文匹配正则
//        String LETTER_REGEX="[a-zA-Z]+";  // 纯英文
//        System.out.println("abc郑".matches(LETTER_REGEX));
//        System.out.println("郑宇明a".matches(CHINESE_REGEX));
//        System.out.println("chencong".matches("(.*)chenc(.*)"));
//    }
//    @Test
//    public void testunReadMsg(){
//        // String代表聊天室的名字(3-2就是用户3和用户2,3-gc31用户3和群聊gc31),Integer代表未读消息的数目
//        Map<String,Integer> unreads=new HashMap<>();
//        // 1.从数据库查询出一个user对象
//        User user=userService.findByid(3);
//        // 一.好友框未读消息(完成了)
//        // 1.获取这个user对象的所有好友
//        List<GroupSplit> splits=user.getGroupSplits();
//        List<GroupSplitUser> friends=new ArrayList<>();// 登录用户的所有好友
//        // 2.遍历,获得当前用户的好友所在的分组
//        for(GroupSplit split:splits)
//            friends.addAll(gsuService.findFriends(split.getGsId()));
//        for(GroupSplitUser gs:friends){
//            int receiveUid=gs.getUserid();
//            // 3.查询出俩人的聊天记录的最后一条消息
//            SingleMsg msg=smService.findLastSingleMsg(user.getId(),receiveUid);
//            SingleMsg msg1=smService.findLastSingleMsg(receiveUid,user.getId());
//            SingleMsg lastmsg=null;
//            // 都不为空
//            if(msg!=null&&msg1!=null){
//                if(msg.getTime().compareTo(msg1.getTime())<0)
//                    lastmsg=msg1;
//                else
//                    lastmsg=msg;
//            }
//            // 收到的消息不为空
//            else if(msg1!=null&&msg==null)
//                lastmsg=msg1;
//            else if(msg1==null&&msg!=null)
//                lastmsg=msg;
//            // 4.最后一条消息是本人发的,就没有未读消息(或者是为null,也要设置为0)
//            if(lastmsg==null||lastmsg==msg){
//                unreads.put(user.getId()+"-"+gs.getUserid(),0);
//            }
//            // 5.不是,离开好友框的时间到最后一条消息这个时间段的消息数量
//            else if(lastmsg==msg1){
//                Date leaveTime=gs.getLeaveTime();
//                List<SingleMsg> singleMsgs=smService.findAfterTime(gs.getUserid(),user.getId(),leaveTime);
//                unreads.put(user.getId()+"-"+gs.getUserid(),singleMsgs.size());
//            }
//        }
//        //二.群聊框未读消息
//        // 1.获得该用户加入的所有群聊
//        List<GroupChat> joins=gcService.joinGroups(user.getId());
//        // 2.查询群聊的最后一条消息
//        for(GroupChat join:joins){
//            GroupMsg gLastMsg=gmService.findLastGroupMsg(join.getGcId());
//            // 该群聊没发送过消息
//            if(gLastMsg==null)
//                unreads.put(join.getGcId()+"-"+user.getId(),0);
//            else {
//                // 1.最后一条消息是本人发送的
//                if(gLastMsg.getSendUid()==user.getId())
//                    unreads.put(join.getGcId()+"-"+user.getId(),0);
//                    // 2.不是,离开好友框的时间到最后一条消息这个时间段的消息数量
//                else {
//                    Date leaveTime=gcService.findLeaveTime(join.getGcId(),user.getId());
//                    System.out.println(leaveTime);
//                    List<GroupMsg> groupMsgs=gmService.findAfterTime(leaveTime,join.getGcId());
//                    System.out.println(groupMsgs);
//                    unreads.put(join.getGcId()+"-"+user.getId(),groupMsgs.size());
//                }
//            }
//        }
//
//        for(String s:unreads.keySet()){
//            System.out.println(s+":"+unreads.get(s));
//        }
//    }
//    @Test
//    public void testTemp(){
//        String str="2021/01/26 17:38:00";
//        Date date= DateFormat.parse(str,DateFormat.sdf);
//        List<SingleMsg> singleMsgs=smService.findAfterTime(2,3,date);
//        for(SingleMsg s:singleMsgs)
//            System.out.println(s);
//
//    }
//}
