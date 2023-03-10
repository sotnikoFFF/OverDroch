package com.meew.overparser.parser;

import com.meew.overparser.data.Hero;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class BlizzardParser extends MainParser {

    public BlizzardParser(String wedriver) {
        super();
        wikiParser = new OverwatchBlizzardWikiParser();

    }



}
