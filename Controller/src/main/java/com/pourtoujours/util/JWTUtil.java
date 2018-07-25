package com.pourtoujours.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JWTUtil {
    //private static final String PRIVATE_KEY = "123456789";
    private JWTUtil(){
    }

    private static JWTUtil instance = null;

    // 单例设计
    public static synchronized JWTUtil getInstance() {
        if (instance == null) {
            instance = new JWTUtil();
        }
        return instance;
    }

    private void jwtTest() throws InterruptedException {
      /*  // 设置3秒后过期
        String jwt = this.buildJwt(DateTime.now().plusSeconds(3).toDate());
        System.out.println(jwt);
        *//* 验证token是否可用 *//*
        boolean isOk = this.isJwtValid(jwt);
        System.out.println(isOk);*/
    }

    public String buildJwt(Date exp, int id, String account) {
        String PRIVATE_KEY = MD5Util.getInstance().md5(account);
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)//SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
                .setExpiration(exp)//expTime是过期时间
                .claim("id", id)//该方法是在JWT中加入值为vaule的key字段
                .compact();
        return jwt;
    }

    public int isJwtValid(String jwt, String account) {
        try {
            String PRIVATE_KEY = MD5Util.getInstance().md5(account);
            //解析JWT字符串中的数据，并进行最基础的验证
            Claims claims = Jwts.parser()
                    .setSigningKey(PRIVATE_KEY)//SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
                    .parseClaimsJws(jwt)//jwt是JWT字符串
                    .getBody();
            int value = claims.get("id", Integer.class);//获取自定义字段key
            return value;
        }
        //在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
        //在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
        catch (SignatureException | ExpiredJwtException e) {
            return -1;
        }
    }

}
