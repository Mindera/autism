package org.mindera.autism.web.module.credits;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;

import static org.junit.Assert.assertTrue;

public class CreditsRenderTest extends ControllerTest {


    @Test
    public void canRenderModule() {
        CreditsModule module = new CreditsModule(getWebDriver(), getUrl(ModuleUrlMapping.MODULE_CREDITS));
        assertTrue(module.getModule().isDisplayed());
    }

}