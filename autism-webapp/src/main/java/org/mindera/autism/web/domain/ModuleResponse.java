package org.mindera.autism.web.domain;

import org.mindera.autism.web.domain.configuration.Module;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;


public class ModuleResponse {

    private ListenableFuture<ResponseEntity<String>> future;

    public void setFuture(ListenableFuture<ResponseEntity<String>> future) {
        this.future = future;
    }

    public ListenableFuture<ResponseEntity<String>> getFuture() {
        return future;
    }

    public enum Status {
        LOADING,
        CANCELLED,
        FAILED,
        SUCCESS
    }

    private Status status;
    private HttpHeaders responseHeaders;
    private Module module;
    private Callable<ResponseEntity<String>> callable;
    private String response;

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setResponseHeaders(HttpHeaders responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Callable<ResponseEntity<String>> getCallable() {
        return callable;
    }

    public void setCallable(Callable<ResponseEntity<String>> callable) {
        this.callable = callable;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}