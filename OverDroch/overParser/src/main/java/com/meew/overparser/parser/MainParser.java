package com.meew.overparser.parser;

import com.meew.overparser.Mode;
import com.meew.overparser.data.Hero;
import com.meew.overparser.utils.RestUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public abstract class MainParser {

    String webDriverPath;
    public WikiParser wikiParser;

    public HeroParser heroParser;

    public static WebDriver driver;
    private RestUtils restUtils;
    MainParser parser;
    private Mode mode;
    public void setWebDriverPath(String webDriverPath) {
        this.webDriverPath = webDriverPath;
    }

    public void setRestUtils(RestUtils restUtils) {
        this.restUtils=restUtils;
    }
    public RestUtils getRestUtils() {
        return restUtils;
    }


    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void scheduleFixedRateWithInitialDelayTask() {
        parser.parse();
    }

    public void parse(){

    }

    public void setMode(String mode) {
        this.mode = Mode.valueOf(mode);
    }

    public Mode getMode() {
        return mode;
    }




    private void init() {
        System.setProperty("webdriver.chrome.driver", webDriverPath);
    }

    public String getAllContent() {
        return wikiParser.getContent() + heroParser.getContent();
    }
}
