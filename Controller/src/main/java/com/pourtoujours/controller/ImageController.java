package com.pourtoujours.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pourtoujours.base.Consumer;
import com.pourtoujours.logic.ImageLogicService;
import com.pourtoujours.model.Gallery;
import com.pourtoujours.util.JsonUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ImageController {
    @RequestMapping(value="getGalleryList",method= RequestMethod.POST)
    public String getGalleryList(@RequestBody String jsonStr,HttpServletRequest request){
        JsonObject json = JsonUtil.string2Json(jsonStr);
        if(json == null){
            return JsonUtil.newFailureJson("request json is null!").toString();
        }
        int userId = (int)request.getAttribute("userId");
        int pageNum = JsonUtil.getInt(json,"pageNum",0);
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
}
