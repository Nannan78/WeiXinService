package com.nan.weixin.controller;


import com.nan.weixin.util.function.WxTestCon;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

@RestController
public class Connection {


    private static int j=100;

    @GetMapping("/weixinconnnection")
    public String test(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        /*System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);*/
        if(WxTestCon.check(signature,timestamp,nonce)){
            System.out.println("接入成功");
            return echostr;
        }else{
            System.out.println("接入失败");
            return "false";
        }

    }

    @PostMapping("/weixinconnnection")
    public String getMessageAndSend( HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException, DocumentException, InterruptedException, TimeoutException {
       request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        //接收用户的消息并处理，将结果封装成一个map
        Map<String,String> map=HandleMessage.parseRequest(request.getInputStream());
        ExecutorService threadPool = Executors.newFixedThreadPool(9);

        if( map.get("MsgType").equals("image")) {
            threadPool.execute(()->{
                try {
                    Thread.sleep(j);
                    j+=100;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        String xmlResponse=HandleMessage.getResponse(map);
        System.out.println("response=>"+xmlResponse);
        return xmlResponse;

    }




}
