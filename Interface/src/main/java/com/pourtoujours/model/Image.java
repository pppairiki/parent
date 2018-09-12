package com.pourtoujours.model;

import java.io.Serializable;
import java.util.Date;

public class Image implements Serializable {
    private Integer id;

    private Integer infotype;

    private Integer infoid;

    private Integer createrid;

    private String creatername;

    private Long visable;

    private String remark;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfotype() {
        return infotype;
    }

    public void setInfotype(Integer infotype) {
        this.infotype = infotype;
    }

    public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
    }

    public Integer getCreaterid() {
        return createrid;
    }

    public void setCreaterid(Integer createrid) {
        this.createrid = createrid;
    }

    public String getCreatername() {
        return creatername;
    }

    public void setCreatername(String creatername) {
        this.creatername = creatername == null ? null : creatername.trim();
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