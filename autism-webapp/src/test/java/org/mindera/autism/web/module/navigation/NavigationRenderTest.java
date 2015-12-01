package org.mindera.autism.web.module.navigation;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.ModuleUrlMapping;

import static org.junit.Assert.assertTrue;

public class NavigationRenderTest extends ControllerTest {


    @Test
    public void canRenderModuleHeader() {
        NavigationModule module = new NavigationModule(getWebDriver(),
                getUrl(ModuleUrlMapping.MODULE_NAVIGATION.concat("?type=header")));
        assertTrue(module.isHeader());
    }

    @Test
    public void canRenderModuleFooter() {
        NavigationModule module = new NavigationModule(getWebDriver(),
                getUrl(ModuleUrlMapping.MODULE_NAVIGATION.concat("?type=footer")));
        assertTrue(module.isFooter());
    }
}