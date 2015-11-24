package org.mindera.autism.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.domain.configuration.Module;
import org.mindera.autism.web.domain.configuration.ModuleLocationType;
import org.mindera.autism.web.domain.configuration.Page;
import org.mindera.autism.web.domain.configuration.Site;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Component
public class ConfigurationInterceptor implements HandlerInterceptor {

    @Resource
    AutismRequestContext requestContext;

    @Resource
    ObjectMapper objectMapper;

    @Value("${site.configuration}")
    private String siteConfigurationFile;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        // TODO: Implement mechanism to validate the integrity of the site configuration file

        Site siteConfiguration = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream(siteConfigurationFile), Site.class);
        String path = httpServletRequest.getRequestURI();
        requestContext.setSiteConfiguration(siteConfiguration);

        requestContext.setCurrentPage(siteConfiguration.getDefaultPage());
        if (siteConfiguration.getSitemap().containsKey(path)) {
            requestContext.setCurrentPage(siteConfiguration.getSitemap().get(path));
        }

        Page currentPage = requestContext.getCurrentPage();

        if (siteConfiguration.getPages().containsKey(requestContext.getCurrentPage().getPage())) {
            Page master = siteConfiguration.getPages().get(requestContext.getCurrentPage().getPage());
            if (currentPage.getLayout() == null) {
                currentPage.setLayout(master.getLayout());
            }
            if (currentPage.getModules() == null) {
                currentPage.setModules(master.getModules());
            }
        }

        currentPage.getModules().forEach(
                (layoutSection, modules) -> modules.stream().map(module -> {
                    Module masterModule = siteConfiguration.getModules().get(module.getModule());

                    if (module.getUrl() == null) {
                        module.setUrl(masterModule.getUrl());
                    }

                    if (module.getParams() == null) {
                        module.setParams(masterModule.getParams());
                    }

                    if (module.getLocationType() == null) {
                        module.setLocationType(masterModule.getLocationType() == null ? ModuleLocationType.LOCAL : masterModule.getLocationType());
                    }

                    return module;
                }).collect(Collectors.toList())
        );

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}