package com.pourtoujours.model;

import java.io.Serializable;
import java.util.Date;

public class UserGroupRelation implements Serializable {
    private Integer id;

    private String name;

    private Integer Userid;

    private Integer Usergroupid;

    private Integer visable;

    private String remark;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUserid() {
        return Userid;
    }

    public void setUserid(Integer Userid) {
        this.Userid = Userid;
    }

    public Integer getUsergroupid() {
        return Usergroupid;
    }

    public void setUsergroupid(Integer Usergroupid) {
        this.Usergroupid = Usergroupid;
    }

    public Integer getVisable() {
        return visable;
    }

    public void setVisable(Integer visable) {
        this.visable = visable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}