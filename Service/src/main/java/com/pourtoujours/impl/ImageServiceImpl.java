package com.pourtoujours.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.pourtoujours.api.IImageService;
import com.pourtoujours.base.Provider;
import com.pourtoujours.dao.GalleryMapper;
import com.pourtoujours.model.Gallery;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

public class ImageServiceImpl implements IImageService{
    Logger log = Logger.getLogger(UserServiceImpl.class);
    @Resource
    public GalleryMapper galleryDao;
    RedisTemplate redisTemplate = Provider.getRedisTemplate();

    public List<Gallery> getGalleryList(int userId, int pageIndex, int pageSize) {
        log.debug("GalleryService.getList run userId"+userId+",pageIndex"+pageIndex+",pageSize"+pageSize);
        int startIndex = pageIndex * pageSize;
        String key = "t_gallery:"+userId;
        List<Gallery> list = redisTemplate.opsForList().range(key,(long)pageIndex*pageSize, (long)(pageIndex+1)*pageSize);
        //Set zSetValue = redisTemplate.opsForZSet().range("t_gallery:"+userId, (long)pageIndex*pageSize, (long)(pageIndex+1)*pageSize);
        if(CollectionUtils.isEmpty(list)){
            log.debug("GalleryService.getList redis key is not exists");
            list = galleryDao.getList(userId, startIndex, pageSize);
        }
        return list;
    }

    public List<Gallery> getAllList() {
        return galleryDao.getAllList();
    }
}
