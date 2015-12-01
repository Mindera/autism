package org.mindera.autism.web.module.login;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;

import static org.junit.Assert.assertTrue;

public class LoginRenderTest extends ControllerTest {


    @Test
    public void canRenderModule() {
        LoginModule module = new LoginModule(getWebDriver(), getUrl(ModuleUrlMapping.MODULE_LOGIN));
        assertTrue(module.getModule().isDisplayed());
    }
}