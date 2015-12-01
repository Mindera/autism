package org.mindera.autism.web.pageobject;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;


public class PageObject {

    private WebDriver driver;

    public PageObject(WebDriver driver, String url) {
        this(driver);
        driver.get(url);
    }

    public PageObject(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public PageObject(WebElement parent) {
        PageFactory.initElements(new SearchContextElementLocatorFactory(parent), this);
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public class SearchContextElementLocatorFactory
            implements ElementLocatorFactory {

        private final SearchContext context;

        public SearchContextElementLocatorFactory(SearchContext context) {
            this.context = context;
        }

        @Override
        public ElementLocator createLocator(Field field) {
            return new DefaultElementLocator(context, field);
        }
    }
}