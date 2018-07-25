package com.pourtoujours.base;

import com.pourtoujours.api.IUserService;
import com.pourtoujours.controller.ControllerLove;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Consumer {
    public static Logger log = Logger.getLogger(ControllerLove.class);
    static volatile ClassPathXmlApplicationContext context = null;

    public static ClassPathXmlApplicationContext singleton() {
        log.debug("singleton run start!");
        if (context == null) {
            synchronized (Consumer.class) {
                if (context == null) {
                    log.debug("context is null");
                    context = new ClassPathXmlApplicationContext(new String[] { "classpath:consumer.xml" });
                    log.debug("new context by consumer.xml");
                    context.start();
                }
            }
        }
        log.debug("singleton run over!");
        return context;
    };

    public static IUserService getUserService() {
        log.debug("getUserService run!");
        return (IUserService) singleton().getBean("userService");
    }

}
