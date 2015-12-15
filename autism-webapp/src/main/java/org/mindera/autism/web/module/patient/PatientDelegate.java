package org.mindera.autism.web.module.patient;

import com.mindera.ams.client.AmsClient;
import com.mindera.ams.domain.Account;
import com.mindera.ams.domain.enumeration.AccountStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;

@Component
public class PatientDelegate {

    @Resource
    AmsClient amsClient;


    public Account create(CreatePatientForm form, String sessionToken) {

        Account account = new Account.Builder()
                .withName(form.getName())
                .withAccountStatus(AccountStatus.ACTIVE)
                .build();

        Account createdAccount;

        try {
            createdAccount = amsClient.createAccount(sessionToken, account);
        } catch (Exception exception) {
            createdAccount = null;
        }

        return createdAccount;
    }
}
