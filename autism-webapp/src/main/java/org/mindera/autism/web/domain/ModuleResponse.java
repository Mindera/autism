package org.mindera.autism.web.domain;

import org.mindera.autism.web.domain.configuration.Module;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;


public class ModuleResponse {

    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        FAILURE_TO_LOAD,
        SUCCESS
    };


    private Module module;
    private ListenableFuture<ResponseEntity<String>> future;
    private String response;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public ListenableFuture<ResponseEntity<String>> getFuture() {
        return future;
    }

    public void setFuture(ListenableFuture<ResponseEntity<String>> future) {
        this.future = future;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}