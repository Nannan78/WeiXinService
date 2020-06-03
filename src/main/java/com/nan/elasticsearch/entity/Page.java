package com.nan.elasticsearch.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    public  String newsUrl ;//新闻的网址
    public  String newsTitle;	//新闻标题
    public  String newsDate ;	//新闻发布的时间
    public  Date newsDate2;
    public  String newsBody ;	//新闻正文

    public  String newsID ;	//该新闻的ID
    public  Date docTime ;	//新闻被建立索引的时间
}
