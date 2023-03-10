package com.meew.overparser.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class Map {
    String name;
    String pic;
    String description;
    List<Hero> heroes;
}
