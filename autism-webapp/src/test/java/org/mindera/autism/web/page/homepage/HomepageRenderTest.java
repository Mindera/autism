package org.mindera.autism.web.page.homepage;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.domain.mapping.UrlMapping;

import static org.junit.Assert.assertTrue;

public class HomepageRenderTest extends ControllerTest {


    @Test
    public void canRenderPage() {
        Homepage module = new Homepage(getWebDriver(), getUrl(UrlMapping.HOME));
        assertTrue(module.getNavigationFooter().isFooter());
        assertTrue(module.getNavigationHeader().isHeader());
    }

}