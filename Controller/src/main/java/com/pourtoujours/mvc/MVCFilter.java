package com.pourtoujours.mvc;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 * 过滤器把请求流保存起来
 *
 */
public class MVCFilter implements Filter{
    public static Logger log = Logger.getLogger(MVCFilter.class);
    // 用于创建MultipartHttpServletRequest
    private CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // 注入bean
    }


    private ServletRequest getRequest(ServletRequest req){
        String enctype = req.getContentType();
        if (StringUtils.isNotBlank(enctype) && enctype.contains("multipart/form-data")){
            // 返回 MultipartHttpServletRequest 用于获取 multipart/form-data 方式提交的请求中 上传的参数
            log.debug("multipartResolver convert!");
            return multipartResolver.resolveMultipart((HttpServletRequest) req);
        }else{
            return req;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        /*HttpServletRequest httpServletRequest = (HttpServletRequest) getRequest(request);
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
        chain.doFilter(getRequest(requestWrapper), response);*/
        chain.doFilter(getRequest(request), response);
    }

    @Override
    public void destroy() {

    }

}
