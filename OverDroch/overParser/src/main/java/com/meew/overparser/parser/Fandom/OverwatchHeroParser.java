package com.meew.overparser.parser.Fandom;

import com.meew.overparser.data.Ability;
import com.meew.overparser.data.AbilityConnectionInfo;
import com.meew.overparser.data.Hero;
import com.meew.overparser.parser.HeroParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.util.ArrayUtils;


import java.util.List;
import java.util.stream.Collectors;

public class OverwatchHeroParser implements HeroParser {

    Logger logger = LoggerFactory.getLogger(OverwatchHeroParser.class);
    private String url = "https://overwatch.fandom.com/wiki/";
    private WebDriver driver;

    public OverwatchHeroParser() {
        this.driver = new ChromeDriver();
        this.driver.get(url + "Heroes");
    }

    @Override
    public String getContent() {
        return driver.getPageSource();
    }

    @Override
    public Hero getHero(String name) {
        driver.get(url +name);
        Hero hero = new Hero();
        hero.setAbilities(getHeroAbilities(name));
        hero.setName(name);
        //TODO: hero.setAbilityConnectionInfoMap();
        //TODO: hero.setDescription();
        return hero;
    }

    private  List<Ability> getHeroAbilities(String name) {

        // Navigate to the D.Va page on the Overwatch Fandom Wiki
        driver.get("https://overwatch.fandom.com/wiki/"+name+"#Abilities");

        // Find the section containing D.Va's abilities
//        WebElement abilitiesSection = driver.findElement(By.id("Abilities"));
//        logger.info(abilitiesSection.getAriaRole());
//

        // Find all of the ability sections within the abilities section
        List<WebElement> abilitySections =  driver.findElements(By.className("ability_box"));
        logger.info("abilities text");
        List<Ability> abilities= new ArrayList<>();
        abilitySections.forEach(a->{
            Ability ability = new Ability();
            String nameAndButton = a.findElement(By.className("abilityHeader")).getText();
            ability.setName(nameAndButton);
            //#mw-content-text > div > div:nth-child(9) > div > div.ability_box > div:nth-child(3)
            String s =  a.findElement(By.cssSelector("div.ability_details_main > div.ability_box > div:nth-child(3)")).getText();
            String[] ablist = s.split("\n");
            HashMap<String,String> abHash = new HashMap<>();
            for(String ab : ablist){
                logger.info(ab);
                if(ab.contains(":")) {
                    String[] splitted = ab.split(": ");
                    if(splitted.length==2) {
                        abHash.put(splitted[0], splitted[1] == null ? "" : splitted[1]);
                    }
                }else{
                    ability.setDescription(ab);
                }
            }
            ability.setInfo(abHash);
                logger.info("Ability Header: " + ability.getName() );
                logger.info("Ability INFO: " + ability.getInfo());

            abilities.add(ability);
        });
        driver.navigate().back();
        return abilities;

    }

    @Override
    public List<Hero> getAllHeroes() {
        List<WebElement> heroElements = driver.findElements(By.cssSelector("table#heroes > tbody > tr > td:nth-child(1) > div > a"));
        List<Hero> heroes = new ArrayList<>();

        for (WebElement heroElement : heroElements) {
            String name = heroElement.getText();
            heroes.add(getHero(name));
        }

        return heroes;
    }

    @Override
    public List<String> getAllHeroesNames() {
        List<WebElement> heroElements = driver.findElements(By.cssSelector("#mw-content-text > div > div.table-wide > div > table > tbody"));
        List<String> names = new ArrayList<>();
        logger.info(""+heroElements);
        for (WebElement heroElement : heroElements) {
            List<WebElement> heroElementss = heroElement.findElements(By.cssSelector("a:nth-child(3)"));
            heroElementss.remove(0);
            for(WebElement element : heroElementss){
                String name = element.getText();
                logger.info(name);
                names.add(name);//#mw-content-text > div > div.table-wide > div > table > tbody > tr:nth-child(2) > td:nth-child(2) > a:nth-child(3)
            }//#mw-content-text > div > div.table-wide > div > table > tbody > tr:nth-child(2) > td:nth-child(3) > a:nth-child(3)
        }
    return names;
    }

    @Override
    public void close() {
        driver.quit();
    }
}
