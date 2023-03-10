package com.meew.overparser.parser;

import com.meew.overparser.data.Hero;

import java.util.List;

public interface WikiParser {


    public abstract void close();

    public abstract void search(String query);

    public abstract void clickLink(String linkText);

    public abstract String getTitle();

    public abstract String getContent();

    public abstract String getLinks();

    public abstract void parse();

}
