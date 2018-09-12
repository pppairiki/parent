package com.pourtoujours.model;

import java.io.Serializable;
import java.util.Date;

public class CCFile  implements Serializable {
    private Integer id;

    private String name;

    private String summary;

    private Integer createrid;

    private String createname;

    private Integer isprivate;

    private Long visable;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Integer getCreaterid() {
        return createrid;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname == null ? null : createname.trim();
    }

    public Integer getIsprivate() {
        return isprivate;
    }

    public void setIsprivate(Integer isprivate) {
        this.isprivate = isprivate;
    }

    public Long getVisable() {
        return visable;
    }

    public void setVisable(Long visable) {
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