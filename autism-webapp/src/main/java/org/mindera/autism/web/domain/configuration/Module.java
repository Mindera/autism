package org.mindera.autism.web.domain.configuration;

import java.util.Map;


public class Module {

    private String module;

    private ModuleLocationType locationType;

    private String url;

    private Map<String, String> params;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public ModuleLocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(ModuleLocationType locationType) {
        this.locationType = locationType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
