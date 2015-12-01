package org.mindera.autism.web.domain.configuration;

import java.util.Map;

public class Site {
    private Page defaultPage;
    private Map<String, Page> pages;
    private Map<String, Module> modules;
    private Map<String, Page> sitemap;

    public Map<String, Page> getPages() {
        return pages;
    }

    public void setPages(Map<String, Page> pages) {
        this.pages = pages;
    }

    public Map<String, Module> getModules() {
        return modules;
    }

    public void setModules(Map<String, Module> modules) {
        this.modules = modules;
    }

    public Map<String, Page> getSitemap() {
        return sitemap;
    }

    public void setSitemap(Map<String, Page> sitemap) {
        this.sitemap = sitemap;
    }

    public Page getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(Page defaultPage) {
        this.defaultPage = defaultPage;
    }
}
