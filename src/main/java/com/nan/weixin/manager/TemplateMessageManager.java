package com.nan.weixin.manager;

import com.nan.weixin.util.format.HttpUtil;
import com.nan.weixin.util.function.GetConAccessToken;

public class TemplateMessageManager {

    private static final String TemplateId="-Qkn2wWgnsfszxZEUfHsWpoiJMRvk1mzo8bkVKFLrwQ";

    public static void main(String[] args) throws Exception {
        /*String industry = getIndustry();
        System.out.println(industry);*/
        String s = sendTemplateMessage();
        System.out.println(s);
    }

    public static String setIndustry() throws Exception {
        String at= GetConAccessToken.getAccessToken();
        String url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry";
        String data="{\n" +
                "    \"industry_id1\":\"1\",\n" +
                "    \"industry_id2\":\"2\"\n" +
                "}";
        String post = HttpUtil.post(url, at, data);
        return post;
    }

    public static String getIndustry() throws Exception {
        String at= GetConAccessToken.getAccessToken();
        String url="https://api.weixin.qq.com/cgi-bin/template/get_industry";
        String get = HttpUtil.get(url, at);
        return get;
    }

    public static String sendTemplateMessage() throws Exception {
        String url="https://api.weixin.qq.com/cgi-bin/message/template/send";
        String at=GetConAccessToken.getAccessToken();
        String data="{\n" +
                "           \"touser\":\"o5OKlwt-bGrGh1guJqiPaH2huuKA\",\n" +
                "           \"template_id\":\"-Qkn2wWgnsfszxZEUfHsWpoiJMRvk1mzo8bkVKFLrwQ\",\n" +
                "           \"url\":\"http://www.baidu.com\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"你有新的反馈信息！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"company\":{\n" +
                "                       \"value\":\"字节舞动\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"time\": {\n" +
                "                       \"value\":\"2020-02-20\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"result\": {\n" +
                "                       \"value\":\"恭喜你被录取啦！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"请和本公司人事联系确认！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }\n";
        String post = HttpUtil.post(url, at, data);
        return post;
    }




}
