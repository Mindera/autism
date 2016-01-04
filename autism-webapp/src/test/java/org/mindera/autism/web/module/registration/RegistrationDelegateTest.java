package org.mindera.autism.web.module.registration;

import com.mindera.ams.client.AmsClient;
import com.mindera.ams.domain.Account;
import com.mindera.udb.client.UdbClient;
import com.mindera.udb.domain.Details;
import com.mindera.udb.domain.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindera.autism.web.Bootstrap;
import org.mindera.autism.web.module.patient.CreatePatientForm;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
public class RegistrationDelegateTest {

    public static final String EMAIL = "moo@maa.com";
    public static final String NAME = "Mr Robot";

    @InjectMocks
    private RegistrationDelegate registrationDelegate;

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
        when(udbClient.register(any())).thenReturn(new Status.Builder().withCode(RegistrationDelegate.USER_CREATED).build());
        Details userDetails = registrationDelegate.create(getForm());
        assertEquals(EMAIL, userDetails.getEmail());
        assertEquals(NAME, userDetails.getName());
    }

    @Test
    public void canHandleCreateAccountException() {
        when(udbClient.register(any())).thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE));
        assertNull(registrationDelegate.create(getForm()));
    }

    @Test
    public void canHandleInvalidResponseCode() {
        when(udbClient.register(any())).thenReturn(new Status.Builder().withCode("OPS_NO_IDEA_WHAT_HAPPENED").build());
        assertNull(registrationDelegate.create(getForm()));
    }

    private RegistrationForm getForm() {
        RegistrationForm form = new RegistrationForm();
        form.setEmail(EMAIL);
        form.setPassword("123456");
        form.setName(NAME);
        return form;
    }


}