package org.mindera.autism.web.module.welcome;

import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomeModule extends PageObject {


    @FindBy(css = ".welcome")
    private WebElement module;

    public WelcomeModule(WebDriver driver, String url) {
        super(driver, url);
    }

    public WebElement getModule() {
        return module;
    }

}
