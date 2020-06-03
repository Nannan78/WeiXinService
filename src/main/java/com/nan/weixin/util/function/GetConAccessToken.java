package com.nan.weixin.util.function;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nan.weixin.entity.AccessToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetConAccessToken {
    //测试公众号
    private static final String APP_ID="wx20e774c6ee013f40";
    private static final String APP_SECRET="b20bbe15e1aaaf1cfcde0790414fff50";
  /*  private static final String APP_ID="wx34e197ad8b29843e";
    private static final String APP_SECRET="b4f2b53fc9616b37a6362fb40bef5172";*/
    private static final String GRANT_TPYE="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static AccessToken at;

    public static void main(String[] args) throws IOException {
        String accessToken = getAccessToken();
        System.out.println(accessToken);
    }
    private static String getAccessToken(String url) throws IOException {

        URL urlObject=new URL(url);
        URLConnection connection = urlObject.openConnection();
        InputStream in = connection.getInputStream();
        byte[] b=new byte[1024];
        int len;
        StringBuilder sb=new StringBuilder();
        while((len=in.read(b))!=-1){
            sb.append(new String(b,0,len));
        }
        return sb.toString();

    }
    private static void getToken() throws IOException {
        String url=GRANT_TPYE.replace("APPID",APP_ID).replace("APPSECRET",APP_SECRET);
        System.out.println(url);
        String accessToken = GetConAccessToken.getAccessToken(url);
        JSONObject jsonObject = JSON.parseObject(accessToken);
        String access_token = jsonObject.getString("access_token");
        String expires_in = jsonObject.getString("expires_in");
        at = new AccessToken(access_token, expires_in);
    }

    public static String getAccessToken() throws IOException {
        if(at==null||at.isExpired()){
            getToken();
        }
        return at.getAccessToken();
    }

}
