package com.pourtoujours.mvc;

import com.pourtoujours.util.JsonUtil;
import com.pourtoujours.util.RedisSession;
import com.pourtoujours.util.SessionUtil;
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
                   return true;
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
               //更新session
               session.setAttribute("lastPage",uri);
               SessionUtil.saveSession(sidCookie.getValue(),session);
               //从session获取用户信息
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

