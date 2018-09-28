package com.pourtoujours.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.pourtoujours.api.IImageService;
import com.pourtoujours.base.Provider;
import com.pourtoujours.dao.GalleryMapper;
import com.pourtoujours.model.Gallery;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

public class ImageService implements IImageService{
    Logger log = Logger.getLogger(ImageService.class);
    @Resource
    public GalleryMapper galleryDao;

    public List<Gallery> getGalleryList(int userId, int pageIndex, int pageSize) {
        log.debug("ImageService.getList run userId"+userId+",pageIndex"+pageIndex+",pageSize"+pageSize);
        int startIndex = pageIndex * pageSize;
        String key = "t_gallery:"+userId;
        List<Gallery> retlist = Provider.getRedisTemplate().opsForList().range(key,(long)(pageIndex-1)*pageSize, (long)pageIndex*pageSize);
        //Set zSetValue = redisTemplate.opsForZSet().range("t_gallery:"+userId, (long)pageIndex*pageSize, (long)(pageIndex+1)*pageSize);
        if(CollectionUtils.isEmpty(retlist)){
            log.debug("ImageService.getList redis key is not exists");
            List<Gallery> list = galleryDao.getListByUser(userId);
            if(CollectionUtils.isNotEmpty(list)){
                retlist = list.subList((pageIndex-1)*pageSize,pageIndex*pageSize > list.size() ? list.size(): pageIndex*pageSize);
                Provider.getRedisTemplate().delete(key);
                for(Gallery gallery : list){
                    Provider.getRedisTemplate().opsForList().rightPush(key, gallery);
                }
            }
        }
        return retlist;
    }

    public List<Gallery> getAllList() {
        return galleryDao.getAllList();
    }
}
