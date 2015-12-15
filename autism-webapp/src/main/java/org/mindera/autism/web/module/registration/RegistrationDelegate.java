package org.mindera.autism.web.module.registration;

import com.mindera.udb.client.UdbClient;
import com.mindera.udb.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;

@Component
public class RegistrationDelegate {

    public static final String USER_CREATED = "USER_CREATED";
    @Resource
    UdbClient udbClient;


    public Details create(RegistrationForm form) {

        User user = new User.Builder()
                .withCredentials(new Credentials.Builder()
                        .withPassword(form.getPassword())
                        .withUsername(form.getEmail())
                        .build())
                .withDetails(new Details.Builder()
                        .withEmail(form.getEmail())
                        .withName(form.getName())
                        .build())
                .build();

        Status status;

        try {
            status = udbClient.register(user);
        } catch (Exception exception) {
            status = new Status.Builder().withCode(exception.getMessage()).build();
        }
        if (status.getCode().equals(USER_CREATED)) {
            return user.getDetails();
        } else {
            return null;
        }
    }
}
