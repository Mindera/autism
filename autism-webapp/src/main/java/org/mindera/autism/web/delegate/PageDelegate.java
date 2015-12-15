package org.mindera.autism.web.delegate;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
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
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        Map<String, List<ModuleResponse>> model = new HashMap<>();
        Map<ModuleResponse, Integer> tempModuleResponses = new HashMap<>();
        List<ModuleResponse> moduleResponses = new ArrayList<>();

        Page page = requestContext.getCurrentPage();

        // prepare the HttpEntity to send to each module
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        HttpEntity requestEntity = getHttpEntity(request, body);

        // create all the futures
        page.getModules().forEach(
                (layoutSection, modules) -> {
                    model.put(layoutSection, new ArrayList<>());
                    modules.stream().forEach(module -> {
                        ModuleResponse m = getModuleResponse(method, request.getParameterMap(), requestEntity, module);
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
                    LOGGER.error("Max module execution time reached for "
                            .concat(mr.getModule().getModule()
                                    .concat(" on page ")
                                    .concat(page.getPage())));
                });
            }
        }

        return model;
    }

    private ModuleResponse getModuleResponse(HttpMethod method, Map<String, String[]> parameterMap, HttpEntity requestEntity, Module module) {
        AsyncRestTemplate asycTemp = new AsyncRestTemplate(taskExecutor);
        String url = module.getUrl();

        if (module.getLocationType() == ModuleLocationType.LOCAL) {
            url = "http://localhost:" + port + module.getUrl();
        }
        Class<String> responseType = String.class;

        if (isNull(module.getParams())) {
            module.setParams(new HashMap<>());
        }

        if (nonNull(parameterMap)) {
            // pass the received page parameters to each module http endpoint
            parameterMap.forEach((k, v) -> module.getParams().put(k, Joiner.on(",").join(v)));

        }

        if (nonNull(module.getParams())) {
            // pass module specific parameters
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
                LOGGER.warn("Module failed to load: ".concat(m.getModule().getModule()));
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
    private HttpEntity getHttpEntity(HttpServletRequest request, String body) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(h -> headers.add(h, request.getHeader(h)));

        return new HttpEntity(body, headers);
    }
}
