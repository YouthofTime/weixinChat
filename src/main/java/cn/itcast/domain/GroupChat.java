package cn.itcast.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*群聊天(只用于创建这个关系)*/
public class GroupChat implements Serializable {
    private String gcId;
    private String gcname;
    private Date buildtime;
    private int belongId;
    private String headImg;
    private List<GroupMsg> groupMsgs;/*群聊天记录*/

    @Override
    public String toString() {
        return "GroupChat{" +
                "gcId='" + gcId + '\'' +
                ", gcname='" + gcname + '\'' +
                ", buildtime=" + buildtime +
                ", belongId=" + belongId +
                ", headImg='" + headImg + '\'' +
                ", groupMsgs=" + groupMsgs +
                '}';
    }

    public List<GroupMsg> getGroupMsgs() {
        return groupMsgs;
    }

    public void setGroupMsgs(List<GroupMsg> groupMsgs) {
        this.groupMsgs = groupMsgs;
    }

    public String getHeadImg() {

        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getGcname() {
        return gcname;
    }

    public void setGcname(String gcname) {
        this.gcname = gcname;
    }

    public Date getBuildtime() {
        return buildtime;
    }

    public void setBuildtime(Date buildtime) {
        this.buildtime = buildtime;
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }
}
