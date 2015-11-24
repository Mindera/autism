package org.mindera.autism.web.domain.configuration;

import java.util.List;
import java.util.Map;

/**
 * Created by fonsecaj on 22/11/15.
 */
public class Page {

    private String page;
    private String layout;
    private Map<String, List<Module>> modules;

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
}
