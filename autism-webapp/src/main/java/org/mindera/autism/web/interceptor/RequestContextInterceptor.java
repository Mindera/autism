package org.mindera.autism.web.interceptor;

import com.mindera.ams.context.AmsClientRequestContext;
import com.mindera.microservice.context.RequestContext;
import com.mindera.microservice.security.domain.Role;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.domain.mapping.UrlMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Component
public class RequestContextInterceptor implements HandlerInterceptor {

    @Resource
    AmsClientRequestContext amsContext;

    @Resource
    AutismRequestContext requestContext;

    @Resource
    private ServletContext context;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {


        requestContext.setRoles(new ArrayList<>());
        requestContext.getRoles().add(Role.VISITOR);
        // check if there is a user authenticated on this request

        if (nonNull(amsContext.getUser())) {
            requestContext.getRoles().add(Role.AUTHENTICATED);
        }

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