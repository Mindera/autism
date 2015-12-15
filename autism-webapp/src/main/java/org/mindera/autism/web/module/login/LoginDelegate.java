package org.mindera.autism.web.module.login;

import com.mindera.ams.client.AmsClient;
import com.mindera.ams.domain.Account;
import com.mindera.udb.client.UdbClient;
import com.mindera.udb.domain.Credentials;
import com.mindera.udb.domain.Session;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class LoginDelegate {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LoginDelegate.class);

    @Resource
    UdbClient udbClient;

    @Resource
    AmsClient amsClient;

    public Session login(LoginForm form, HttpServletResponse response) {

        boolean r = false;
        Session session = new Session.Builder().build();

        if (validateForm(form)) {
            try {
                session = udbClient.login(new Credentials.Builder()
                        .withUsername(form.getEmail())
                        .withPassword(form.getPassword())
                        .build());
            } catch (Exception e) {
                LOGGER.debug("UDB Login failed: " + e.getMessage());
            }
        }

        return session;
    }

    public List<Account> getAccountList(Session session) {
        List<Account> list = new ArrayList<>();
        try {
            list = amsClient.getAccountList(session.getToken());
        } catch (Exception e) {
            LOGGER.debug("AMS Account list retrieval failure " + e.getMessage());
        }
        return list;
    }

    private boolean validateForm(LoginForm form) {
        if (nonNull(form.getEmail())
                && nonNull(form.getPassword())
                && nonNull(form.getFailureUrl())
                && nonNull(form.getSuccessUrl())) {
            return true;
        } else {
            return false;
        }
    }
}
