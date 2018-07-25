package com.pourtoujours.controller;

import com.google.gson.JsonObject;
import com.pourtoujours.logic.UserLogicService;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerLove {
    public static Logger log = Logger.getLogger(ControllerLove.class);
    public ControllerLove(){
    }
    @RequestMapping(value="goLove",method=RequestMethod.POST)
    public String Love(@RequestBody String jsonStr) {
        JsonObject json = JsonUtil.string2Json(jsonStr);
        if(json == null){
            return JsonUtil.newFailureJson("request json is null").toString();
        }
        log.debug("controllerLover goLove run! request json is:"+jsonStr);
        String account = JsonUtil.getString(json,"account");
        String token = JsonUtil.getString(json,"token");
        if(StringUtil.isNullOrEmpty(account)){
            return JsonUtil.newFailureJson("You need login!").toString();
        }
        int userId = UserLogicService.isLogin(account,token);
        if(userId < 0){
            return JsonUtil.newFailureJson("You need login!").toString();
        }else{
            //角色，用户组权限验证
            JsonObject retJson = JsonUtil.newSucessJson("login sucessful!");
            retJson.addProperty("url","love.html");
            return  retJson.toString();
        }
    }
}
