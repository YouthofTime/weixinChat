package cn.itcast.domain;

import java.io.Serializable;
import java.util.Date;
/*俩个用户之间的聊天*/
public class SingleMsg implements Serializable {
    private int singleId;
    private Date time;
    private String value;
    private int sendUid;
    private int receiveUid;
    private User sendUser;

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    @Override
    public String toString() {
        return "SingleMsg{" +
                "singleId=" + singleId +
                ", time=" + time +
                ", value='" + value + '\'' +
                ", sendUid=" + sendUid +
                ", receiveUid=" + receiveUid +
                ", sendUser=" + sendUser +
                '}';
    }

    public int getSingleId() {
        return singleId;
    }

    public void setSingleId(int singleId) {
        this.singleId = singleId;
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

    public int getReceiveUid() {
        return receiveUid;
    }

    public void setReceiveUid(int receiveUid) {
        this.receiveUid = receiveUid;
    }
}
