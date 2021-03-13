package cn.itcast.service.impl;

import cn.itcast.dao.ApplyDao;
import cn.itcast.domain.Apply;
import cn.itcast.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applyService")
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    ApplyDao applyDao;
    @Override
    public void addApply(Apply apply) {
        applyDao.addApply(apply);
    }

    @Override
    public List<Apply> findAll(String receiveId) {
        return applyDao.findAll(receiveId);
    }

    @Override
    public void deleteById(int applyId) {
        applyDao.deleteById(applyId);
    }

    @Override
    public Apply findBySRId(String sendUid, String receiveId) {
        return applyDao.findBySRId(sendUid,receiveId);
    }

    @Override
    public void updateById(int applyId) {
        applyDao.updateById(applyId);
    }
}
