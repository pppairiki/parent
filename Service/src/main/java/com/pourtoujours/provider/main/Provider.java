package com.pourtoujours.provider.main;

import com.pourtoujours.dao.BaseDao;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
    public static Logger log = Logger.getLogger(Provider.class);
    static volatile ClassPathXmlApplicationContext context = null;

    public static ClassPathXmlApplicationContext singleton() {
        log.debug("singleton run start!");
        if (context == null) {
            synchronized (Provider.class) {
                if (context == null) {
                    log.debug("context is null");
                    context = new ClassPathXmlApplicationContext(new String[] { "classpath:/META-INF/spring/provider.xml" });
                    log.debug("new context by consumer.xml");
                    context.start();
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
}
