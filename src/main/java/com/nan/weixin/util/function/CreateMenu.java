package com.nan.weixin.util.function;




import com.alibaba.fastjson.JSON;
import com.nan.weixin.entity.button.*;
import com.nan.weixin.util.format.HttpUtil;
import com.nan.weixin.util.format.JsonFormatTool;

public class CreateMenu {
    public static void main(String[] args) throws Exception {
        Button button = new Button();
        //第一个一级菜单
        button.getButton().add(new ViewButton("海大时事搜索","http://nanweixin.free.idcfengye.com/index"));
        //第二个一级菜单
        button.getButton().add(new ClickButton("开启/关闭语音聊天","voiceTalk"));
        //第三个一级菜单
        SubButton subButton = new SubButton("图像功能");
        //第三个一级菜单的子菜单
       /* subButton.getSub_button().add(new ClickButton("点击","31"));*/
        subButton.getSub_button().add(new PhotoOrAlbumButton("识别文字","32"));
        subButton.getSub_button().add(new PhotoOrAlbumButton("识别图像","33"));
        subButton.getSub_button().add(new PhotoOrAlbumButton("人像合成（两种）","34"));
        button.getButton().add(subButton);

        String s = JSON.toJSONString(button);

        System.out.println(GetConAccessToken.getAccessToken());
        System.out.println(JsonFormatTool.formatJson(s));
        String post = HttpUtil.post(" https://api.weixin.qq.com/cgi-bin/menu/create", GetConAccessToken.getAccessToken(), s);


    }
}
