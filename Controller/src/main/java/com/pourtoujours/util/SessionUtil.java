package com.pourtoujours.util;

import com.pourtoujours.base.Consumer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SessionUtil {
    public static String getSid(){
        //SESSION:日期:20位随机字符串
        StringBuffer sidSB = new StringBuffer("SESSION:");
        sidSB.append(new Date().getTime()).append(":").append(RandomUtil.generateRandomNumber(20));
        return sidSB.toString();
    }

    public static void saveSession(String sid,RedisSession session){
        Consumer.getRedisTemplate().opsForValue().set(sid,session,1, TimeUnit.DAYS);
    }

    public static RedisSession getSession(String sid){
        return (RedisSession) Consumer.getRedisTemplate().opsForValue().get(sid);
    }
}
