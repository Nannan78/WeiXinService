package com.nan.weixin.robot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nan.weixin.util.format.GsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HaiZhiConnection {
    private static final String url = "http://api.ruyi.ai/v1/message";

    private static final String app_key = "9aa281dd-ec2e-407d-85c8-ad0a0abd0824";

    private static final String user_id = UUID.randomUUID().toString();

    public static String getResponseByPost(String  q){
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("q", q);
            map.put("app_key", app_key);
            map.put("user_id", user_id);
            String param = GsonUtils.toJson(map);
            String result = HaiZhiZhiNengRobot.postHaiZhi(url,param);
            String text=parseText(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseText(String result) {
        JSONObject parseObject = JSON.parseObject(result);
        String robotWords = parseObject.getString("result");
        JSONObject jsonObject = JSON.parseObject(robotWords);
        String result1 = jsonObject.getString("intents");
        JSONArray objects = JSON.parseArray(result1);
        Object o1 = objects.get(0);

        String s = JSON.toJSONString(o1);
        JSONObject jsonObject2 = JSON.parseObject(s);
        String string = jsonObject2.getString("result");

        JSONObject jsonObject1 = JSON.parseObject(string);
        String text = jsonObject1.getString("text");

        return text;
    }

    public static String getResponseByGet(String  q){
        try {
            String result = HaiZhiZhiNengRobot.getHaiZhi(url,q,app_key,user_id);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
