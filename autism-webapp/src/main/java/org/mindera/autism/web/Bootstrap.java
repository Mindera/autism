package org.mindera.autism.web;

import com.google.common.cache.CacheBuilder;
import com.mindera.ams.interceptor.ConfigureAmsClientRequestContext;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.mindera.autism.web.interceptor.ConfigurationInterceptor;
import org.mindera.autism.web.interceptor.RequestContextInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@EnableAsync
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.mindera", "com.mindera"})
public class Bootstrap extends WebMvcConfigurerAdapter {

    @Resource
    ConfigureAmsClientRequestContext configureAmsClientRequestContext;

    @Resource
    ConfigurationInterceptor configurationInterceptor;

    @Resource
    RequestContextInterceptor bouncerInterceptor;

    @Value("${module.request.pool.size}")
    private Integer batchMaxPoolSize;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Bootstrap.class, args);
    }

    @Bean
    public AsyncListenableTaskExecutor asyncListenableTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(batchMaxPoolSize);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Account Management System is available for all requests
        registry.addInterceptor(configureAmsClientRequestContext).addPathPatterns("/**");

        // configuration interceptor is ran for every request except modules
        registry.addInterceptor(configurationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/module/**");

        // the bouncer redirect is available for every request except modules and /login
        registry.addInterceptor(bouncerInterceptor)
                .addPathPatterns("/**");
    }

    @Bean(name = "freemarkerViewResolver")
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setExposeSpringMacroHelpers(true);
        viewResolver.setExposeRequestAttributes(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPaths("classpath:templates", "classpath:modules", "classpath:org/springframework/web/servlet/view/freemarker");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        Configuration configuration = factory.createConfiguration();
        configuration.addAutoImport("spring", "/spring.ftl");
        configuration.addAutoInclude("macros/global.ftl");
        result.setConfiguration(configuration);
        return result;
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // TODO: Move some of this settings to configuration
        GuavaCache amsClientRequestContextCache = new GuavaCache(ConfigureAmsClientRequestContext.AMS_USER_REQUEST_CONTEXT_CACHE,
                CacheBuilder.newBuilder()
                .expireAfterWrite(3600, TimeUnit.SECONDS)
                .maximumSize(10000)
                .build());

        cacheManager.setCaches(Arrays.asList(
                amsClientRequestContextCache));

        return cacheManager;
    }
}

