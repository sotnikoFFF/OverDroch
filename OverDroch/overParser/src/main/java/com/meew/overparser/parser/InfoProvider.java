package com.meew.overparser.parser;

import com.meew.overparser.data.InfoType;

public interface InfoProvider {


    public Object getAllContent();
    public Object getAllContent(InfoType type);

    String getLinks();

    Object getAllContent(InfoType type, String name);
}
