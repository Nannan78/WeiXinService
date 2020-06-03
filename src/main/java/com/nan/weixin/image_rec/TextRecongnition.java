package com.nan.weixin.image_rec;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TextRecongnition {
    public static void main(String[] args) {
        String filePath="C:\\Users\\hasee\\Desktop\\text1.png";
        String s = InputITextImagePath.advancedGeneral(filePath);
        JSONObject parseObject = JSON.parseObject(s);

        String words_result = parseObject.getString("words_result");


        JSONArray objects = JSON.parseArray(words_result);

        String text="";
         for(int i=0;i<objects.size();i++){
             Object o = objects.get(i);
             String s1 = JSON.toJSONString(o);
             JSONObject jsonObject = JSON.parseObject(s1);
             String word = jsonObject.getString("words");
             text+=word;

         }
        System.out.println(text);
    }
}
