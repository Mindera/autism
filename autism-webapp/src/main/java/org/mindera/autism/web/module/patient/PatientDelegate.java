package org.mindera.autism.web.module.patient;

import com.mindera.ams.client.AmsClient;
import com.mindera.ams.domain.Account;
import com.mindera.ams.domain.enumeration.AccountStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Component
public class PatientDelegate {

    @Resource
    AmsClient amsClient;


    public Account create(CreatePatientForm form, String sessionToken) {

        if (validateForm(form)) {
            // generate an account with a random name
            Account account = new Account.Builder()
                    .withName(UUID.randomUUID().toString())
                    .withDescription(form.getDescription())
                    .withAccountStatus(AccountStatus.ACTIVE)
                    .build();

            Account createdAccount;

            try {
                createdAccount = amsClient.createAccount(sessionToken, account);
            } catch (Exception exception) {
                createdAccount = null;
            }

            return createdAccount;
        } else {
            return null;
        }

    }

    public boolean validateForm(CreatePatientForm form) {
        if (nonNull(form.getDescription())) {
            return true;
        } else {
            return false;
        }
    }
}
