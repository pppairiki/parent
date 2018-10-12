package com.pourtoujours.model;

import java.io.Serializable;

public class FileAuthKey implements Serializable {
    private Integer fileid;

    private Integer userid;

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}