package com.meew.overparser.parser.Fandom;

import com.meew.overparser.parser.MainParser;
import org.openqa.selenium.chrome.ChromeDriver;


public class FandomParser extends MainParser {
    
    public FandomParser(String webDriverPath) {
        System.setProperty("webdriver.chrome.driver", webDriverPath);
        driver = new ChromeDriver();
        wikiParser = new OverwatchWikiParser(driver);
        heroParser = new OverwatchHeroParser(driver);
    }
    public FandomParser() {
        driver = new ChromeDriver();
        wikiParser = new OverwatchWikiParser(driver);

    }


}
