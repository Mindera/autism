package org.mindera.autism.web.controller;

import com.mindera.microservice.controller.ApiController;
import com.mindera.microservice.controller.ApiResponse;
import com.mindera.microservice.security.annotation.Authorize;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.delegate.PageDelegate;
import org.mindera.autism.web.domain.ModuleResponse;
import org.mindera.autism.web.domain.mapping.UrlMappings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(UrlMappings.GENERIC_PAGE)
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
