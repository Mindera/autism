package org.mindera.autism.web.module.navigation;


import com.mindera.microservice.controller.ApiResponse;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(ModuleUrlMapping.MODULE_NAVIGATION)
public class NavigationController extends SiteController {

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse module(final HttpServletRequest request) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate("modules/navigation/" + request.getParameter("type") + ".ftl", null);
            }
        };
    }
}
