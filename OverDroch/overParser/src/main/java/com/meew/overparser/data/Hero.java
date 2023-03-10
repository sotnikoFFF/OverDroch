package com.meew.overparser.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Hero {
    String name;
    List<Ability> abilities;
    AbilityConnectionInfo abilityConnectionInfoMap;
}
