package cn.itcast.domain;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String phone;
    private String password;
    private String bind_email;
    private String username;
    private String address;
    private String sex;
    private String dsqId;
    private String headImg;
    private List<GroupChat> groupChats;// 创建的群
    private List<GroupSplit> groupSplits;// 拥有的分组

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", bind_email='" + bind_email + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", dsqId='" + dsqId + '\'' +
                ", headImg='" + headImg + '\'' +
                ", groupChats=" + groupChats +
                ", groupSplits=" + groupSplits +
                '}';
    }

    public List<GroupChat> getGroupChats() {
        return groupChats;
    }

    public void setGroupChats(List<GroupChat> groupChats) {
        this.groupChats = groupChats;
    }

    public List<GroupSplit> getGroupSplits() {
        return groupSplits;
    }

    public void setGroupSplits(List<GroupSplit> groupSplits) {
        this.groupSplits = groupSplits;
    }

    public String getDsqId() {
        return dsqId;
    }

    public void setDsqId(String dsqId) {
        this.dsqId = dsqId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBind_email() {
        return bind_email;
    }

    public void setBind_email(String bind_email) {
        this.bind_email = bind_email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
