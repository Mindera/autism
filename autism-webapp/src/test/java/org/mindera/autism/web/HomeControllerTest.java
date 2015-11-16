package org.mindera.autism.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

public class HomeControllerTest extends ControllerTest {


    @Test
    public void canGetHelloWorldMessage() {
        expect().body(containsString("Let's overcome Autism together!")).when().get("/");
    }
}