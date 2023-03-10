package com.meew.overparser.data;

import ch.qos.logback.core.joran.sanity.Pair;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class AbilityConnectionInfo {
    Set<Ability> abilityAbilitySet;
    String description;
}
