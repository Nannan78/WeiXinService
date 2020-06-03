package com.nan.weixin.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

@XStreamAlias("xml")
public class ImageMessage extends BaseMessage {

    @XStreamAlias("Image")
    private InnerImageMessage image;

    public ImageMessage(Map<String, String> map, InnerImageMessage image) {
        super(map);
        this.setMsgType("image");
        this.image = image;
    }

    public InnerImageMessage getImage() {
        return image;
    }

    public void setImage(InnerImageMessage image) {
        this.image = image;
    }
}
