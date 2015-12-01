package org.mindera.autism.web.page.slowdown;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class SlowdownRenderTest extends ControllerTest {


    @Test
    public void canRenderPageWhenModulesSlowdown() {
        expect().body(containsString("SLOW")).when().get("/module/slowdown");
        expect().body(not(containsString("SLOW"))).when().get("/slowdown");
    }


}