package org.mindera.autism.web.module.registration;

import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationModule extends PageObject {


    @FindBy(css = "registration-form")
    private WebElement form;

    @FindBy(css = "registration-confirmed")
    private WebElement confirmation;

    public RegistrationModule(WebDriver driver, String url) {
        super(driver, url);
    }

    public RegistrationModule(WebDriver driver) {
        super(driver);
    }

    public RegistrationModule(WebElement parent) {
        super(parent);
    }

    public WebElement getRegistrationForm() {
        return form;
    }
    public WebElement getConfirmationScreen() {
        return confirmation;
    }

}
