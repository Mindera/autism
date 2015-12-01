package org.mindera.autism.web.page.login;

import org.mindera.autism.web.module.login.LoginModule;
import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Loginpage extends PageObject {

    @FindBy(css = "form")
    private WebElement loginform;


    public Loginpage(WebDriver driver, String url) {
        super(driver, url);
    }

    public LoginModule getLoginModule() {
        return new LoginModule(loginform);
    }


}
