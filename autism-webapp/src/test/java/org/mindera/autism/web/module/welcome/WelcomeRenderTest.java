package org.mindera.autism.web.module.welcome;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;

import static org.junit.Assert.assertTrue;

public class WelcomeRenderTest extends ControllerTest {


    @Test
    public void canRenderModule() {
        WelcomeModule module = new WelcomeModule(getWebDriver(),
                getUrl(ModuleUrlMapping.MODULE_WELCOME));
        assertTrue(module.getModule().isDisplayed());
    }
}