package com.nan.weixin.util.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nan.weixin.util.format.HttpUtil;

public class GetQrCodeTicket {
    public static void main(String[] args) throws Exception {
        String qrCodeTicket = getQrCodeTicket();
        System.out.println(qrCodeTicket);

    }
    public static String getQrCodeTicket() throws Exception {
        String url="https://api.weixin.qq.com/cgi-bin/qrcode/create";
        String accessToken=GetConAccessToken.getAccessToken();
        String data="{\"expire_seconds\": 6040, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"nan\"}}}";
        String post = HttpUtil.post(url, accessToken, data);
        JSONObject jsonObject = JSON.parseObject(post);
        String ticket = jsonObject.getString("ticket");
        return ticket;
    }
}
