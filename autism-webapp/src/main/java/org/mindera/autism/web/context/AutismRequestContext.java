package org.mindera.autism.web.context;


import com.mindera.microservice.context.RequestContext;
import com.mindera.microservice.security.domain.Role;
import org.mindera.autism.web.domain.configuration.Page;
import org.mindera.autism.web.domain.configuration.Site;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("RequestContext")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AutismRequestContext implements RequestContext {

    private List<Role> roles;
    private Site siteConfiguration;
    private Page currentPage;

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setSiteConfiguration(Site siteConfiguration) {
        this.siteConfiguration = siteConfiguration;
    }

    public Site getSiteConfiguration() {
        return siteConfiguration;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public Page getCurrentPage() {
        return currentPage;
    }
}
