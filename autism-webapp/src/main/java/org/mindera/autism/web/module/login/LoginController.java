package org.mindera.autism.web.module.login;


import com.mindera.ams.domain.Account;
import com.mindera.microservice.controller.ApiResponse;
import com.mindera.microservice.security.annotation.Authorize;
import com.mindera.microservice.security.domain.Role;
import com.mindera.udb.domain.Session;
import org.mindera.autism.web.controller.SiteController;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping(ModuleUrlMapping.MODULE_LOGIN)
public class LoginController extends SiteController {

    @Resource
    LoginDelegate loginDelegate;

    @RequestMapping(method = {RequestMethod.GET})
    @Authorize(role = {Role.VISITOR})
    public ApiResponse get() {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                Map<String, Object> model = new HashMap<>();
                return processTemplate("modules/login/loginform.ftl", model);
            }
        };
    }

    @RequestMapping(method = {RequestMethod.POST})
    @Authorize(role = {Role.VISITOR})
    public ApiResponse post(@ModelAttribute LoginForm form, HttpServletResponse response) {

        return new ApiResponse() {
            @Override
            public ResponseEntity<String> run() throws Exception {
                Session session = loginDelegate.login(form, response);

                if (nonNull(session.getToken())) {
                    Cookie sessionCookie = new Cookie("M-SSO", session.getToken());
                    sessionCookie.setPath("/");
                    response.addCookie(sessionCookie);

                    String url = form.getSuccessUrl();
                    List<Account> accountList = loginDelegate.getAccountList(session);
                    if (accountList.size() == 0) {
                        url = form.getAccountUrl();
                    } else {
                        Cookie accountCookie = new Cookie("M-Account-Id", accountList.get(0).getId().toString());
                        accountCookie.setPath("/");
                        response.addCookie(accountCookie);
                    }

                    response.addHeader("Location", url);
                    return getResponseEntity("", HttpStatus.FOUND);
                } else {
                    response.addHeader("Location", form.getFailureUrl());
                    return getResponseEntity("", HttpStatus.FOUND);
                }
            }
        };
    }
}
