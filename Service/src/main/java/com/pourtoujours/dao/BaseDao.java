package com.pourtoujours.dao;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BaseDao {
    @Resource
    public UserMapper UserDao;

    @Resource
    public UserGroupRelationMapper UserGroupRelationDao;

    @Resource
    public UserRoleRelationMapper UserRoleRelationDao;

    @Resource
    public GalleryMapper galleryDao;
}
