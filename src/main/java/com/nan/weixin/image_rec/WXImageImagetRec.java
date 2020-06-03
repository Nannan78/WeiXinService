package com.nan.weixin.image_rec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WXImageImagetRec {

    public static String getImageInfo(String filePath){
        String s = InputImagePath.advancedGeneral(filePath);
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(s);
        String words_result = jsonObject.getString("result");
        System.out.println(words_result);
        JSONArray objects = JSON.parseArray(words_result);
        String text="";
        Object o = objects.get(0);
        String s1 = JSON.toJSONString(o);
        JSONObject jsonObject1 = JSON.parseObject(s1);
        String word = jsonObject1.getString("keyword");
        text+=word;
        return text;
    }


}
