package cn.itcast.service.impl;

import cn.itcast.dao.GroupSplitDao;
import cn.itcast.domain.GroupSplit;
import cn.itcast.domain.User;
import cn.itcast.service.GroupSplitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service("gsService")
public class GroupSplitServiceImpl implements GroupSplitService {
    @Autowired
    GroupSplitDao gsdao;

    @Override
    public void saveGroupSplit(GroupSplit groupSplit) {
        gsdao.saveGroupSplit(groupSplit);
    }

    @Override
    public int findCountByUid(int uid) {
        return gsdao.findCountByUid(uid);
    }

    @Override
    public void deleteByGsId(String gsId) {
        gsdao.deleteByGsId(gsId);
    }

    @Override
    public void updateGroupSplit(String gsname, String gsId) {
        gsdao.updateGroupSplit(gsname,gsId);
    }

    @Override
    public List<GroupSplit> findSplits(int uid) {
        return gsdao.findByUid(uid);
    }
}
