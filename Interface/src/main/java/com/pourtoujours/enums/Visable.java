package com.pourtoujours.enums;

public enum Visable {
    IsVisable(1L,"可用");

    private Long tabId;
    private String tabName;

    private Visable(Long tabId, String tabName){
        this.tabId = tabId;
        this.tabName = tabName;
    }

    public Long getTabId(){
        return tabId;
    }

    public String getTabName(){
        return tabName;
    }

    public static String getDesc(Integer value) {
        for (Visable each : Visable.values()) {
            if (each.getTabId().equals(value)) {
                return each.getTabName();
            }
        }
        return "";
    }

    public static Long getCode(String tabName){
        for (Visable each : Visable.values()) {
            if (each.getTabName().equals(tabName)) {
                return each.getTabId();
            }
        }
        return 0L;
    }
}
