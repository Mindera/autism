package org.mindera.autism.web.module.login;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.mindera.udb.mapping.UrlMappings;
import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;
import org.mockserver.model.Header;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class LoginRenderTest extends ControllerTest {

    @Test
    public void canRenderModule() {
        LoginModule module = new LoginModule(getWebDriver(), getUrl(ModuleUrlMapping.MODULE_LOGIN));
        assertTrue(module.getModule().isDisplayed());
    }

    @Test
    public void loginWithIncorrectCredentials() {

        // prepare UDB mock to respond with successful login
        mockServer.when(request()
                        .withMethod("POST")
                        .withPath(UrlMappings.LOGIN)
        ).respond(response().withStatusCode(403));

        String module = postLoginForm("/success", "/failure", "/account", "joe@vencerautismo.org", "bla")
                .andReturn().body().asString();

        assertEquals("", module);
    }

    @Test
    public void loginWithoutHavingAPatient() {

        // prepare UDB mock to respond with successful login
        mockServer.when(request()
                        .withMethod("POST")
                        .withPath(UrlMappings.LOGIN)
        ).respond(response().withStatusCode(200)
                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(getUdbUser(1L)));

        postLoginForm("/success", "/failure", "/account", "joe@vencerautismo.org", "bla")
                .then()
                .header("Location", equalTo("/account"))
                .statusCode(302)
                .cookie("M-SSO", equalTo("session-token"))
                .body(containsString(""));
    }

    private Response postLoginForm(String successUrl, String failureUrl, String accountUrl, String email, String password) {
        return given().param("successUrl", successUrl)
                .param("failureUrl", failureUrl)
                .param("accountUrl", accountUrl)
                .param("email", email)
                .param("password", password)
                .when()
                .post(ModuleUrlMapping.MODULE_LOGIN);
    }
}