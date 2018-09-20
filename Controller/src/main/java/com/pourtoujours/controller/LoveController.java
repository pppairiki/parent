package com.pourtoujours.controller;

import com.google.gson.JsonObject;
import com.pourtoujours.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoveController {
    public static Logger log = Logger.getLogger(LoveController.class);
    public LoveController(){
    }
    @RequestMapping(value="goLove",method=RequestMethod.POST)
    public String Love(@RequestBody String jsonStr) {
        //角色，用户组权限验证
        JsonObject retJson = JsonUtil.newSucessJson("login sucessful!");
        retJson.addProperty("url","love.html");
        return  retJson.toString();
    }
}
