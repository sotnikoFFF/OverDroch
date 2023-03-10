package com.meew.overparser.parser;

import com.meew.overparser.Mode;
import com.meew.overparser.parser.Fandom.FandomParser;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public class InfoProviderImpl  implements InfoProvider{

    Logger logger = LoggerFactory.getLogger(InfoProviderImpl.class);
    MainParser parser;

    Properties loadedProperties;

    public InfoProviderImpl(Properties properties , String mode){

        logger.info(properties.toString());
        loadedProperties=properties;
        switch (Mode.valueOf(mode)){
            case FANDOM -> {
                parser = new FandomParser(properties.getProperty("wedriver"));
                break;
            }
            case BLIZZARD ->{
                parser = new BlizzardParser(properties.getProperty("wedriver"));
                break;
            }
        }

    }


    @Override
    public String getInfo() {
        return parser.getAllContent();
    }

    @Override
    public String getLinks() {
        return parser.wikiParser.getLinks();
    }

    public void setLoadedProperties(Properties loadedProperties) {
        this.loadedProperties = loadedProperties;
    }
}
