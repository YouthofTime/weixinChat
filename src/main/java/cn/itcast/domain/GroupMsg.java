package cn.itcast.domain;

import java.io.Serializable;
import java.util.Date;
/*群消息*/
public class GroupMsg implements Serializable {
    private int gmId;
    private Date time;
    private String value;
    private int sendUid;
    private String receiveGcid;
    private User sendUser;

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    @Override
    public String toString() {
        return "GroupMsg{" +
                "gmId=" + gmId +
                ", time=" + time +
                ", value='" + value + '\'' +
                ", sendUid=" + sendUid +
                ", receiveGcid='" + receiveGcid + '\'' +
                '}';
    }

    public int getGmId() {
        return gmId;
    }

    public void setGmId(int gmId) {
        this.gmId = gmId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSendUid() {
        return sendUid;
    }

    public void setSendUid(int sendUid) {
        this.sendUid = sendUid;
    }

    public String getReceiveGcid() {
        return receiveGcid;
    }

    public void setReceiveGcid(String receiveGcid) {
        this.receiveGcid = receiveGcid;
    }
}
