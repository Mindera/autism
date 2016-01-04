package org.mindera.autism.web.module.patient;


import com.mindera.ams.domain.Account;
import com.mindera.microservice.controller.ApiResponse;
import com.mindera.microservice.security.annotation.Authorize;
import com.mindera.microservice.security.domain.Role;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping(ModuleUrlMapping.MODULE_PATIENT)
public class PatientController extends SiteController {


    public static final String ACCOUNT_ID_HEADER = "M-Account-Id";
    @Resource
    PatientDelegate patientDelegate;

    @RequestMapping(method = RequestMethod.GET)
    @Authorize(role = Role.VISITOR)
    public ApiResponse get() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                return processTemplate("modules/patient/createform.ftl", null);
            }
        };
    }

    @RequestMapping(value = "/switch/{id}", method = RequestMethod.GET)
    @Authorize(role = Role.AUTHENTICATED)
    public ApiResponse switchPatient(@PathVariable Long id,
                                     HttpServletResponse response) {
        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                Cookie accountCookie = new Cookie(ACCOUNT_ID_HEADER, String.valueOf(id));
                accountCookie.setPath("/");
                response.addCookie(accountCookie);
                response.addHeader("Location", "/");
                return getResponseEntity("", HttpStatus.FOUND);
            }
        };
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse post(@ModelAttribute CreatePatientForm form,
                            @CookieValue(value = "M-SSO", defaultValue = "") String sessionToken,
                            HttpServletResponse response) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                Map<String, Object> model = new HashMap<>();

                Account account = patientDelegate.create(form, sessionToken);

                if (nonNull(account)) {
                    model.put("account", account);
                    Cookie accountCookie = new Cookie(ACCOUNT_ID_HEADER, account.getId().toString());
                    accountCookie.setPath("/");
                    response.addCookie(accountCookie);
                    return processTemplate("modules/patient/confirmed.ftl", model);
                } else {
                    model.put("form", form);
                    return processTemplate("modules/patient/createform.ftl", model);
                }
            }
        };
    }
}
