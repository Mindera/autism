package org.mindera.autism.web.module.credits;

import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreditsModule extends PageObject {


    @FindBy(css = ".credits")
    private WebElement module;

    public CreditsModule(WebDriver driver, String url) {
        super(driver, url);
    }


    public WebElement getModule() {
        return module;
    }

}
