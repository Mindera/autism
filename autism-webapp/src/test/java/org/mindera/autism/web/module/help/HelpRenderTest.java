package org.mindera.autism.web.module.help;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;

import static org.junit.Assert.assertTrue;

public class HelpRenderTest extends ControllerTest {


    @Test
    public void canRenderModule() {
        HelpModule module = new HelpModule(getWebDriver(), getUrl(ModuleUrlMapping.MODULE_HELP));
        assertTrue(module.getModule().isDisplayed());
    }
}