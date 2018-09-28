package com.pourtoujours.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pourtoujours.base.Consumer;
import com.pourtoujours.logic.ImageLogicService;
import com.pourtoujours.model.Gallery;
import com.pourtoujours.util.JsonUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ImageController {
    public static Logger log = Logger.getLogger(ImageController.class);


    @RequestMapping(value="getGalleryList",method= RequestMethod.POST)
    public String getGalleryList(@RequestBody String jsonStr,HttpServletRequest request){
        JsonObject json = JsonUtil.string2Json(jsonStr);
        if(json == null){
            return JsonUtil.newFailureJson("request json is null!").toString();
        }
        int userId = (int)request.getAttribute("userId");
        int pageNum = JsonUtil.getInt(json,"pageNum",1);
        int pageSize = JsonUtil.getInt(json,"pageSize",5);
        List<Gallery> galleryList = Consumer.getImageService().getGalleryList(userId, pageNum, pageSize);
        if(CollectionUtils.isEmpty(galleryList)){
            return JsonUtil.newFailureJson("result is null!").toString();
        }
        JsonObject retJson = JsonUtil.newSucessJson("sucessful");
        JsonArray ja = ImageLogicService.parseList(galleryList);
        retJson.add("list",ja);
        return retJson.toString();
    }

    @RequestMapping(value="/upload",method=RequestMethod.POST)
    private String fildUpload(@RequestParam(value="file",required=false) MultipartFile[] files,
                              HttpServletRequest request)throws Exception{
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //获得物理路径webapp所在路径
            //String pathRoot = request.getSession().getServletContext().getRealPath("");
            String path="";
            log.debug("fildUpload files.size:"+files.length);
            List<String> filePahtList = new ArrayList();
            for(MultipartFile file : files){
                if(!file.isEmpty()) {
                    //生成uuid作为文件名称
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    //获得文件类型（可以判断如果不是图片，禁止上传）
                    String contentType = file.getContentType();
                    //获得文件后缀名称
                    String imageName = contentType.substring(contentType.indexOf("/") + 1);
                    path = "/data/cdn/" + uuid + "." + imageName;
                    file.transferTo(new File(path));
                    filePahtList.add("http://www.ccpourtoujours.com/static/"+ uuid + "." + imageName);
                }else{
                    log.debug("file is empty");
                }
            }
            JSONArray dataArray = new JSONArray(filePahtList );
            return JsonUtil.newImageRetJson(0,dataArray).toString();
        }catch (Exception e){
            return JsonUtil.newImageRetJson(-1,null).toString();
        }
    }

    @RequestMapping(value="/uploadmobileimg",method=RequestMethod.POST)
    private String fildUploadMoble(@RequestParam(value="wangEditorMobileFile",required=false) MultipartFile[] files,
                              HttpServletRequest request)throws Exception{
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //获得物理路径webapp所在路径
            //String pathRoot = request.getSession().getServletContext().getRealPath("");
            String path="";
            log.debug("fildUpload files.size:"+files.length);
            if(files != null){
                MultipartFile file = files[0];
                if(!file.isEmpty()) {
                    //生成uuid作为文件名称
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    //获得文件类型（可以判断如果不是图片，禁止上传）
                    String contentType = file.getContentType();
                    //获得文件后缀名称
                    String imageName = contentType.substring(contentType.indexOf("/") + 1);
                    path = "/data/cdn/" + uuid + "." + imageName;
                    file.transferTo(new File(path));
                    return "static/"+ uuid + "." + imageName;
                }else{
                    log.debug("file is empty");
                    return "error|上传文件为空";
                }
            }else{
                return "error|上传文件为空";
            }
        }catch (Exception e){
            return "error|上传发生异常";
        }
    }
}
