package org.mindera.autism.web.module.user;


import com.mindera.microservice.controller.ApiResponse;
import org.mindera.autism.web.context.AutismRequestContext;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.mindera.autism.web.module.login.LoginDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(ModuleUrlMapping.MODULE_USER)
public class UserController extends SiteController {

    @Resource
    AutismRequestContext requestContext;

    @Resource
    LoginDelegate loginDelegate;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResponse module() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                Map<String, Object> model = new HashMap<>();
                model.put("user", requestContext.getUser());
                model.put("patientList", Collections.emptyList());
                if (requestContext.getUser() != null) {
                    model.put("patientList", loginDelegate.getAccountList(requestContext.getUser().getSession()));
                }
                return processTemplate("modules/user/user.ftl", model);
            }
        };
    }
}
