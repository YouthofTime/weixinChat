package cn.itcast.domain;

import java.io.Serializable;

/*申请表*/
public class Apply implements Serializable {
    private int applyId;
    private String textConfirm;
    private String sendUid;
    private String receiveId;
    private String remark;
    private String gsId;
    private int isApprove;
    private String username;
    private String headImg;

    @Override
    public String toString() {
        return "Apply{" +
                "applyId=" + applyId +
                ", textConfirm='" + textConfirm + '\'' +
                ", sendUid='" + sendUid + '\'' +
                ", receiveId='" + receiveId + '\'' +
                ", remark='" + remark + '\'' +
                ", gsId='" + gsId + '\'' +
                ", isApprove=" + isApprove +
                ", username='" + username + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getTextConfirm() {
        return textConfirm;
    }

    public void setTextConfirm(String textConfirm) {
        this.textConfirm = textConfirm;
    }

    public String getSendUid() {
        return sendUid;
    }

    public void setSendUid(String sendUid) {
        this.sendUid = sendUid;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
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

    public int getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(int isApprove) {
        this.isApprove = isApprove;
    }
}
