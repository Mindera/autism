package org.mindera.autism.web.controller;

import com.mindera.microservice.controller.ApiResponse;
import freemarker.template.TemplateException;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.delegate.PageDelegate;
import org.mindera.autism.web.domain.ModuleResponse;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PageController extends SiteController implements ErrorController {

    public static final String SET_COOKIE_HEADER = "Set-Cookie";
    @Resource
    PageDelegate pageDelegate;

    @Resource
    AutismRequestContext requestContext;

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping(value = UrlMapping.GENERIC_PAGE, method = RequestMethod.GET)
    public ApiResponse get(HttpServletRequest request, HttpServletResponse response) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processPageModules(request, null, response);
            }
        };
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {

        return "layout/error";
    }

    @RequestMapping(value = UrlMapping.GENERIC_PAGE, method = RequestMethod.POST)
    public ApiResponse post(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processPageModules(request, body, response);
            }
        };
    }

    private ResponseEntity<String> processPageModules(HttpServletRequest request, String body, HttpServletResponse response) throws IOException, InterruptedException, TemplateException {
        Map<String, List<ModuleResponse>> modules = pageDelegate.process(request, body);
        proxyHeaders(modules, response);
        return processTemplate(requestContext.getCurrentPage().getLayout(), (Map) modules);
    }


    private void proxyHeaders(Map<String, List<ModuleResponse>> modules, HttpServletResponse response) {
        modules.forEach((layoutSection, moduleList) -> moduleList.stream().forEach( module -> {
            // forward set-cookie header
            if (module.getResponseHeaders() != null && module.getResponseHeaders().get(SET_COOKIE_HEADER) != null) {
                module.getResponseHeaders().get(SET_COOKIE_HEADER).stream().forEach(cookieValue ->
                        response.addHeader(SET_COOKIE_HEADER, cookieValue
                        ));
            }
        }));
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
