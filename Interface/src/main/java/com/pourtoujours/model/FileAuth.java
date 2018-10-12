package com.pourtoujours.model;

import java.io.Serializable;

public class FileAuth extends FileAuthKey implements Serializable {
    private Integer ableview;

    private Integer ableedit;

    public Integer getAbleview() {
        return ableview;
    }

    public void setAbleview(Integer ableview) {
        this.ableview = ableview;
    }

    public Integer getAbleedit() {
        return ableedit;
    }

    public void setAbleedit(Integer ableedit) {
        this.ableedit = ableedit;
    }
}