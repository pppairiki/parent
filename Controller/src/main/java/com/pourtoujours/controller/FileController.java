package com.pourtoujours.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pourtoujours.base.Consumer;
import com.pourtoujours.logic.FileLogicService;
import com.pourtoujours.model.CCFile;
import com.pourtoujours.model.CCFileContent;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FileController {
    public static Logger log = Logger.getLogger(FileController.class);

    @RequestMapping(value="getFileList",method= RequestMethod.POST)
    public String getFileList(@RequestBody String jsonStr,HttpServletRequest request){
        JsonObject json = JsonUtil.string2Json(jsonStr);
        if(json == null){
            return JsonUtil.newFailureJson("request json is null!").toString();
        }
        int userId = (int)request.getAttribute("userId");
        int pageNum = JsonUtil.getInt(json,"pageNum",0);
        int pageSize = JsonUtil.getInt(json,"pageSize",5);
        List<CCFile> fileList = Consumer.getFileService().getFileList(userId, pageNum, pageSize);
        if(CollectionUtils.isEmpty(fileList)){
            return JsonUtil.newFailureJson("result is null!").toString();
        }
        JsonObject retJson = JsonUtil.newSucessJson("sucessful");
        JsonArray ja = FileLogicService.parseList(fileList);
        retJson.add("list",ja);
        return retJson.toString();
    }

    @RequestMapping(value="getFile",method= RequestMethod.POST)
    public String getFile(@RequestBody String jsonStr,HttpServletRequest request){
        try {
            JsonObject json = JsonUtil.string2Json(jsonStr);
            if(json == null){
                return JsonUtil.newFailureJson("request json is null!").toString();
            }
            int userId = (int)request.getAttribute("userId");
            int id = JsonUtil.getInt(json,"id");
            List<CCFileContent> fileListContent = Consumer.getFileService().getFileContentList(id);
            if(CollectionUtils.isEmpty(fileListContent)){
                return JsonUtil.newFailureJson("result is null!").toString();
            }
            JsonObject retJson = JsonUtil.newSucessJson("sucessful");
            JsonArray ja = FileLogicService.parseFileContentList(fileListContent);
            retJson.add("list",ja);
            return retJson.toString();
        }catch (Exception e){
            log.debug("exception:",e);
            return JsonUtil.newFailureJson("service exception!").toString();
        }
    }

    /**
     * onle create file define
     * @param jsonStr
     * @param request
     * @return
     */
    @RequestMapping(value="createFile",method= RequestMethod.POST)
    public String createFile(@RequestBody String jsonStr,HttpServletRequest request) {
        JsonObject json = JsonUtil.string2Json(jsonStr);
        JsonObject retJson = new JsonObject();
        if (json == null) {
            return JsonUtil.newFailureJson("request json is null!").toString();
        }
        int userId = (int)request.getAttribute("userId");
        String userName = (String)request.getAttribute("userName");
        String name = JsonUtil.getString(json, "title");
        String summary = JsonUtil.getString(json, "summary");
        int isPrivate = JsonUtil.getInt(json, "isPrivate",0);
        if (StringUtil.isNullOrEmpty(name)) {
            return JsonUtil.newFailureJson("please type in name!").toString();
        }
        CCFile fileObject = new CCFile();
        fileObject.setName(name);
        fileObject.setSummary(summary);
        fileObject.setIsprivate(isPrivate);
        fileObject.setCreaterid(userId);
        fileObject.setCreatename(userName);
        int ret = Consumer.getFileService().save(fileObject);
        if (ret > 0) {
            return JsonUtil.newSucessJson("sucessful").toString();
        } else {
            return JsonUtil.newFailureJson("create file failure!").toString();
        }
    }

    /**
     * create file with content
     * @param jsonStr
     * @param request
     * @return
     */
    @RequestMapping(value="createFileWithContent",method= RequestMethod.POST)
    public String createFileWithContent(@RequestBody String jsonStr,HttpServletRequest request) {
        try{
            JsonObject json = JsonUtil.string2Json(jsonStr);
            JsonObject retJson = new JsonObject();
            if (json == null) {
                return JsonUtil.newFailureJson("request json is null!").toString();
            }
            int userId = (int)request.getAttribute("userId");
            String userName = (String)request.getAttribute("userName");
            String name = JsonUtil.getString(json, "title");
            String summary = JsonUtil.getString(json, "summary");
            int isPrivate = JsonUtil.getInt(json, "isPrivate",0);
            if (StringUtil.isNullOrEmpty(name)) {
                return JsonUtil.newFailureJson("please type in name!").toString();
            }
            String content = JsonUtil.getString(json, "content");
            int ret = Consumer.getFileService().createFileWithContent(userId,userName,name,summary,isPrivate,content);
            if(ret > 0){
                return JsonUtil.newSucessJson("sucessful").toString();
            }else{
                return JsonUtil.newFailureJson("create file failure!").toString();
            }
        }catch (Exception e){
            log.debug("exception:",e);
            return JsonUtil.newFailureJson("create file failure!").toString();
        }
    }

    @RequestMapping(value="updateFileContent",method= RequestMethod.POST)
    public String updateFileContent(@RequestBody String jsonStr,HttpServletRequest request) {
        try{
            JsonObject json = JsonUtil.string2Json(jsonStr);
            JsonObject retJson = new JsonObject();
            if (json == null) {
                return JsonUtil.newFailureJson("request json is null!").toString();
            }
            int userId = (int)request.getAttribute("userId");
            String userName = (String)request.getAttribute("userName");
            int id = JsonUtil.getInt(json, "id");
            if(id <= 0){
                return JsonUtil.newFailureJson("update file failure!").toString();
            }
            String name = JsonUtil.getString(json, "title");
            String summary = JsonUtil.getString(json, "summary");
            int isPrivate = JsonUtil.getInt(json, "isPrivate",0);
            if (StringUtil.isNullOrEmpty(name)) {
                return JsonUtil.newFailureJson("please type in name!").toString();
            }
            String content = JsonUtil.getString(json, "content");
            int ret = Consumer.getFileService().updateFileContent(userId,userName,id,name,summary,isPrivate,content);
            if(ret > 0){
                return JsonUtil.newSucessJson("sucessful").toString();
            }else{
                return JsonUtil.newFailureJson("update file failure!").toString();
            }
        }catch (Exception e){
            log.debug("exception:",e);
            return JsonUtil.newFailureJson("update file failure!").toString();
        }
    }
}
