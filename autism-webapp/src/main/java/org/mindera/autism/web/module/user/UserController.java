package org.mindera.autism.web.module.user;


import com.mindera.microservice.controller.ApiResponse;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(ModuleUrlMapping.MODULE_USER)
public class UserController extends SiteController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ApiResponse module() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate("modules/user/user.ftl", null);
            }
        };
    }
}
