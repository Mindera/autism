package org.mindera.autism.web.driver;

import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.internal.WrapsDriver;

import java.util.List;


public class JsoupWebElement implements WrapsDriver, FindsByCssSelector, WebElement {

    protected final JsoupDriver driver;

    protected final Element element;

    public JsoupWebElement(JsoupDriver driver, Element element) {
        this.driver = driver;
        this.element = element;
    }

    @Override
    public WebElement findElementByCssSelector(String s) {
        return null;
    }

    @Override
    public List<WebElement> findElementsByCssSelector(String s) {
        return null;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return null;
    }

    @Override
    public void click() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... charSequences) {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getTagName() {
        return element.tagName();
    }

    @Override
    public String getAttribute(String s) {
        return element.attr(s);
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return !element.hasAttr("disabled");
    }

    @Override
    public String getText() {
        return element.text();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return true;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public String getCssValue(String s) {
        return null;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return null;
    }
}
