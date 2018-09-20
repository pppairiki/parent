package com.pourtoujours.mvc;

import com.google.gson.JsonObject;
import com.pourtoujours.logic.UserLogicService;
import com.pourtoujours.model.User;
import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.RedisSession;
import com.pourtoujours.util.SessionUtil;
import com.pourtoujours.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 登陆态校验拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    public static Logger log = Logger.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
       try{
           String uri = request.getRequestURI();
           if("/".equals(uri)){
               return true;
           } else if (uri.contains(".")){
               return true;
           }else if("/doLogin".equals(uri)){
               log.debug("doLogin brench run!");
               JsonObject json = JsonUtil.string2Json(getBodyString(request));
               if(json == null){
                   log.debug("doLogin brench request json is null!");
                   return false;
               }
               JsonObject retJson = new JsonObject();
               String account = JsonUtil.getString(json,"account");
               String password = JsonUtil.getString(json,"password");
               if(StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)){
                   log.debug("doLogin brench account or password is empty!");
                   return false;
               }
               User UserObj = UserLogicService.getUserByAccount(account);
               if(UserObj == null){
                   log.debug("doLogin brench request user object is null!");
                   return false;
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
                   return true;
               }
               log.debug("doLogin brench failed!");
               return false;
           }else if("/logout".equals(uri)){
               Cookie[] cookies = request.getCookies();
               Cookie sidCookie = null;
               for(Cookie cookie : cookies){
                   if("sid".equals(cookie.getName())){
                       sidCookie = cookie;
                       break;
                   }
               }
               if(sidCookie == null){
                   response.setContentType("application/json");
                   response.setCharacterEncoding("UTF-8");
                   response.getWriter().print( JsonUtil.newSucessJson("sidCookie is null").toString());
                   response.getWriter().flush();
                   return  false;
               }
               RedisSession session = SessionUtil.getSession(sidCookie.getValue());
               if(session == null){
                   response.setContentType("application/json");
                   response.setCharacterEncoding("UTF-8");
                   response.getWriter().print( JsonUtil.newSucessJson("session is null").toString());
                   response.getWriter().flush();
                   return  false;
               }
               session.setAttribute("loginStatus",2);
               SessionUtil.saveSession(sidCookie.getValue(),session);
               response.setContentType("application/json");
               response.setCharacterEncoding("UTF-8");
               response.getWriter().print( JsonUtil.newSucessJson("normal logout").toString());
               response.getWriter().flush();
               return  false;
           }else{
               Cookie[] cookies = request.getCookies();
               Cookie sidCookie = null;
               for(Cookie cookie : cookies){
                   if("sid".equals(cookie.getName())){
                       sidCookie = cookie;
                       break;
                   }
               }
               if(sidCookie == null){
                   response.setContentType("application/json");
                   response.setCharacterEncoding("UTF-8");
                   response.getWriter().print( JsonUtil.newFailureJson("please login first!").toString());
                   response.getWriter().flush();
                   return  false;
               }
               RedisSession session = SessionUtil.getSession(sidCookie.getValue());
               if(session == null){
                   response.setContentType("application/json");
                   response.setCharacterEncoding("UTF-8");
                   response.getWriter().print( JsonUtil.newFailureJson("login status is out of time,please login again!").toString());
                   response.getWriter().flush();
                   return  false;
               }
               int loginStatus = (int)session.getAttribute("loginStatus");
               if(loginStatus != 1){
                   response.setContentType("application/json");
                   response.setCharacterEncoding("UTF-8");
                   response.getWriter().print( JsonUtil.newFailureJson("the user has logout,please login again!").toString());
                   response.getWriter().flush();
                   return  false;
               }
               request.setAttribute("userId",session.getAttribute("userId"));
               request.setAttribute("userName",session.getAttribute("userName"));
               return true;
           }
       }catch (Exception e){
           log.debug("preHandle exception:",e);
            return false;
       }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
  /*  *
     * 获取请求Body
     *
     * @param request
     * @return*/

    public static String getBodyString(final HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

/*    *
     * Description: 复制输入流</br>
     *
     * @param inputStream
     * @return</br>*/

    public static InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }
}

