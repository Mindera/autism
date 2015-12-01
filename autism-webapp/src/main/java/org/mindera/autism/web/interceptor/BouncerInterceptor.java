package org.mindera.autism.web.interceptor;

import com.mindera.ams.context.AmsClientRequestContext;
import org.mindera.autism.web.domain.mapping.UrlMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BouncerInterceptor implements HandlerInterceptor {

    @Resource
    AmsClientRequestContext amsContext;

    @Resource
    private ServletContext context;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        // check if there is a user authenticated on this request

//        if (amsContext.getUser() == null) {
//            String contextPath = context.getContextPath();
//            httpServletResponse.sendRedirect(contextPath + UrlMapping.LOGIN);
//            return false;
//        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o,
                           ModelAndView modelAndView) throws Exception {
        // do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o,
                                Exception e) throws Exception {
        // do nothing
    }
}