package org.mindera.autism.web.module.patient;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class PatientRenderTest extends ControllerTest {

    @Test
    public void canRenderModule() {
        CreatePatientModule module = new CreatePatientModule(getWebDriver(), getUrl(ModuleUrlMapping.MODULE_PATIENT));
        assertTrue(module.getCreatePatientForm().isDisplayed());
    }


    @Test
    public void canSwitchAccount() {
        given().redirects()
                .follow(false)
                .header("M-SSO", SESSION_TOKEN)
                .header("M-Account-Id", String.valueOf(ACCOUNT_ID))
                .when()
                .get(ModuleUrlMapping.MODULE_PATIENT.concat("/switch/").concat(String.valueOf(ACCOUNT_ID)))
                .then()
                .header("Location", equalTo("/"))
                .statusCode(302)
                .cookie("M-Account-Id", equalTo(String.valueOf(ACCOUNT_ID)))
                .body(containsString(""));
    }


}