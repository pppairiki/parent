package com.pourtoujours.util;

import com.pourtoujours.base.Consumer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TokenUtil {
    public static String getRequestToken(){
        StringBuffer token = new StringBuffer("token:");
        token.append(new Date().getTime()).append(":").append(RandomUtil.generateRandomNumber(15));
        Consumer.getRedisTemplate().opsForValue().set(token.toString(),token.toString(),6, TimeUnit.HOURS);
        return token.toString();
    }

    public static boolean isFirstRequest(String token){
        if(StringUtil.isNullOrEmpty(token)){
            return false;
        }
        String ptoken = (String)Consumer.getRedisTemplate().opsForValue().get(token);
        if(token.equals(ptoken)){
            return Consumer.getRedisTemplate().delete(token);
        }else{
            return false;
        }
    }
}
