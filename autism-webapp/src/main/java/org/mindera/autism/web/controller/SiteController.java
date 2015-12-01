package org.mindera.autism.web.controller;


import com.mindera.microservice.controller.ApiController;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

abstract public class SiteController extends ApiController {

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    public ResponseEntity<String> processTemplate(String templateName, Object model) throws IOException, TemplateException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        Writer out = new StringWriter();
        template.process(model, out);
        out.flush();
        return getResponseEntity(out.toString(), HttpStatus.OK);
    }
}
