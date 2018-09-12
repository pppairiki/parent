package com.pourtoujours.mvc;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



/**
 * 过滤器把请求流保存起来
 *
 */
public class MVCFilter implements Filter{


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }


}
