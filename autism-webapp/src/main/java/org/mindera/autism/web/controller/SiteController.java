package org.mindera.autism.web.controller;


import com.mindera.microservice.controller.ApiController;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

abstract public class SiteController extends ApiController {

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    public ResponseEntity<String> processTemplate(String templateName, Map<String, Object> model) throws IOException, TemplateException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        Writer out = new StringWriter();
        if (isNull(model)) {
            model = new HashMap<>();
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        model.put("springMacroRequestContext", new RequestContext(request, null, null, null));
        model.put("parameterMap", request.getParameterMap());
        template.process(model, out);
        out.flush();
        return getResponseEntity(out.toString(), HttpStatus.OK);
    }
}
