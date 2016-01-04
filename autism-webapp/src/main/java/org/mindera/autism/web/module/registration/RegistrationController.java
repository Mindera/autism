package org.mindera.autism.web.module.registration;


import com.mindera.microservice.controller.ApiResponse;
import com.mindera.udb.client.UdbClient;
import com.mindera.udb.domain.Details;
import com.mindera.udb.domain.User;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping(ModuleUrlMapping.MODULE_REGISTRATION)
public class RegistrationController extends SiteController {

    @Resource
    RegistrationDelegate registrationDelegate;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse get() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate("modules/registration/registration.ftl", null);
            }
        };
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse post(@ModelAttribute RegistrationForm registrationForm, HttpServletRequest request) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                Details user = registrationDelegate.create(registrationForm);
                Map<String, Object> model = new HashMap<>();

                if (nonNull(user)) {
                    model.put("user", user);
                    return processTemplate("modules/registration/confirmed.ftl", model);
                } else {
                    model.put("form", registrationForm);
                    return processTemplate("modules/registration/registration.ftl", model);
                }
            }
        };
    }
}
