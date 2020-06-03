package com.nan.weixin.face;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nan.weixin.util.format.Base64Util;
import com.nan.weixin.util.format.FileUtil;
import com.nan.weixin.util.format.GsonUtils;
import com.nan.weixin.util.format.HttpUtil;
import com.nan.weixin.util.function.AuthService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FaceMix {

    private static String faceMerge(String filePath1,String filePath2) throws IOException {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v1/merge";
        byte[] bytes1 = FileUtil.readFileByBytes(filePath1);
        String image1 = Base64Util.encode(bytes1);
        byte[] bytes2 = FileUtil.readFileByBytes(filePath2);
        String image2 = Base64Util.encode(bytes2);
        try {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> image_templateMap = new HashMap<>();
            image_templateMap.put("image", image1);
            image_templateMap.put("image_type", "BASE64");
            image_templateMap.put("quality_control", "NONE");
            map.put("image_template", image_templateMap);
            Map<String, Object> image_targetMap = new HashMap<>();
            image_targetMap.put("image", image2);
            image_targetMap.put("image_type", "BASE64");
            image_targetMap.put("quality_control", "NONE");
            map.put("image_target", image_targetMap);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getFixFace();;

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResultFilePath(String filePath1,String filePath2 ) throws IOException {
        String s = FaceMix.faceMerge(filePath1,filePath2);
        JSONObject jsonObject = JSON.parseObject(s);
        String result = jsonObject.getString("result");
        JSONObject jsonObject1 = JSON.parseObject(result);
        String merge_image = jsonObject1.getString("merge_image");
        System.out.println(merge_image);
        String filePath = "C:\\Users\\hasee\\Desktop\\face_files\\fix_"+UUID.randomUUID().toString()+".jpg"; //生成的新文件
        Base64Util.decode(merge_image,filePath);
        return filePath;
    }

    public static void main(String[] args) throws IOException {
        String s1= "C:\\Users\\hasee\\Desktop\\face_files\\face1.jpg";
        String s2= "C:\\Users\\hasee\\Desktop\\face_files\\face2.jpg";
        String resultFilePath = getResultFilePath(s1, s2);
        System.out.println(resultFilePath);
    }

}
