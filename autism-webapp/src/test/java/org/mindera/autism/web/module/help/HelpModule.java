package org.mindera.autism.web.module.help;

import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HelpModule extends PageObject {


    @FindBy(css = ".help")
    private WebElement module;

    public HelpModule(WebDriver driver, String url) {
        super(driver, url);
    }


    public WebElement getModule() {
        return module;
    }

}
