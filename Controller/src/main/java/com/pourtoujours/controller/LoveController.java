package com.pourtoujours.controller;

import com.google.gson.JsonObject;
import com.pourtoujours.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoveController {
    public static Logger log = Logger.getLogger(LoveController.class);
    public LoveController(){
    }
    @RequestMapping(value="goLove",method=RequestMethod.POST)
    public String Love(@RequestBody String jsonStr,HttpServletRequest request) {
        //角色，用户组权限验证
        int userId = (int)request.getAttribute("userId");
        if(userId != 1 && userId != 2){
            JsonObject retJson = JsonUtil.newFailureJson("you are not limited to visit this page!");
            retJson.addProperty("url","index.html");
            return  retJson.toString();
        }
        JsonObject retJson = JsonUtil.newSucessJson("check sucessful!");
        retJson.addProperty("url","love.html");
        return  retJson.toString();
    }
}
