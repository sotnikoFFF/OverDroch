package com.meew.overparser.parser;

import com.meew.overparser.data.Hero;
import com.meew.overparser.parser.exceptions.ParserException;

import java.util.List;

public interface WikiParser {


    public abstract void close();

    public abstract void search(String query)throws ParserException;

    public abstract void clickLink(String linkText)throws ParserException;

    public abstract String getTitle()throws ParserException;

    public abstract String getContent()throws ParserException;

    public abstract String getLinks()throws ParserException;

    public abstract void parse()throws ParserException;


}
