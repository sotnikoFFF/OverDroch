package com.meew.overparser.root.resources;

import com.meew.overparser.data.Info;
import com.meew.overparser.data.parser.Holder;
import com.meew.overparser.parser.InfoProvider;
import com.meew.overparser.parser.MainParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String wikiInfo(){
        return infoProvider.getInfo();
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
