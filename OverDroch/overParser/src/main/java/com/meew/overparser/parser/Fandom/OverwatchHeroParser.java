package com.meew.overparser.parser.Fandom;

import com.meew.overparser.parser.HeroParser;
import org.openqa.selenium.WebDriver;

public class OverwatchHeroParser implements HeroParser {

    WebDriver driver;
    public OverwatchHeroParser(WebDriver driver) {
        this.driver=driver;
    }

    @Override
    public String getContent() {
        return null;
    }
}
