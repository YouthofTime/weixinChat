package cn.itcast.service.impl;

import cn.itcast.dao.GroupMsgDao;
import cn.itcast.domain.GroupMsg;
import cn.itcast.service.GroupMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("gmService")
public class GroupMsgServiceImpl implements GroupMsgService {
    @Autowired
    GroupMsgDao msgDao;
    @Override
    public GroupMsg findLastGroupMsg(String gcId) {
        int count=msgDao.findCountByGcId(gcId);
        if(count==0)
            return null;
        GroupMsg gs=msgDao.findLastGroupMsg(gcId,count-1,count);
        return gs;
    }

    @Override
    public void saveGroupMsg(GroupMsg groupMsg) {
        msgDao.saveGroupMsg(groupMsg);
    }

    @Override
    public List<GroupMsg> findAfterTime(Date time, String gcId) {
        return msgDao.findAfterTime(time,gcId);
    }

    @Override
    public void deleteByGcId(String gcId) {
        msgDao.deleteByGcId(gcId);
    }
}
