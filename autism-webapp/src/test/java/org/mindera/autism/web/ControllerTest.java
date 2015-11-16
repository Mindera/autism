package org.mindera.autism.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.jayway.restassured.RestAssured;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@SpringApplicationConfiguration(classes = Bootstrap.class)
@ActiveProfiles(profiles = "integration-test")
abstract public class ControllerTest {


    @Value("${local.server.port}")
    protected int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

}