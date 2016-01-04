package org.mindera.autism.web.module.patient;

import com.mindera.ams.client.AmsClient;
import com.mindera.ams.domain.Account;
import com.mindera.udb.client.UdbClient;
import com.mindera.udb.domain.Session;
import org.apache.http.protocol.HTTP;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindera.autism.web.Bootstrap;
import org.mindera.autism.web.module.login.LoginForm;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
public class PatientDelegateTest {

    @InjectMocks
    private PatientDelegate patientDelegate;

    @Mock
    AmsClient amsClient;

    @Mock
    UdbClient udbClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canCreateAccount() {
        String token = "token";
        Account account = new Account.Builder().withId(1L).build();
        CreatePatientForm form = new CreatePatientForm();
        form.setDescription("Boom");

        when(amsClient.createAccount(eq(token), any())).thenReturn(account);
        Account createdAccount = patientDelegate.create(form, token);
        assertEquals(account, createdAccount);
    }

    @Test
    public void canHandleAccountCreationException() {
        String token = "token";
        CreatePatientForm form = new CreatePatientForm();
        form.setDescription("Boom");

        when(amsClient.createAccount(eq(token), any())).thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE));
        Account createdAccount = patientDelegate.create(form, token);
        assertEquals(null, createdAccount);
    }

    @Test
    public void dontCreateAccountWhenFormIsinvalid() {
        String token = "token";
        CreatePatientForm form = new CreatePatientForm();
        when(amsClient.createAccount(eq(token), any())).thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE));
        Account createdAccount = patientDelegate.create(form, token);
        assertEquals(null, createdAccount);
    }

    @Test
    public void canValidateForm() {
        CreatePatientForm form = new CreatePatientForm();
        assertFalse(patientDelegate.validateForm(form));
        form.setDescription("Boom");
        assertTrue(patientDelegate.validateForm(form));
    }

}