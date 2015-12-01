package org.mindera.autism.web;

import com.mindera.ams.interceptor.ConfigureAmsClientRequestContext;
import freemarker.template.TemplateException;
import org.mindera.autism.web.interceptor.ConfigurationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

import javax.annotation.Resource;
import java.io.IOException;

@EnableAsync
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.mindera", "com.mindera"})
public class Bootstrap extends WebMvcConfigurerAdapter {

    @Resource
    ConfigureAmsClientRequestContext configureAmsClientRequestContext;

    @Resource
    ConfigurationInterceptor configurationInterceptor;

//    @Resource
//    BouncerInterceptor bouncerInterceptor;

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
//        registry.addInterceptor(bouncerInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/module/**")
//                .excludePathPatterns("/login");
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPaths("classpath:templates", "classpath:modules");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setConfiguration(factory.createConfiguration());
        return result;
    }
}

