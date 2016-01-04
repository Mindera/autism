package org.mindera.autism.web.module.registration;

import com.mindera.udb.domain.Status;
import com.mindera.udb.mapping.UrlMappings;
import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.mindera.autism.web.util.JSON;
import org.mockserver.client.server.ForwardChainExpectation;
import org.mockserver.model.Header;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class RegistrationRenderTest extends ControllerTest {

    @Test
    public void canRenderModule() {
        RegistrationModule module = new RegistrationModule(getWebDriver(), getUrl(ModuleUrlMapping.MODULE_REGISTRATION));
        assertTrue(module.getRegistrationForm().isDisplayed());
    }

    @Test
    public void canRenderRegistrationScreen() {

        mockUDBRegister().respond(response().withStatusCode(201)
                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(JSON.encode(new Status.Builder().withCode("USER_CREATED").build())));

        String html = getPostResponse();

        RegistrationModule module = new RegistrationModule(getWebDriverWithPage(html));
        assertTrue(module.getConfirmationScreen().isDisplayed());
    }

    @Test
    public void canRenderFormWhenError() {
        mockUDBRegister().respond(response().withStatusCode(500)
                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8")));

        String html = getPostResponse();
        RegistrationModule module = new RegistrationModule(getWebDriverWithPage(html));
        assertTrue(module.getRegistrationForm().isDisplayed());
    }

    private ForwardChainExpectation mockUDBRegister() {
        return mockServer.when(request()
                        .withMethod("POST")
                        .withPath(UrlMappings.USER)
        );
    }

    private String getPostResponse() {
        return given().param("name", "American Dad")
                .param("email", "america@states.com")
                .param("password", "boo2oo!m")
                .when()
                .post(ModuleUrlMapping.MODULE_REGISTRATION)
                .andReturn().asString();
    }


}