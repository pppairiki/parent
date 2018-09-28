package com.pourtoujours.controller;

import com.google.gson.JsonObject;
import com.pourtoujours.logic.UserLogicService;
import com.pourtoujours.model.User;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.RedisSession;
import com.pourtoujours.util.SessionUtil;
import com.pourtoujours.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
    public String doLogin(@RequestBody String jsonStr, HttpServletResponse response){
        log.debug("doLogin brench run!");
        JsonObject json = JsonUtil.string2Json(jsonStr);
        if(json == null){
            return JsonUtil.newFailureJson("request json is null!").toString();
        }
        JsonObject retJson = new JsonObject();
        String account = JsonUtil.getString(json,"account");
        String password = JsonUtil.getString(json,"password");
        if(StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)){
            log.debug("doLogin brench account or password is empty!");
            return JsonUtil.newFailureJson(" account or password is empty!").toString();
        }
        User UserObj = UserLogicService.getUserByAccount(account);
        if(UserObj == null){
            log.debug("doLogin brench request user object is null!");
            return JsonUtil.newFailureJson("user object is null!").toString();
        }
        if (UserObj.getPassword().equals(password)) {
            log.debug("doLogin brench password is right!");
            RedisSession redisSession = new RedisSession();
            String a = null;
            redisSession.setAttribute("userId",UserObj.getId());
            redisSession.setAttribute("userName",UserObj.getName());
            redisSession.setAttribute("loginStatus",1);
            redisSession.setAttribute("lastPage","index.html");
            String sid = SessionUtil.getSid();
            SessionUtil.saveSession(sid,redisSession);
            Cookie cookie = new Cookie("sid", sid);
            cookie.setMaxAge(30 * 24 * 60 * 60);  //设置生存期为30天
            cookie.setDomain("www.ccpourtoujours.com");  //子域，在这个子域下才可以访问该Cookie
            //		hit.setPath("/hello");  //在这个路径下面的页面才可以访问该Cookie
            //		hit.setSecure(true);  //如果设置了Secure，则只有当使用https协议连接时cookie才可以被页面访问
            response.addCookie(cookie);
        }
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
