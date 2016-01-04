package org.mindera.autism.web;

import com.jayway.restassured.RestAssured;
import com.mindera.ams.domain.Account;
import com.mindera.ams.domain.UrlMappings;
import com.mindera.ams.domain.User;
import com.mindera.ams.domain.enumeration.AccountStatus;
import com.mindera.microservice.security.domain.Role;
import com.mindera.udb.domain.Details;
import com.mindera.udb.domain.Product;
import com.mindera.udb.domain.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mindera.autism.web.driver.JsoupDriver;
import org.mindera.autism.web.util.JSON;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest()
@SpringApplicationConfiguration(classes = Bootstrap.class)
@ActiveProfiles(profiles = "integration-test")
abstract public class ControllerTest {

    public static final long ACCOUNT_ID = 999L;
    public static final String ACCOUNT_NAME = "joes";
    public static final String ACCOUNT_DESCRIPTION = "joes";
    public static final String SESSION_TOKEN = "session-token";

    @Value("${server.port}")
    protected int port;

    protected JsoupDriver driver;


    protected ClientAndServer mockServer;

    @Value("${ams.client.port}")
    protected int amsClientPort;


    @Before
    public void setup() {
        RestAssured.port = port;
        mockServer = startClientAndServer(amsClientPort);
        mockServer.when(request()
                        .withMethod("GET")
                        .withPath(UrlMappings.USER)
        ).respond(response().withStatusCode(200)
                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(getAmsUser(new Double(Math.random() * 1000).longValue())));
    }

    @After
    public void tearDown() {
        mockServer.stop();
    }

    protected String getAmsUser(Long userId) {
        Account account = new Account(ACCOUNT_ID, ACCOUNT_NAME, ACCOUNT_DESCRIPTION, AccountStatus.ACTIVE);
        User user = new User(userId, account, Role.ADMINISTRATOR, new Session.Builder()
                .withToken(SESSION_TOKEN)
                .build());
        return JSON.encode(user);
    }

    protected String getUdbUser(Long userId) {
        Session session = new Session.Builder()
                .withDetails(new Details.Builder().withName("Joe").withId(userId).withEmail("joe@vencerautismo.org").build())
                .withProduct(new Product.Builder().withId("autism-integration").build())
                .withTime(new Date())
                .withToken(SESSION_TOKEN)
                .build();
        return JSON.encode(session);
    }

    protected WebDriver getWebDriver() {
        return new JsoupDriver();
    }

    protected WebDriver getWebDriverWithPage(String html) {
        JsoupDriver jsoupDriver = new JsoupDriver();
        jsoupDriver.parse(html);
        return jsoupDriver;
    }

    protected String getUrl(String path) {
        return "http://localhost:" + port + path;
    }



}