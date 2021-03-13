package cn.itcast.service.impl;

import cn.itcast.dao.SingleMsgDao;
import cn.itcast.domain.SingleMsg;
import cn.itcast.service.SingleMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("smService")
public class SingleMsgServiceImpl implements SingleMsgService {
    @Autowired
    SingleMsgDao singleMsgDao;
    @Override
    public SingleMsg findLastSingleMsg(int sendUid, int receiveUid) {
        int count=singleMsgDao.findCountByUid(sendUid,receiveUid);
        if(count==0)
            return null;
        SingleMsg msg=singleMsgDao.findLastSingleMsg(sendUid,receiveUid,count-1,count);
        return msg;
    }

    @Override
    public void saveSingleMsg(SingleMsg singleMsg) {
        singleMsgDao.saveSingleMsg(singleMsg);
    }

    @Override
    public List<SingleMsg> findByUser(int uid1, int uid2) {
        List<SingleMsg> msgs=singleMsgDao.findByUser(uid1,uid2);
        return msgs;
    }

    @Override
    public List<SingleMsg> findAfterTime(int sendUid, int receiveUid, Date time) {
        return singleMsgDao.findAfterTime(sendUid,receiveUid,time);
    }
}
