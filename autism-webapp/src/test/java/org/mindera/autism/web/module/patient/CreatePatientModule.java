package org.mindera.autism.web.module.patient;

import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreatePatientModule extends PageObject {


    @FindBy(css = "create-patient-form")
    private WebElement form;

    @FindBy(css = "create-patient-confirmed")
    private WebElement confirmation;

    public CreatePatientModule(WebDriver driver, String url) {
        super(driver, url);
    }

    public CreatePatientModule(WebDriver driver) {
        super(driver);
    }

    public CreatePatientModule(WebElement parent) {
        super(parent);
    }

    public WebElement getCreatePatientForm() {
        return form;
    }
    public WebElement getCreatePatientConfirmed() {
        return confirmation;
    }

}
