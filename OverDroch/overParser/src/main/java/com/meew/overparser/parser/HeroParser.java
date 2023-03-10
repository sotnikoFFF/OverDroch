package com.meew.overparser.parser;

import com.meew.overparser.data.Hero;
import com.meew.overparser.parser.exceptions.ParserException;

import javax.swing.text.html.parser.Parser;
import java.util.List;

public interface HeroParser {
    String getContent() throws ParserException;

    Hero getHero(String name) throws ParserException;

    List<Hero> getAllHeroes() throws ParserException;

    List<String> getAllHeroesNames() throws ParserException;

    void close();
}
