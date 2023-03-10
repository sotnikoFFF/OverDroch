package com.meew.overparser.parser.Fandom;

import com.meew.overparser.parser.HeroParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OverwatchHeroParser implements HeroParser {

    WebDriver driver;
    public OverwatchHeroParser() {
        this.driver = new ChromeDriver();
    }

    @Override
    public String getContent() {
        return null;
    }
}
