package com.nan.weixin.entity.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XStreamAlias("xml")
public class NewsMessage extends BaseMessage {

    @XStreamAlias("ArticleCount")
    private String articleCount;

    @XStreamAlias("Articles")
    private List<InnerArticleMessage> articles=new ArrayList<>();

    public String getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(String articleCount) {
        this.articleCount = articleCount;
    }

    public List<InnerArticleMessage> getArticles() {
        return articles;
    }

    public void setArticles(List<InnerArticleMessage> articles) {
        this.articles = articles;
    }

    public NewsMessage(Map<String, String> map, List<InnerArticleMessage> articles) {
        super(map);
        this.setMsgType("news");
        this.articleCount=articles.size()+"";
        this.articles=articles;
    }
}
