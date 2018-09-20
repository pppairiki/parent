package com.pourtoujours.controller;

import com.google.gson.JsonObject;
import com.pourtoujours.logic.UserLogicService;
import com.pourtoujours.model.User;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public UserController() {
    }

    public static Logger log = Logger.getLogger(UserController.class);

    @RequestMapping(value="getUser", method = RequestMethod.GET)
    public User getUser(){
        int id = 100001;
        if(id == 100001){
            User User = new User();
            User.setId(id);
            User.setName("崔立强");
            return User;
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
        User UserObj = new User();
        UserObj.setName(name);
        UserObj.setAccount(account);
        UserObj.setPassword(password);
        int ret = UserLogicService.signUp(UserObj);
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
