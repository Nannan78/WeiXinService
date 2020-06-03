package com.nan.weixin.util.function;

import com.nan.weixin.util.format.HttpUtil;

public class GetUserInfo {
    public static void main(String[] args) throws Exception {
        String userInfo = getUserInfo("o5OKlwt-bGrGh1guJqiPaH2huuKA");
        System.out.println(userInfo);
    }

    public static String getUserInfo(String userId) throws Exception {
        String url="https://api.weixin.qq.com/cgi-bin/user/info";
        String params="&openid="+userId+"&lang=zh_CN";
        String s = HttpUtil.get(url, params);
        return s;
    }
}
