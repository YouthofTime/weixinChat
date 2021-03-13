package cn.itcast.domain;

import java.io.Serializable;
import java.util.Date;

/*分组包括的用户(拥有关系) 多对多*/
public class GroupSplitUser implements Serializable {
    private String gsId;
    private int userid;
    private String remark;
    private Date leaveTime;

    @Override
    public String toString() {
        return "GroupSplitUser{" +
                "gsId='" + gsId + '\'' +
                ", userid=" + userid +
                ", remark='" + remark + '\'' +
                ", leaveTime=" + leaveTime +
                '}';
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGsId() {
        return gsId;
    }

    public void setGsId(String gsId) {
        this.gsId = gsId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
