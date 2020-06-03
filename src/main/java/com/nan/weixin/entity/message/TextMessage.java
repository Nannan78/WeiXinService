package com.nan.weixin.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;


@XStreamAlias("xml")
public class TextMessage extends BaseMessage {
    @XStreamAlias("Content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextMessage(Map<String,String> map,String content) {
        super(map);
        this.setMsgType("text");
        this.content=content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "content='" + content + '\'' +
                "} " + super.toString();
    }
}
