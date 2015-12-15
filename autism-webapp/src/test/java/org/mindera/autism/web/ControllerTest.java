package org.mindera.autism.web;

import com.jayway.restassured.RestAssured;
import com.mindera.ams.domain.Account;
import com.mindera.ams.domain.UrlMappings;
import com.mindera.ams.domain.User;
import com.mindera.ams.domain.enumeration.AccountStatus;
import com.mindera.microservice.security.domain.Role;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mindera.autism.web.driver.JsoupDriver;
import org.mindera.autism.web.util.JSON;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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

    protected String getAmsUser(Long userId) {
        Account account = new Account(ACCOUNT_ID, ACCOUNT_NAME, AccountStatus.ACTIVE);
        User user = new User(userId, account, Role.ADMINISTRATOR);
        return JSON.encode(user);
    }

    protected WebDriver getWebDriver() {
        return new JsoupDriver();
    }

    protected JsoupDriver getPage(String path) {
        driver = new JsoupDriver();
        driver.get("http://localhost:" + port + path);
        return driver;
    }

    protected String getUrl(String path) {
        return "http://localhost:" + port + path;
    }


    protected <T> T buildPageObject(Class<T> cls, String path) {
        return PageFactory.initElements(getPage(path), cls);
    }


}