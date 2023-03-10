package com.meew.overparser.parser;

import com.meew.overparser.data.Hero;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class OverwatchBlizzardWikiParser implements WikiParser {


    private WebDriver driver;

    public OverwatchBlizzardWikiParser(WebDriver driver){
        this.driver = driver;
    }


    @Override
    public void close() {

    }

    @Override
    public void search(String query) {

    }

    @Override
    public void clickLink(String linkText) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String getLinks() {
        return null;
    }

    @Override
    public void parse() {

    }
}
