package com.nan.elasticsearch.controller;


import com.nan.elasticsearch.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ResearchController {
    @Autowired
    ContentService contentService;


    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String,Object>> search(
            @PathVariable("keyword") String keyword,
            @PathVariable("pageNo") int pageNo,
            @PathVariable("pageSize") int pageSize) throws IOException {
        return  contentService.searchPageHighlight(keyword,pageNo,pageSize);

    }


    @GetMapping("/searchsort/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String,Object>> searchBysort(
            @PathVariable("keyword") String keyword,
            @PathVariable("pageNo") int pageNo,
            @PathVariable("pageSize") int pageSize) throws IOException {
        return  contentService.searchPageHighlightByTime(keyword,pageNo,pageSize);

    }
}
