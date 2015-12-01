package org.mindera.autism.web.module.login;

import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginModule extends PageObject {


    @FindBy(css = ".loginform")
    private WebElement module;

    public LoginModule(WebDriver driver, String url) {
        super(driver, url);
    }

    public LoginModule(WebElement parent) {
        super(parent);
    }

    public WebElement getModule() {
        return module;
    }

}
