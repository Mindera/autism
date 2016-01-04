package org.mindera.autism.web.module.login;

import com.mindera.ams.client.AmsClient;
import com.mindera.ams.domain.Account;
import com.mindera.udb.client.UdbClient;
import com.mindera.udb.domain.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindera.autism.web.Bootstrap;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
public class LoginDelegateTest {

    @InjectMocks
    private LoginDelegate loginDelegate;

    @Mock
    AmsClient amsClient;

    @Mock
    UdbClient udbClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canGetAccountList() {
        List<Account> list = new ArrayList<>();
        String token = "token";
        when(amsClient.getAccountList(token)).thenReturn(list);
        List<Account> result = loginDelegate.getAccountList(new Session.Builder().withToken(token).build());
        assertEquals(list, result);
    }

    @Test
    public void canHandleAmsExcpetion() {
        String token = "token";
        when(amsClient.getAccountList(token)).thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE));
        List<Account> result = loginDelegate.getAccountList(new Session.Builder().withToken(token).build());
        assertEquals(0, result.size());
    }

    @Test
    public void canValidateForm() {
        Session emptySession = new Session.Builder().build();
        when(udbClient.login(any())).thenReturn(emptySession);
        LoginForm form = new LoginForm();
        assertEquals(emptySession, loginDelegate.login(form, null));

        form.setAccountUrl("/");
        assertEquals(emptySession, loginDelegate.login(form, null));

        form.setSuccessUrl("/");
        assertEquals(emptySession, loginDelegate.login(form, null));

        form.setEmail("moo");
        assertEquals(emptySession, loginDelegate.login(form, null));
    }

}