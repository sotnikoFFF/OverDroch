package com.meew.overparser.parser;

import com.meew.overparser.data.Map;

import java.util.List;

public interface MapParser {

    List<Map> getAllMaps();

    Map getMap(String name);
}
