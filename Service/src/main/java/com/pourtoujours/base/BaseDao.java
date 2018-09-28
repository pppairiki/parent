package com.pourtoujours.base;

import com.pourtoujours.dao.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BaseDao {
    @Resource
    public UserMapper userDao;

    @Resource
    public UserGroupRelationMapper userGroupRelationDao;

    @Resource
    public UserRoleRelationMapper userRoleRelationDao;

    @Resource
    public GalleryMapper galleryDao;

    @Resource
    public CCFileMapper fileDao;
}
