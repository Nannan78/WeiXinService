package com.nan.elasticsearch.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GetIndex {



    @RequestMapping("/index")
    public String getIndex1(){
        return "index";
    }


}
