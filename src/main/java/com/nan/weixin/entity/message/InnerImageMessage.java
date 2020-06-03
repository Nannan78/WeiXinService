package com.nan.weixin.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class InnerImageMessage {
    @XStreamAlias("MediaId")
    private String mediaId;

    public InnerImageMessage(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
