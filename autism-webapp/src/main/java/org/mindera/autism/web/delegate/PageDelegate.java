package org.mindera.autism.web.delegate;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.*;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.domain.ModuleResponse;
import org.mindera.autism.web.domain.configuration.Module;
import org.mindera.autism.web.domain.configuration.ModuleLocationType;
import org.mindera.autism.web.domain.configuration.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class PageDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageDelegate.class);

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource
    AutismRequestContext requestContext;

    @Value("${server.port}")
    private int port;

    @Value("${module.process.maxExecutionTime}")
    private int maxExecutionTime;

    @Value("${module.process.executionCheckIncrement}")
    private int executionIncrement;


    @Resource
    AsyncListenableTaskExecutor taskExecutor;

    public Map<String, List<ModuleResponse>> process(HttpServletRequest request, String body) throws IOException,
            InterruptedException {
        Long now = System.currentTimeMillis();
        Map<String, List<ModuleResponse>> model = new HashMap<>();
        Map<ModuleResponse, Integer> tempModuleResponses = new HashMap<>();
        List<ModuleResponse> moduleResponses = new ArrayList<>();

        Page page = requestContext.getCurrentPage();

        // prepare the HttpEntity to send to each module
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        HttpEntity requestEntity = getHttpEntity(request, body);

        // create all the module responses with the various callables to
        // be submitted to the task executor
        page.getModules().forEach(
                (layoutSection, modules) -> {
                    model.put(layoutSection, new ArrayList<>());
                    modules.stream().forEach(module -> {
                        ModuleResponse m = new ModuleResponse();
                        m.setStatus(ModuleResponse.Status.LOADING);
                        m.setModule(module);
                        m.setResponse("");
                        m.setCallable(getModuleCallable(method, request.getQueryString(), requestEntity, module));
                        model.get(layoutSection).add(m);
                        tempModuleResponses.put(m, 1);
                    });
                }
        );
        moduleResponses.addAll(tempModuleResponses.keySet());

        // submit all callables to the task executor and collect the futures in a list
        ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(moduleResponses.size() + 1));

        moduleResponses.stream().forEach(mr -> {
            ListenableFuture<ResponseEntity<String>> future = executor.submit(mr.getCallable());
            Futures.addCallback(future, new FutureCallback<ResponseEntity<String>>() {
                @Override
                public void onSuccess(ResponseEntity<String> result) {
                    mr.setStatus(ModuleResponse.Status.SUCCESS);
                    mr.setResponseHeaders(result.getHeaders());
                    mr.setResponse(result.getBody());
                }

                @Override
                public void onFailure(Throwable thrown) {
                    LOGGER.error("Failure to load module [" + mr.getModule().getModule() + "] on URL [" + mr.getModule().getUrl() + "]: " + thrown.getMessage());
                    mr.setStatus(ModuleResponse.Status.FAILED);
                }
            });
        });

        // stops the executor services and waits for all tasks to finish or for the global timeout to be reached
        executor.shutdown();
        executor.awaitTermination(maxExecutionTime, TimeUnit.MILLISECONDS);


        LOGGER.info("Page processing milliseconds: " + (System.currentTimeMillis() - now));

        return model;
    }

    private Callable<ResponseEntity<String>> getModuleCallable(HttpMethod method, String queryString, HttpEntity requestEntity, Module module) {
        Callable<ResponseEntity<String>> callable = () -> {
            RestTemplate rest = new RestTemplate();
            String url = module.getUrl();

            if (module.getLocationType() == ModuleLocationType.LOCAL) {
                url = "http://localhost:" + port + module.getUrl();
            }
            Class<String> responseType = String.class;

            if (isNull(module.getParams())) {
                module.setParams(new HashMap<>());
            }

            if (nonNull(module.getParams())) {
                // pass module specific parameters
                url = url.concat("?").concat(Joiner.on("&").withKeyValueSeparator("=").join(module.getParams()));
            }

            // I'm sure there is a freaking better way of doing this
            if (nonNull(queryString)) {
                if (url.contains("?")) {
                    url = url.concat("&").concat(queryString);
                } else {
                    url = url.concat("?").concat(queryString);
                }
            }

            ResponseEntity<String> response = rest.exchange(url, method, requestEntity, responseType);
            return response;
        };
        return callable;
    }

    /**
     * Calculates the HttpEntity to pass down to each module
     *
     * @param request
     * @return
     * @throws IOException
     */
    private HttpEntity getHttpEntity(HttpServletRequest request, String body) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(h -> headers.add(h, request.getHeader(h)));

        return new HttpEntity(body, headers);
    }
}
