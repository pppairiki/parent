package com.pourtoujours.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Page<T> implements Serializable {
    private static final long serialVersionUID = 362498820763181265L;
    List<T> list;
    int nowPage;  //页码
    int pageSize; //每页记录数
    int allTotal; //记录总数

    public Page() {
        super();
    }

    public Page(List<T> list, int nowPage, int pageSize, int allTotal) {
        this.list = list;
        this.nowPage = nowPage;
        this.pageSize = pageSize;
        this.allTotal = allTotal;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(int allTotal) {
        this.allTotal = allTotal;
    }

    public static <T> Map<String, Object> createResultMap(Page<T> pageObj) {
        return createResultMap(pageObj, "list");
    }

    public static <T> Map<String, Object> createResultMap(Page<T> pageObj, String listName) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (pageObj == null) {
            return result;
        }
        result.put("allTotal", pageObj.getAllTotal());
        result.put("pageSize", pageObj.getPageSize());
        result.put("nowPage", pageObj.getNowPage());
        result.put(listName, pageObj.getList());
        return result;
    }
}
