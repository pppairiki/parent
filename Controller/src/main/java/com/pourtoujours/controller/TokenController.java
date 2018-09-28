package com.pourtoujours.controller;

import com.google.gson.JsonObject;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    public static Logger log = Logger.getLogger(TokenController.class);
    @RequestMapping(value="getRequestToken",method= RequestMethod.GET)
    public String updateFileContent() {
        try{
            JsonObject retJson = JsonUtil.newSucessJson("sucessful");
            String token = TokenUtil.getRequestToken();
            retJson.addProperty("reqtoken",token);
            return retJson.toString();
        }catch (Exception e){
            log.debug("getRequestToken exception:",e);
            return JsonUtil.newFailureJson("getRequestToken failure!").toString();
        }
    }
}
