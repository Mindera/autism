package org.mindera.autism.web.domain.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Page {

    private String page;
    private String layout;
    private Map<String, List<Module>> modules;
    private String parent;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Map<String, List<Module>> getModules() {
        return modules;
    }

    public void setModules(Map<String, List<Module>> modules) {
        this.modules = modules;
    }


    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
