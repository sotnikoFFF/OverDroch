package com.meew.overparser.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
@Setter
@Getter
public class Ability {
//    int powerWeight;
    String name;
    HashMap<String,String> info;
    String description;
    String button;
    public void addConnection(AbilityConnectionInfo connectionInfo) {
        connectionInfo.abilityAbilitySet.add(this);
    }
}
