package cn.itcast.domain;

import java.io.Serializable;
import java.util.Date;

/*群聊中的用户(加入关系),查用户加入了哪些群聊用的是这个表(不是GroupChat),多对多*/
public class GroupChatUser implements Serializable {
    private String gcId;
    private int userid;
    private Date leaveTime;
    private Date enterTime;

    @Override
    public String toString() {
        return "GroupChatUser{" +
                "gcId='" + gcId + '\'' +
                ", userid=" + userid +
                ", leaveTime=" + leaveTime +
                ", enterTime=" + enterTime +
                '}';
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
