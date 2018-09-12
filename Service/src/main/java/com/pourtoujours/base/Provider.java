package com.pourtoujours.base;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.pourtoujours.dao.BaseDao;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class Provider {
    public static Logger log = Logger.getLogger(Provider.class);

    private  static ApplicationContext context=ServiceBean.getSpringContext();
    public static ApplicationContext singleton() {
        log.debug("singleton run start!");
        if (context == null) {
            synchronized (Provider.class) {
                if (context == null) {
                    context=ServiceBean.getSpringContext();
                }
            }
        }
        log.debug("singleton run over!");
        return context;
    };

    public static BaseDao getBaseDao() {
        log.debug("baseDao run!");
        return (BaseDao) singleton().getBean("baseDao");
    }

    public static RedisTemplate getRedisTemplate(){
        log.debug("getJdisCluster run!");
        return (RedisTemplate) singleton().getBean("clusterRedisTemplate");
    }
}
