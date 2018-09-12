package com.pourtoujours.util;

import java.io.Serializable;
import java.util.HashMap;

public class RedisSession implements Serializable{
    private HashMap<String,Serializable> sMap = new HashMap<>();
    public RedisSession(){
    }

    public void setAttribute(String key,Serializable value){
        sMap.put(key,value);
    }

    public Serializable getAttribute(String key){
        return sMap.get(key);
    }


}
