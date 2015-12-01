package org.mindera.autism.web.page.homepage;

import org.mindera.autism.web.module.navigation.NavigationModule;
import org.mindera.autism.web.pageobject.PageObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepage extends PageObject {

    @FindBy(css = "nav module.navigation")
    private WebElement header;

    @FindBy(css = "footer module.navigation")
    private WebElement footer;


    public Homepage(WebDriver driver, String url) {
        super(driver, url);
    }

    public NavigationModule getNavigationHeader() {
        return new NavigationModule(header);
    }

    public NavigationModule getNavigationFooter() {
        return new NavigationModule(footer);
    }


//    @FindBy(css = ".navigation .header")
//    private NavigationModule header;
//
//    @FindBy(css = ".navigation .footer")
//    private List<WebElement> header;
//
//    public Homepage(WebDriver driver) {
//        super(driver);
//    }
//
//    public WebElement getModule() {
//        return module;
//    }

}
