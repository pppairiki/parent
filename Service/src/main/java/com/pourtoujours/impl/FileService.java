package com.pourtoujours.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.pourtoujours.api.IFileService;
import com.pourtoujours.base.Provider;
import com.pourtoujours.common.Page;
import com.pourtoujours.dao.CCFileContentMapper;
import com.pourtoujours.dao.CCFileMapper;
import com.pourtoujours.dao.FileAuthMapper;
import com.pourtoujours.enums.Visable;
import com.pourtoujours.model.CCFile;
import com.pourtoujours.model.CCFileContent;
import com.pourtoujours.model.FileAuth;
import com.pourtoujours.util.StringUtil;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FileService implements IFileService{
    Logger log = Logger.getLogger(FileService.class);
    @Resource
    public CCFileMapper fileDao;
    @Resource
    public CCFileContentMapper fileContentDao;
    @Resource
    public FileAuthMapper fileAuthDao;

    public CCFile getFile(int id){
        return fileDao.selectByPrimaryKey(id);
    }

    public int updateFile(CCFile file){
        return fileDao.updateByPrimaryKey(file);
    }

    public CCFileContent getFileContent(int id){
        return fileContentDao.selectByPrimaryKey(id);
    }

    public CCFileContent getFileContentByFileId(int fileId){
        return fileContentDao.selectByByFileId(fileId);
    }

    public int updateFileContent(CCFileContent fileContent){
        return fileContentDao.updateByPrimaryKeyWithBLOBs(fileContent);
    }

    public List<CCFile> getFileList(int userId, int pageIndex, int pageSize) {
        log.debug("FileService.getList run userId"+userId+",pageIndex"+pageIndex+",pageSize"+pageSize);
        int startIndex = (pageIndex-1) * pageSize;
        String key = "t_file:"+userId;
        List<CCFile> list = Provider.getRedisTemplate().opsForList().range(key,(long)(pageIndex-1)*pageSize, (long)pageIndex*pageSize);
        //Set zSetValue = redisTemplate.opsForZSet().range("t_gallery:"+userId, (long)pageIndex*pageSize, (long)(pageIndex+1)*pageSize);
        if(CollectionUtils.isEmpty(list)){
            log.debug("GalleryService.getList redis key is not exists");
            list = fileDao.getList(userId, startIndex, pageSize);
        }
        return list;
    }

    public int save(CCFile fileObject) {
        return fileDao.insert(fileObject);
    }

    public int saveFileContent(CCFileContent fileContent) {
        return fileContentDao.insert(fileContent);
    }

    @Transactional
    public int createFileWithContent(int userId, String userName, String name, String summary, int isPrivate, String content) throws Exception{
        try{
            CCFile fileObject = new CCFile();
            fileObject.setName(name);
            fileObject.setVisable(Visable.IsVisable.getTabId());
            fileObject.setSummary(summary);
            fileObject.setIsprivate(isPrivate);
            fileObject.setCreaterid(userId);
            fileObject.setCreatename(userName);
            fileObject.setCreatetime(new Date());
            int ret = save(fileObject);
            log.debug("update file result is:"+ret);
            if (ret > 0) {
                if(!StringUtil.isNullOrEmpty(content)){
                    int fileId = fileObject.getId();
                    CCFileContent fileContent = new CCFileContent();
                    fileContent.setFileid(fileId);
                    fileContent.setSortid(0);
                    fileContent.setContent(content);
                    fileContent.setCreaterid(userId);
                    fileContent.setCreatename(userName);
                    fileContent.setCreatetime(new Date());
                    fileContent.setUpdatetime(new Date());
                    int ret2 = saveFileContent(fileContent);
                    log.debug("save file content result is:"+ret2);
                    if (ret2 > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }else{
                    return 1;
                }
            } else {
                return -1;
            }
        }catch (Exception e){
            log.debug("exception",e);
            throw new Exception(e);
        }
    }

    public List<CCFileContent> getFileContentList(int id) {
        String key = "t_file_content:"+id;
        List<CCFileContent> list = Provider.getRedisTemplate().opsForList().range(key,(long)1, (long)-1);
        if(CollectionUtils.isEmpty(list)){
            log.debug("GalleryService.getList redis key is not exists");
            list = fileContentDao.getList(id);
        }
        return list;
    }

    @Transactional
    public int updateFileContent(int userId, String userName, int id, String name, String summary, int isPrivate, String content) throws Exception{
        try{
            CCFile fileObject = getFile(id);
            if(fileObject == null){
                throw new Exception("file is not exists");
            }
            fileObject.setName(name);
            fileObject.setSummary(summary);
            fileObject.setIsprivate(isPrivate);
            fileObject.setCreaterid(userId);
            fileObject.setCreatename(userName);
            int ret = updateFile(fileObject);
            log.debug("create file result is:"+ret);
            if (ret > 0) {
                if(!StringUtil.isNullOrEmpty(content)){
                    int fileId = fileObject.getId();
                    CCFileContent fileContent = getFileContentByFileId(fileObject.getId());
                    fileContent.setFileid(fileId);
                    fileContent.setSortid(0);
                    fileContent.setContent(content);
                    fileContent.setCreaterid(userId);
                    fileContent.setCreatename(userName);
                    fileContent.setUpdatetime(new Date());
                    int ret2 = updateFileContent(fileContent);
                    log.debug("save file content result is:"+ret2);
                    if (ret2 > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }else{
                    return 1;
                }
            } else {
                return -1;
            }
        }catch (Exception e){
            log.debug("exception",e);
            throw new Exception(e);
        }
    }

    public int deleteFile(int userId, String userName, int id) {
        return fileDao.del(id);
    }

    public Page<CCFile> getFilePage(int userId, int pageIndex, int pageSize) {
        log.debug("FileService.getFilePage run userId"+userId+",pageIndex"+pageIndex+",pageSize"+pageSize);
        int startIndex = (pageIndex-1) * pageSize;
        String key = "t_file:"+userId;
        /*List<CCFile> retlist = Provider.getRedisTemplate().opsForList().range(key,(long)(pageIndex-1)*pageSize, (long)pageIndex*pageSize-1);
        long allTotal = Provider.getRedisTemplate().opsForList().size(key); //记录总数
        if(CollectionUtils.isEmpty(retlist)){
            log.debug("FileService.getFilePage redis key is not exists");
            List<CCFile> list = fileDao.getListByUser(userId);
            if(CollectionUtils.isNotEmpty(list)){
                retlist = list.subList((pageIndex-1)*pageSize,pageIndex*pageSize > list.size() ? list.size(): pageIndex*pageSize);
                allTotal = list.size();
                Provider.getRedisTemplate().delete(key);
                for(CCFile file : list){
                    Provider.getRedisTemplate().opsForList().rightPush(key, file);
                }
            }
        }*/
        List<CCFile> list = fileDao.getListByUser(userId);
        List<CCFile> retlist = list.subList((pageIndex-1)*pageSize,pageIndex*pageSize > list.size() ? list.size(): pageIndex*pageSize);
        for(CCFile file : retlist){
            file.setAbleEdit(1);
        }
        long allTotal = list.size(); //记录总数
        Page<CCFile> page = new Page<CCFile>();
        page.setList(retlist);
        page.setAllTotal((int)allTotal);
        page.setNowPage(pageIndex);
        page.setPageSize(pageSize);
        return page;
    }

    public Page<CCFile> getPublicFilePage(int pageIndex, int pageSize) {
        log.debug("FileService.getFilePage run pageIndex"+pageIndex+",pageSize"+pageSize);
        int startIndex = (pageIndex-1) * pageSize;
        List<CCFile> list = fileDao.getPublicFileList();
        List<CCFile> retlist = list.subList((pageIndex-1)*pageSize,pageIndex*pageSize > list.size() ? list.size(): pageIndex*pageSize);
        for(CCFile file : retlist){
            file.setAbleEdit(0);
        }
        long allTotal = list.size(); //记录总数
        Page<CCFile> page = new Page<CCFile>();
        page.setList(retlist);
        page.setAllTotal((int)allTotal);
        page.setNowPage(pageIndex);
        page.setPageSize(pageSize);
        return page;
    }

    public Page<CCFile> getShowMeFilePage(int userId, int pageNum, int pageSize) throws Exception{
        log.debug("FileService.getShowMeFilePage run userId"+userId+",pageIndex"+pageNum+",pageSize"+pageSize);
        //校验
        if(userId <= 0){
            throw new Exception("user id in null");
        }
        //先获取权限
        List<FileAuth> faList = fileAuthDao.getListByUser(userId);
        if(CollectionUtils.isEmpty(faList)){
            throw new Exception("file auth is null");
        }
        List<FileAuth> tFAList = faList.subList((pageNum-1)*pageSize,pageNum*pageSize > faList.size() ? faList.size(): pageNum*pageSize);
        List<Integer> fileIdList = new ArrayList<Integer>();
        HashMap<Integer,FileAuth> map = new HashMap<Integer, FileAuth>(8);
        for(FileAuth fa : tFAList){
            fileIdList.add(fa.getFileid());
            map.put(fa.getFileid(),fa);
        }
        List<CCFile> list = fileDao.getShowMeFileList(fileIdList);
        for(CCFile file : list){
            file.setAbleEdit(1);
            FileAuth fa = map.get(file.getId());
            file.setAbleEdit(fa.getAbleedit());
        }
        Page<CCFile> page = new Page<CCFile>();
        page.setList(list);
        page.setAllTotal((int)faList.size());
        page.setNowPage(pageNum);
        page.setPageSize(pageSize);
        return page;
    }
}
