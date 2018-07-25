package com.pourtoujours.controller;
import com.pourtoujours.logic.UserLogicService;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.StringUtil;
import com.google.gson.JsonObject;
import com.pourtoujours.model.User;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ControllerUser {

    public ControllerUser() {
    }

    public static Logger log = Logger.getLogger(ControllerUser.class);

    @RequestMapping(value="getUser", method = RequestMethod.GET)
    public User getUser(){
        int id = 100001;
        if(id == 100001){
            User user = new User();
            user.setId(id);
            user.setName("崔立强");
            return user;
        }else{
            return null;
        }
    }

    @RequestMapping(value="doLogin",method=RequestMethod.POST)
    public String doLogin(@RequestBody String jsonStr){
        JsonObject json = JsonUtil.string2Json(jsonStr);
        JsonObject retJson = new JsonObject();
        if(json == null){
            return JsonUtil.newFailureJson("request json is null!").toString();
        }
        String account = JsonUtil.getString(json,"account");
        String password = JsonUtil.getString(json,"password");
        if(StringUtil.isNullOrEmpty(account)){
            return JsonUtil.newFailureJson("please type in account!").toString();
        }
        if(StringUtil.isNullOrEmpty(password)){
            return JsonUtil.newFailureJson("please type in password!").toString();
        }
        return UserLogicService.login(account,password).toString();
    }

    @RequestMapping(value="doSignUp",method=RequestMethod.POST)
    public String doSignUp(@RequestBody String jsonStr) {
        JsonObject json = JsonUtil.string2Json(jsonStr);
        JsonObject retJson = new JsonObject();
        if (json == null) {
            return JsonUtil.newFailureJson("request json is null!").toString();
        }
        String name = JsonUtil.getString(json, "name");
        String account = JsonUtil.getString(json, "account");
        String password = JsonUtil.getString(json, "password");
        if (StringUtil.isNullOrEmpty(name)) {
            return JsonUtil.newFailureJson("please type in name!").toString();
        }
        if (StringUtil.isNullOrEmpty(account)) {
            return JsonUtil.newFailureJson("please type in account!").toString();
        }
        if (StringUtil.isNullOrEmpty(password)) {
            return JsonUtil.newFailureJson("please type in password!").toString();
        }
        User userObj = new User();
        userObj.setName(name);
        userObj.setAccount(account);
        userObj.setPassword(password);
        int ret = UserLogicService.signUp(userObj);
        if (ret > 0) {
            return JsonUtil.newSucessJson("sucessful").toString();
        } else {
            return JsonUtil.newFailureJson("sign up failure!").toString();
        }
    }

    public static  void main(String[] args){
        log.debug("doLogin request json:");
    }

}
