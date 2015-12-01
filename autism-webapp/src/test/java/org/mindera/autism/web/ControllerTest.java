package org.mindera.autism.web;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mindera.autism.web.driver.JsoupDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest()
@SpringApplicationConfiguration(classes = Bootstrap.class)
@ActiveProfiles(profiles = "integration-test")
abstract public class ControllerTest {


    @Value("${server.port}")
    protected int port;

    protected JsoupDriver driver;


    @Before
    public void setup() {
        RestAssured.port = port;
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