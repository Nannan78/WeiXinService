package com.nan.weixin.image_rec;


import com.nan.weixin.util.format.Base64Util;
import com.nan.weixin.util.format.FileUtil;
import com.nan.weixin.util.format.HttpUtil;
import com.nan.weixin.util.format.JsonFormatTool;

import java.net.URLEncoder;

public class InputITextImagePath {
    public static String advancedGeneral(String file_path) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        try {
            // 本地文件路径
            String filePath = file_path;
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;



            // 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            // 默认有效时间30天，创建于5-18
            //String accessToken=AuthService.getAuthText();
            String accessToken = "24.0c39d1155f017af2ce8c89139b4e85d1.2592000.1592706807.282335-19978165";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(JsonFormatTool.formatJson(result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
