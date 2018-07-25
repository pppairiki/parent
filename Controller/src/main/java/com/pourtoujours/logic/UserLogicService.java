package com.pourtoujours.logic;

import com.google.gson.JsonObject;
import com.pourtoujours.api.IUserService;
import com.pourtoujours.base.Consumer;
import com.pourtoujours.model.User;
import com.pourtoujours.util.JWTUtil;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.StringUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.util.Date;

public class UserLogicService {
    public static Logger log = Logger.getLogger(UserLogicService.class);

    private static IUserService userService = Consumer.getUserService();

    public static int signUp(User userObj) {
        log.debug("UserLogicService.signUp run!");
       return userService.signUp(userObj);
    }

    public static User getUserById(int id){
        return userService.getUserById(id);
    }

    public static User getUserByAccount(String account){
        return userService.getUserByAccount(account);
    }

    public static JsonObject login(String account, String password) {
        if(StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)){
            return JsonUtil.newFailureJson("account or password is null!");
        }
        User userObj = getUserByAccount(account);
        if(userObj == null){
            return JsonUtil.newFailureJson("account is not exists!");
        }
        if (userObj.getPassword().equals(password)) {
            JsonObject retJson = JsonUtil.newSucessJson("login sucessful!");
            String token = JWTUtil.getInstance().buildJwt(DateUtils.addHours(new Date(),1),userObj.getId(),userObj.getAccount());
            if (token != null) {
                retJson.addProperty("token",token);
            }
            userObj.setPassword("");
            retJson.add("user",JsonUtil.parseObject2Json(userObj));
            return retJson;
        }
        return JsonUtil.newFailureJson("password is wrong!");
    }

    public static int isLogin( String account, String token){
        return JWTUtil.getInstance().isJwtValid(token,account);
    }
}
