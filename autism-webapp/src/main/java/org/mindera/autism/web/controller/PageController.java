package org.mindera.autism.web.controller;

import com.mindera.microservice.controller.ApiResponse;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.delegate.PageDelegate;
import org.mindera.autism.web.domain.mapping.UrlMapping;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class PageController extends SiteController implements ErrorController {

    @Resource
    PageDelegate pageDelegate;

    @Resource
    AutismRequestContext requestContext;

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping(value = UrlMapping.GENERIC_PAGE, method = RequestMethod.GET)
    public ApiResponse get(HttpServletRequest request) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate(requestContext.getCurrentPage().getLayout(), (Map) pageDelegate.process(request, null));
            }
        };
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {

        return "layout/error";
    }

    @RequestMapping(value = UrlMapping.GENERIC_PAGE, method = RequestMethod.POST)
    public ApiResponse post(HttpServletRequest request, @RequestBody String body) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate(requestContext.getCurrentPage().getLayout(), (Map) pageDelegate.process(request, body));
            }
        };
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
