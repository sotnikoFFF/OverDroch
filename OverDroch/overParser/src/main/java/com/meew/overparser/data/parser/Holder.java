package com.meew.overparser.data.parser;

import com.meew.overparser.Mode;
import com.meew.overparser.data.Hero;
import com.meew.overparser.data.Map;
import com.meew.overparser.parser.Fandom.FandomParser;
import com.meew.overparser.parser.MainParser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Holder  {
    List<Hero> heroList;
    List<String> allHeroesNames;

    List<Map> mapList;
    String overwatchOverview;

    Object allContent;
}
