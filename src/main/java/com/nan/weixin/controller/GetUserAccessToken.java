package com.nan.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nan.weixin.entity.AccessToken;
import com.nan.weixin.util.format.HttpUtil;
import com.nan.weixin.util.format.JsonFormatTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserAccessToken {
    private static final String APP_ID="wx20e774c6ee013f40";
    private static final String APP_SECRET="b20bbe15e1aaaf1cfcde0790414fff50";
    private static final String GET_FIRST_SCCESS="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private static final String GET_INFO="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private static final String REFRESH_TOKEN="33_knoCQJlhMNa8ZK-_tq7zz17hi8xp_Xj5RsCBVdDy4J5Q26rT3_k4Gaf1gnb-yHVdHfOtPs_ufeGDAYNuzsYBN6tBSQFDYNSwbMUThplnM3Y";
    private static final String GET_NEW_SCCESS="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    private static AccessToken userat;


    @RequestMapping("/getUserAu")
    public String getUserAu(String code) throws Exception {
        String url=GET_FIRST_SCCESS.replace("APPID",APP_ID).replace("SECRET",APP_SECRET)
                .replace("CODE",code);
        String s = HttpUtil.get(url);
        //{"access_token":"33_KRXfMEv_jKKDuT36lMaRpfHsY7h3fEofPqXdV7TDZCLFxnt2oKCZo_y7l5OX58yh3Be81dv9o_amDGSWfUeHZMb4n7rKyfNH3eg7JeHSkME",
        // "expires_in":7200,
        // "refresh_token":"33_knoCQJlhMNa8ZK-_tq7zz17hi8xp_Xj5RsCBVdDy4J5Q26rT3_k4Gaf1gnb-yHVdHfOtPs_ufeGDAYNuzsYBN6tBSQFDYNSwbMUThplnM3Y",
        // "openid":"o5OKlwt-bGrGh1guJqiPaH2huuKA","scope":"snsapi_userinfo"}
        System.out.println(s);
        JSONObject jsonObject = JSON.parseObject(s);
        String access_token = jsonObject.getString("access_token");
        String expires_in = jsonObject.getString("expires_in");
        String open_id = jsonObject.getString("openid");
        userat = new AccessToken(access_token, expires_in);
        String infoUrl=GET_INFO.replace("ACCESS_TOKEN",access_token).replace("OPENID",open_id);
        String userInfo = HttpUtil.get(infoUrl);
        System.out.println(JsonFormatTool.formatJson(userInfo));
        return "yes";
    }

/*    private static void getNewToken() throws Exception {
        String url=GET_NEW_SCCESS.replace("APPID",APP_ID).replace("REFRESH_TOKEN",REFRESH_TOKEN);
        String accessToken = HttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(accessToken);
        String access_token = jsonObject.getString("access_token");
        String expires_in = jsonObject.getString("expires_in");
        userat = new AccessToken(access_token, expires_in);
    }

    public static String getUserAccessToken() throws Exception {
        if(userat.isExpired()){
            getNewToken();
        }
        System.out.println(userat);
        return userat.getAccessToken();
    }*/

}
