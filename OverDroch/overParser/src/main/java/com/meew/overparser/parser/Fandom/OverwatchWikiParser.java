package com.meew.overparser.parser.Fandom;

import com.meew.overparser.parser.WikiParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class OverwatchWikiParser implements WikiParser {

    private String url="https://overwatch.fandom.com/wiki/Overwatch_Wiki";

    private WebDriver driver;

    public OverwatchWikiParser(){
        this.driver = new ChromeDriver();
        driver.get(url);
    }

    private boolean isOnPage(){
        return driver.getCurrentUrl().equals(url);
    }

    @Override
    public void close() {
        driver.quit();
    }
    @Override
    public void search(String query) {
        driver.get(url);
        WebElement searchBox = driver.findElement(By.id("searchInput"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }
    @Override
    public void clickLink(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        link.click();
    }
    @Override
    public String getTitle() {
        return driver.getTitle();
    }
    @Override
    public String getContent() {
        if(!isOnPage()){
            driver.get(url);
        }
        WebElement content = driver.findElement(By.id("mw-content-text"));
        return content.getText();
    }
    @Override
    public String getLinks() {
        if(!isOnPage()){
            driver.get(url);
        }
        WebElement content = driver.findElement(By.id("mw-content-text"));
        List<WebElement> links = content.findElements(By.tagName("a"));
        StringBuilder stringBuilder = new StringBuilder();
        for(WebElement e : links){
            stringBuilder.append(e.getText()).append("\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public void parse() {
        driver.get(url);
    }

}