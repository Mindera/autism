package org.mindera.autism.web.delegate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.domain.ModuleResponse;
import org.mindera.autism.web.domain.configuration.Module;
import org.mindera.autism.web.domain.configuration.ModuleLocationType;
import org.mindera.autism.web.domain.configuration.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PageDelegate {

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Resource
    AutismRequestContext requestContext;

    @Value("${server.port}")
    private int port;

    @Value("${modules.process.maxExecutionTime:500}")
    private int maxExecutionTime;

    @Value("${modules.process.executionCheckIncrement:10}")
    private int executionIncrement;


    @Resource
    AsyncListenableTaskExecutor taskExecutor;

    public Map<String, List<ModuleResponse>> process(HttpServletRequest request) throws IOException, InterruptedException {
        Map<String, List<ModuleResponse>> model = new HashMap<>();
        Map<ModuleResponse, Integer> tempModuleResponses = new HashMap<>();
        List<ModuleResponse> moduleResponses = new ArrayList<>();

        Page page = requestContext.getCurrentPage();

        // prepare the HttpEntity to send to each module
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        HttpEntity requestEntity = getHttpEntity(request);

        // create all the futures
        page.getModules().forEach(
                (layoutSection, modules) -> {
                    model.put(layoutSection, new ArrayList<>());
                    modules.stream().forEach(module -> {
                        ModuleResponse m = getModuleResponse(method, requestEntity, module);
                        model.get(layoutSection).add(m);
                        tempModuleResponses.put(m, 1);
                    });
                }
        );
        moduleResponses.addAll(tempModuleResponses.keySet());

        int time = 0;
        boolean done = false;

        // wait for a certain time for all futures to complete.
        // cancel any future that does not complete within the correct time
        // TODO: Replace this with a proper implementation (Maybe using an ExecutorService)
        while (time < maxExecutionTime && !done) {
            Thread.sleep(executionIncrement);
            time += executionIncrement;

            moduleResponses = moduleResponses.stream().filter(mr -> mr.getResponse() == null).collect(Collectors.toList());

            if (moduleResponses.isEmpty()) {
                done = true;
            } else if (time >= maxExecutionTime) {
                moduleResponses.forEach(mr -> {
                    mr.getFuture().cancel(true);
                    mr.setStatus(ModuleResponse.Status.FAILURE_TO_LOAD);
                });
            }
        }

        System.out.println("TIME---" + time);
        return model;
    }

    private ModuleResponse getModuleResponse(HttpMethod method, HttpEntity requestEntity, Module module) {
        AsyncRestTemplate asycTemp = new AsyncRestTemplate(taskExecutor);
        String url = module.getUrl();

        if (module.getLocationType() == ModuleLocationType.LOCAL) {
            url = "http://localhost:" + port + module.getUrl();
        }
        Class<String> responseType = String.class;

        if (module.getParams() != null) {
            url = url.concat("?").concat(Joiner.on("&").withKeyValueSeparator("=").join(module.getParams()));
        }

        ListenableFuture<ResponseEntity<String>> future = asycTemp.exchange(url, method, requestEntity, responseType);
        ModuleResponse m = new ModuleResponse();
        m.setModule(module);
        m.setFuture(future);
        future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                m.setStatus(ModuleResponse.Status.FAILURE_TO_LOAD);
            }

            @Override
            public void onSuccess(ResponseEntity<String> stringResponseEntity) {
                m.setResponse(stringResponseEntity.getBody());
                m.setStatus(ModuleResponse.Status.SUCCESS);
            }
        });
        return m;
    }

    /**
     * Calculates the HttpEntity to pass down to each module
     *
     * @param request
     * @return
     * @throws IOException
     */
    private HttpEntity getHttpEntity(HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(h -> headers.add(h, request.getHeader(h)));
        StringBuffer body = new StringBuffer();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        return new HttpEntity(body, headers);
    }
}
