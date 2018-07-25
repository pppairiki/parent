package com.pourtoujours.model;

import java.io.Serializable;
import java.util.Date;

public class Priority implements Serializable {
    private Integer id;

    private Integer prioritytype;

    private String name;

    private Integer visable;

    private String remark;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrioritytype() {
        return prioritytype;
    }

    public void setPrioritytype(Integer prioritytype) {
        this.prioritytype = prioritytype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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