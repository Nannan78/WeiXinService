package com.nan;

import com.nan.elasticsearch.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;

@SpringBootTest
class WeixinApplicationTests {


    @Autowired
    private ContentService contentService;

    @Test
    void contextLoads() {
      /*  Map<String,String> map=new HashMap<String,String>();
        map.put("ToUserName","to");
        map.put("FromUserName","from");
        map.put("CreateTime",System.currentTimeMillis()/1000+"");
        NewsMessage news=new NewsMessage(map,new ArrayList<ArticleMessage>());
        System.out.println(news);*/
    }

    @Test
    void gettoken() throws IOException {
        //33_g391Uj3I4JHqc3erh96iH6e3dqn2J1-8PK2ICRe3HdVMmciX0MVFcevbnNVQ-uCuOI2ixAG6jtz8KiOpq8xR382eofg_WcTmFBzx3H8YGgPTEZD-NqmcYzbeoUKHTK4HZtn-m6aq49L49pWpOMAaAEAUSG
      /*  System.out.println(GetConAccessToken.getAccessToken());*/

    }
    @Test
    void testbutton(){
      /*  Button button = new Button();
        //第一个一级菜单
        button.getButton().add(new ViewButton("一级浏览","www.baidu.com"));
        //第二个一级菜单
        button.getButton().add(new ViewButton("一级浏览","www.baidu.com"));
        //第三个一级菜单
        SubButton subButton = new SubButton("有此菜单");
        //第三个一级菜单的子菜单
        subButton.getSub_button().add(new PhotoOrAlbumButton("传图","31"));
        subButton.getSub_button().add(new PhotoOrAlbumButton("传图","31"));
        subButton.getSub_button().add(new ViewButton("网易","news.163.com"));
        button.getButton().add(subButton);

        String s = JSON.toJSONString(button);
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(jsonObject.toJSONString());*/
    }
    @Test
    void imageRec(){
     /*   String text = WXImageTextRec.getText("C:\\Users\\hasee\\Desktop\\text1.png");*/

    }

    @Test
    void getUseraccess() throws Exception {
        /*System.out.println("test");*/
    }

    @Test
    void testtrim() throws Exception {
       /* String  s="{CreateTime=1590198316, EventKey=33, Event=pic_photo_or_album, ToUserName=gh_37ffbe3c5e11, FromUserName=o5OKlwt-bGrGh1guJqiPaH2huuKA, MsgType=event, SendPicsInfo=3\n" +
                "34f9ed5c82f1d97a3393f73147f68d6c\n" +
                "\n" +
                "c828de3f2569f873a664f1f3e894d304\n" +
                "\n" +
                "3137c02278e249819554f0ff705ca8e3\n" +
                "\n" +
                "\n" +
                "}";
        int msgType = s.indexOf("SendPicsInfo");
        String substring = s.substring(msgType+13,msgType+14);
        System.out.println(substring);*/
    }
    @Test
    void testgetnews() throws IOException, ParseException {
    //  这样报错，是因为需要@Autowired一个ContentService,而不是new一个
        /*Boolean aBoolean = new ContentService().parseContent();
        System.out.println(aBoolean);*/
       /* Boolean aBoolean = contentService.parseContent();
        System.out.println(aBoolean);*/
    }

}


//
