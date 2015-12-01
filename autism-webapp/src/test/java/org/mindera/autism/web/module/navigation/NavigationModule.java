package org.mindera.autism.web.module.navigation;

import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationModule extends PageObject {


    @FindBy(css = ".header")
    private WebElement header;

    @FindBy(css = ".footer")
    private WebElement footer;

    public NavigationModule(WebDriver driver, String url) {
        super(driver, url);
    }

    public NavigationModule(WebElement parent) {
        super(parent);
    }

    public boolean isHeader() {
        return header.isDisplayed();
    }

    public boolean isFooter() {
        return footer.isDisplayed();
    }

}
