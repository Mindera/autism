package org.mindera.autism.web.driver;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsoupDriver implements WebDriver, FindsByCssSelector, HasCapabilities {

    private DesiredCapabilities capabilities;
    private Document page;

    public JsoupDriver() {
        capabilities = DesiredCapabilities.htmlUnit();
        capabilities.setBrowserName("Jsoup");
        capabilities.setJavascriptEnabled(false);
    }

    @Override
    public WebElement findElementByCssSelector(String s) {
        Elements elements = page.select(s);
        if (elements.size() > 0) {
            return new JsoupWebElement(this, elements.get(0));
        } else {
            return null;
        }
    }

    @Override
    public List<WebElement> findElementsByCssSelector(String s) {
        Elements elements = page.select(s);
        return elements.stream().map(e -> new JsoupWebElement(this, e)).collect(Collectors.toList());
    }

    @Override
    public Capabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public void get(String url) {
        try {
            page = Jsoup.connect(url).timeout(30000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public List<WebElement> findElements(By by) {
        return by.findElements(this);
    }

    @Override
    public WebElement findElement(By by) {
        return by.findElement(this);
    }

    @Override
    public String getPageSource() {
        return page.toString();
    }

    @Override
    public void close() {

    }

    @Override
    public void quit() {

    }

    @Override
    public Set<String> getWindowHandles() {
        return null;
    }

    @Override
    public String getWindowHandle() {
        return null;
    }

    @Override
    public TargetLocator switchTo() {
        return null;
    }

    @Override
    public Navigation navigate() {
        return null;
    }

    @Override
    public Options manage() {
        return null;
    }

    public void parse(String html) {
        page = Jsoup.parse(html);
    }

}
