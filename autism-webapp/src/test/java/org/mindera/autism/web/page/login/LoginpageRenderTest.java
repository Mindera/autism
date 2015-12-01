package org.mindera.autism.web.page.login;

import org.junit.Test;
import org.mindera.autism.web.ControllerTest;
import org.mindera.autism.web.delegate.PageDelegate;
import org.mindera.autism.web.domain.mapping.UrlMapping;
import org.mindera.autism.web.page.homepage.Homepage;

import javax.annotation.Resource;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LoginpageRenderTest extends ControllerTest {

    @Test
    public void canRenderPage() {
        Loginpage module = new Loginpage(getWebDriver(), getUrl(UrlMapping.LOGIN));
        assertTrue(module.getLoginModule().getModule().isDisplayed());
    }


}