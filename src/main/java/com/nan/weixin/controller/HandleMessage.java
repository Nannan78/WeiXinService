package com.nan.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nan.weixin.entity.message.*;
import com.nan.weixin.face.FaceMix;
import com.nan.weixin.image_rec.WXImageImagetRec;
import com.nan.weixin.image_rec.WXImageTextRec;
import com.nan.weixin.robot.HaiZhiConnection;
import com.nan.weixin.util.function.DownImage;
import com.nan.weixin.util.function.HttpUploadAndDownloadFile;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class HandleMessage {
    //图片标志位，0表示正常传图，1表示智能识别文字，2表示智能识别图片
    private static int imageTextBit=0;
    private static int imageImageBit=0;
    private static int faceFixBit=0;
    private static String faceFilePath1="";
    private static String faceFilePath2="";
    //开启语音聊天
    private static int voiceTalk=0;
    public static String getResponse(Map<String, String> map) throws IOException, InterruptedException {
        BaseMessage msg= null;
        switch (map.get("MsgType")){
            case "text":
                msg=dealTextMessage(map);
                break;

            case "voice":
                  msg=dealVoiceMessage(map);
                break;
            //直接发送图片形式处理
            case "image":

                msg=dealImageMessage(map);
                break;

            case "news":
              /*  msg=dealNewsMessage(map);*/
                break;

            case "event":
                  msg=dealEvent(map);
                System.out.println(msg);
                break;

        }
        if(msg!=null){
            return beanToXml(msg);
        }
        return null;
    }

    private static BaseMessage dealVoiceMessage(Map<String, String> map) {
        System.out.println(voiceTalk);
        if(voiceTalk==0){
            return new TextMessage(map,"我不懂你在说什么，开启语音聊天我就能明白了");
        }
        String recognition = map.get("Recognition");
        if(recognition.equals("")){
            return new TextMessage(map,"我没听清楚，再说一遍吧");
        }
        String robotResponse = HaiZhiConnection.getResponseByPost(recognition);
        System.out.println(robotResponse);
        String text = HaiZhiConnection.parseText(robotResponse);
        TextMessage tm=new TextMessage(map,text);
        return tm;
    }

    private static BaseMessage dealImageMessage(Map<String, String> map) throws IOException, InterruptedException {

        System.out.println("IMAGE当前imageImageBit:"+imageImageBit);
        System.out.println("IMAGE当前imageTextBit:"+imageTextBit);
        System.out.println("IMAGE当前faceFixBit:"+faceFixBit);
        if(imageTextBit!=0){
            //处理需要智能识别文字的图片
            String picUrl = map.get("PicUrl");
            String fromUserName = map.get("FromUserName");
            String random = UUID.randomUUID().toString();
            DownImage.downImage(picUrl,fromUserName,random);
            String text = WXImageTextRec.getText("C:\\Users\\hasee\\Desktop\\WinXinImage\\"  +fromUserName+"_"+random+".jpg");
            System.out.println(text);
            imageTextBit--;
            if (text.equals("")) {
                text="这张照片里好像没文字诶";
            }
            return new TextMessage(map,text);

        }else if(imageImageBit!=0){
            //处理需要智能识别图像的图片
            String picUrl = map.get("PicUrl");
            String fromUserName = map.get("FromUserName");
            String random = UUID.randomUUID().toString();
            DownImage.downImage(picUrl,fromUserName,random);
            Thread.sleep(500);
            String info = WXImageImagetRec.getImageInfo("C:\\Users\\hasee\\Desktop\\WinXinImage\\"  +fromUserName+"_"+random+".jpg");
            System.out.println(info);
            imageImageBit--;
            return new TextMessage(map, "我觉得这应该是："+info);
        }else if(faceFixBit!=0){
            System.out.println("进入FaceMIx函数了，faceFixBit:"+faceFixBit);
            //处理需要合成的图片
            String picUrl = map.get("PicUrl");
            String fromUserName = map.get("FromUserName");
            String random = UUID.randomUUID().toString();
            DownImage.downImage(picUrl,fromUserName,random);
            if(faceFilePath1.equals("")){
                faceFilePath1="C:\\Users\\hasee\\Desktop\\WinXinImage\\"  +fromUserName+"_"+random+".jpg";
                faceFixBit--;
                return null;
            }
            faceFilePath2="C:\\Users\\hasee\\Desktop\\WinXinImage\\"  +fromUserName+"_"+random+".jpg";
            String resultFilePath = FaceMix.getResultFilePath(faceFilePath1, faceFilePath2);
            System.out.println(resultFilePath+":resultFilePath");
            String git = HttpUploadAndDownloadFile.uploadTemporyFile(resultFilePath, "image");
            JSONObject jsonObject = JSON.parseObject(git);
            String media_id = jsonObject.getString("media_id");
            faceFilePath1="";
            faceFilePath2="";
            faceFixBit--;
            return new ImageMessage(map, new InnerImageMessage(media_id));
        }else {
            //处理直接传输的图片
            String picUrl = map.get("PicUrl");
            String fromUserName = map.get("FromUserName");
            String createTime = map.get("CreateTime");
            DownImage.downImage(picUrl,fromUserName,createTime);
            return new ImageMessage(map, new InnerImageMessage("hvpss7VEUDxdQ_7awgThgKmFAuM2J6zPxU8maDHScfP-F0c9-AxNs_sARujWo7om"));
        }
    }


    //处理时间推送
    private static BaseMessage dealEvent(Map<String, String> map) {
        String event = map.get("Event");
        switch (event){
            case "CLICK":
                return dealClick(map);
            case "VIEW":
                return dealView(map);
            case "pic_photo_or_album":
                return dealpic_photo_or_album(map);
            case "subscribe":
                return dealsubscribe(map);


        }
        return null;
    }

    private static BaseMessage dealsubscribe(Map<String, String> map) {
        return new TextMessage(map,"欢迎关注阿南的公众号，各种有趣的功能任君使用！");
    }

    private static BaseMessage dealpic_photo_or_album(Map<String, String> map) {
        int msgType;
        String s="";
        String substring="";

        switch (map.get("EventKey")){
            case "32":
                s=map.toString();
                msgType = s.indexOf("SendPicsInfo");
                substring = s.substring(msgType+13,msgType+14);
                System.out.println(substring+"===========================================================");
                imageTextBit=Integer.parseInt(substring);
                break;
            case "33":
                s=map.toString();
                msgType = s.indexOf("SendPicsInfo");
                substring = s.substring(msgType+13,msgType+14);
                System.out.println(substring+"===========================================================");
                imageImageBit=Integer.parseInt(substring);
                break;
            case "34":
                s=map.toString();
                msgType = s.indexOf("SendPicsInfo");
                substring = s.substring(msgType+13,msgType+14);
                System.out.println(substring+"===========================================================");
                faceFixBit=Integer.parseInt(substring);
                break;

        }
        System.out.println("EVENT当前imageImageBit:"+imageImageBit);
        return new TextMessage(map,"为了提高准确率，请不要一次性发送太多图片哦");
    }

    private static BaseMessage dealView(Map<String, String> map) {

        return null;
    }

    private static BaseMessage dealClick(Map<String, String> map) {
        String key = map.get("EventKey");
        switch (key){
            case "voiceTalk":
                if(voiceTalk==0){
                    voiceTalk=1;
                    System.out.println("已经开启了语音聊天");
                    return new TextMessage(map,"已经开启了语音聊天，开始聊天吧！");
                }else{
                    voiceTalk=0;
                    System.out.println("已经关闭了语音聊天");
                    return new TextMessage(map,"已经关闭了语音聊天，下次再见咯！");
                }


        }
        return null;
    }

    private static BaseMessage dealNewsMessage(Map<String, String> map) {
        List<InnerArticleMessage> articles=new ArrayList<>();
        articles.add(new InnerArticleMessage("这是图文消息标题","这是介绍","http://mmbiz.qpic.cn/mmbiz_jpg/twOSD8gUt9liaqWjp7miaHUEMsWFY3R5lBCabWcpfLKEeOwgDm2LMbAjAG8vImicS8eBVamUooWS8ehYhSe9ibN7kw/0","www.baidu.com"));
        NewsMessage nm=new NewsMessage(map,articles);
        return nm;
    }




    public static BaseMessage dealTextMessage(Map<String, String> map) {
        if(map.get("Content").equals("登录")){
            TextMessage textMessage = new TextMessage(map,"点击<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx20e774c6ee013f40&" +
                    "redirect_uri=http://nanweixin.free.idcfengye.com/getUserAu&response_type=code&scope=snsapi_userinfo#wechat_redirect'>这里</a>登录");
            return textMessage;
        }
        if(map.get("Content").equals("海大新闻")){
            TextMessage textMessage = new TextMessage(map,"点击<a href='http://nanweixin.free.idcfengye.com/index'>这里</a>查看");
            return textMessage;
        }
        String robotResponse = HaiZhiConnection.getResponseByPost(map.get("Content"));
        System.out.println(robotResponse);
        String text = HaiZhiConnection.parseText(robotResponse);
        TextMessage tm=new TextMessage(map,text);
        return tm;
    }



    public static String beanToXml(BaseMessage msg) {
        XStream stream=new XStream();
        //需要配置相应类，才可以识别别名
        stream.processAnnotations(TextMessage.class);
        stream.processAnnotations(ImageMessage.class);
        stream.processAnnotations(VoiceMessage.class);
        stream.processAnnotations(InnerArticleMessage.class);
        stream.processAnnotations(NewsMessage.class);
        String xml=stream.toXML(msg);
        System.out.println(xml);
        return xml;
    }
    public static Map<String, String> parseRequest(InputStream is) throws DocumentException {
        Map<String, String> map = new HashMap<>();
        SAXReader reader=new SAXReader();
        Document read = reader.read(is);

        Element root = read.getRootElement();

        List<Element> elements = root.elements();
        for (Element element : elements) {
            map.put(element.getName(),element.getStringValue());
        }
        //输出XML转化为的Map对象
        System.out.println(map);
        return map;

    }
}
