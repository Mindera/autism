package org.mindera.autism.web.module.credits;


import com.mindera.microservice.controller.ApiResponse;
import com.mindera.microservice.security.annotation.Authorize;
import com.mindera.microservice.security.domain.Role;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(ModuleUrlMapping.MODULE_CREDITS)
public class CreditsController extends SiteController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @Authorize(role = {Role.VISITOR})
    public ApiResponse module() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate("modules/credits/credits.ftl", null);
            }
        };
    }
}
