package org.mindera.autism.web.page.homepage;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.delegate.PageDelegate;
import org.mindera.autism.web.domain.mapping.UrlMapping;
import org.springframework.boot.test.IntegrationTest;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

public class HomepageRenderMooTest extends ControllerTest {

    @Resource
    PageDelegate pageDelegate;

    @Test
    public void canRenderPage() {
        Homepage module = new Homepage(getWebDriver(), getUrl(UrlMapping.HOME));
        assertTrue(module.getNavigationFooter().isFooter());
        assertTrue(module.getNavigationHeader().isHeader());
    }

}