package org.mindera.autism.web.controller;

import com.mindera.microservice.controller.ApiResponse;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.delegate.PageDelegate;
import org.mindera.autism.web.domain.mapping.UrlMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(UrlMapping.GENERIC_PAGE)
public class PageController extends SiteController {

    @Resource
    PageDelegate pageDelegate;

    @Resource
    AutismRequestContext requestContext;

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse page(HttpServletRequest request) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate(requestContext.getCurrentPage().getLayout(), pageDelegate.process(request));
            }
        };
    }
}
