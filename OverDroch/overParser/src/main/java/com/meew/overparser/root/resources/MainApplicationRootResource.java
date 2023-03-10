package com.meew.overparser.root.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.meew.overparser.data.Hero;
import com.meew.overparser.data.Info;
import com.meew.overparser.data.InfoType;
import com.meew.overparser.parser.InfoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RestController
public class MainApplicationRootResource {

    Logger logger = LoggerFactory.getLogger(MainApplicationRootResource.class);
    private Info info;
    private Properties properties;


    private InfoProvider infoProvider;

    public MainApplicationRootResource(){
    }

    @GetMapping("/info1")
    public String mainInfo(){
        System.out.println(properties);
        return info.toString();
    }


    @GetMapping("/wiki/information")
    public Object wikiInfo(){
        return infoProvider.getAllContent();
    }

    @GetMapping("/{type}")
    public Object heroes(@PathVariable String type) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(infoProvider.getAllContent(InfoType.valueOf(type)));
        return json;
    }

    @GetMapping("/{type}/{name}")
    public String hero(@PathVariable String type, @PathVariable String name) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(infoProvider.getAllContent(InfoType.valueOf(type), name));
        return json ;
    }




    @GetMapping("/wiki/information/links")
    public String wikiLinks(){
        return infoProvider.getLinks().toString();
    }


    public void setProperties(Properties properties) {
        this.properties=properties;
    }

    public Info getInfo() {
        return this.info;
    }
    @Autowired
    public void setInfo(Info info) {
        this.info=info;
    }
@Autowired
    public void setInfoProvider(InfoProvider infoProvider) {
        this.infoProvider = infoProvider;
    }

//    @Autowired
//    public void setDataHoler(Holder dataHoler) {
//        this.dataHoler = dataHoler;
//    }
}
