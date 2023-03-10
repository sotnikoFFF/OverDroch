package com.meew.overparser.parser;

import com.meew.overparser.Mode;
import com.meew.overparser.data.Hero;
import com.meew.overparser.data.InfoType;
import com.meew.overparser.data.parser.Holder;
import com.meew.overparser.parser.Fandom.FandomParser;
import com.meew.overparser.parser.exceptions.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InfoProviderImpl  implements InfoProvider{

    Logger logger = LoggerFactory.getLogger(InfoProviderImpl.class);
    MainParser parser;

    Holder holder;
    Properties loadedProperties;

    public InfoProviderImpl(Properties properties , String mode){

        logger.info(properties.toString().replaceAll(",",",\n"));
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


        fillHolder();
    }


    @Override
    public Object getAllContent() {
        Object o = holder.getAllContent();
        if(o==null) {
            try {
                o = parser.getAllContent();
            } catch (ParserException e) {
                throw new RuntimeException("Exception",e);
            }
        }
        return o;
    }

    @Override
    public Object getAllContent(InfoType type) {
       return getAllContent(type,null);
    }

    @Override
    public String getLinks() {
        try {
            return parser.wikiParser.getLinks();
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getAllContent(InfoType type, String name) {
        Object info = null;

        try {
            if (holder==null)
                switch (type){
                    case HERO ->{
                            info = parser.heroParser.getHero(name);
                            break;
                    }
                    case MAPS -> {
                        info = parser.mapParser.getAllMaps();
                        break;
                    }
                    case HEROES -> {
                        info = parser.heroParser.getAllHeroes();
                        break;
                    }
                    case MAIN_WIKI ->{
                        info = parser.wikiParser.getContent();
                        break;
                    }
                    case ABILITY_CONNECTION -> {
                        //Todo create abilities connectionbreak;
                        }
                        case MAP -> {
                            info = parser.getMapParser().getMap(name);
                            break;
                        }
                    }
            else
                switch (type) {
                    case HERO -> {
                        info = holder.getHeroList().stream().filter(hero -> hero.getName().equals(name)).findFirst().get();
                        break;
                    }
                    case MAPS -> {
                        info = holder.getMapList();
                        break;
                    }
                    case HEROES -> {
                        info = holder.getHeroList();
                        break;
                    }
                    case MAIN_WIKI -> {
                        info = parser.wikiParser.getContent();
                        break;
                    }
                    case ABILITY_CONNECTION -> {
                        //Todo create abilities connection
                        break;
                    }
                    case MAP -> {
                        info = parser.getMapParser().getMap(name);
                        break;
                    }
                }
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
        return info;
    }
    private void fillHolder() {
        holder = new Holder();
        try {
            holder.setAllHeroesNames(parser.getHeroParser().getAllHeroesNames());
            List<Hero> heroes = new ArrayList<>();
            logger.info(holder.getAllHeroesNames() + "");
            for(String h: holder.getAllHeroesNames())
                heroes.add(parser.getHeroParser().getHero(h));
            holder.setHeroList(heroes);
//            holder.setMapList(parser.getMapParser().getAllMaps());
//            holder.setOverwatchOverview(parser.getWikiParser().getContent());
        } catch (ParserException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException nullPointerException){
            parser.close();
            throw nullPointerException;
        }


    }

    public void setLoadedProperties(Properties loadedProperties) {
        this.loadedProperties = loadedProperties;
    }
}
