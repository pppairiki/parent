package com.pourtoujours.base;

import com.pourtoujours.model.Gallery;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.List;

public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
    public static Logger log = Logger.getLogger(Provider.class);
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("InitListener run!");
        //防止重复执行。
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            //初始化缓存
            initGalleryCache();
        }
    }
    public void initGalleryCache(){
        log.debug("initGalleryCache run!");
        RedisTemplate redisTemplate = Provider.getRedisTemplate();
        List<Gallery> list = Provider.getBaseDao().galleryDao.getAllList();
        HashSet<String> keySet = new HashSet<String>();
        for(Gallery gallery : list){
            String key = "t_gallery:"+gallery.getCreaterid();
            if(!keySet.contains(key)){
                keySet.add(key);
                redisTemplate.delete(key);
            }
            redisTemplate.opsForList().rightPush(key, gallery);
        }
    }
}