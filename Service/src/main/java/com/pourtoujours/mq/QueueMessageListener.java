package com.pourtoujours.mq;

import com.pourtoujours.base.Provider;
import org.apache.log4j.Logger;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

public class  QueueMessageListener implements SessionAwareMessageListener {
    public static Logger log = Logger.getLogger(Provider.class);
    public void onMessage(Message message, Session session) throws JMSException {
        MapMessage tm = (MapMessage) message;
        try {
            log.debug("---------消息消费---------");
            log.debug("消息ID:\t" + tm.getJMSMessageID());
        } catch (JMSException e) {
            session.recover();//唤起重传
            e.printStackTrace();
        }
    }
}
