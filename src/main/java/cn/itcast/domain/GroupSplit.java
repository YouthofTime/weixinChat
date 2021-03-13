package cn.itcast.domain;

import java.io.Serializable;

/*群分组(创建关系)*/
public class GroupSplit implements Serializable {
    private String gsId;
    private int belongId;
    private String gsname;


    @Override
    public String toString() {
        return "GroupSplit{" +
                "gsId='" + gsId + '\'' +
                ", belongId=" + belongId +
                ", gsname='" + gsname + '\'' +
                '}';
    }

    public String getGsId() {
        return gsId;
    }

    public void setGsId(String gsId) {
        this.gsId = gsId;
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }

    public String getGsname() {
        return gsname;
    }

    public void setGsname(String gsname) {
        this.gsname = gsname;
    }
}
