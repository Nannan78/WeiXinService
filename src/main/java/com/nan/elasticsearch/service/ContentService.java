package com.nan.elasticsearch.service;


import com.alibaba.fastjson.JSON;
import com.nan.elasticsearch.entity.Page;
import com.nan.elasticsearch.util.GetNewHtml;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.elasticsearch.common.text.Text;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentService {



    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public Boolean parseContent() throws IOException, ParseException {
        ArrayList<Page> contents = GetNewHtml.getNewsDetail();


        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");



        for (int i = 0; i < contents.size(); i++) {
            bulkRequest.add(new IndexRequest("news")
                    .source(JSON.toJSONString(contents.get(i)), XContentType.JSON));
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();

    }



    public List<Map<String,Object>> searchPageHighlight(String keyword, int pageNo, int pageSize) throws IOException {
        if(pageNo<1){
            pageNo=1;
        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest(("news"));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //去重
        searchSourceBuilder.collapse(new CollapseBuilder("newsTitle.keyword"));

        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        //精准匹配
        QueryBuilder matchQueryBuilder= QueryBuilders.matchQuery("newsTitle",keyword);
        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field("newsTitle");
        highlightBuilder.field("newsBody");
        //关闭多个高亮
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {

            //解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("newsTitle");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();//原来的结果


            //用高亮字段替换我们原来的字段
            if(title!=null){
                Text[] fragments = title.fragments();
                String newTitle="";
                for (Text fragment : fragments) {
                    newTitle+=fragment;
                }
                sourceAsMap.put("newsTitle",newTitle);
            }

            Map<String, HighlightField> highlightFields1 = hit.getHighlightFields();
            HighlightField newsBody = highlightFields.get("newsBody");
            Map<String, Object> sourceAsMap1 = hit.getSourceAsMap();//原来的结果
            if(newsBody!=null){
                Text[] fragments = newsBody.fragments();
                String newBody="";
                for (Text fragment : fragments) {
                    newBody+=fragment;
                }
                sourceAsMap.put("newsBody",newBody);
            }

            list.add(sourceAsMap);
        }

        return list;


    }
    public List<Map<String,Object>> searchPageHighlightByTime(String keyword,int pageNo,int pageSize) throws IOException {
        if(pageNo<1){
            pageNo=1;
        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest(("news"));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //去重
        searchSourceBuilder.collapse(new CollapseBuilder("newsTitle.keyword"));

        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        SortBuilder sortBuilder = SortBuilders.fieldSort("newsDate2")
                .order(SortOrder.DESC);
        searchSourceBuilder.sort(sortBuilder);


        //精准匹配
        QueryBuilder matchQueryBuilder= QueryBuilders.matchQuery("newsTitle",keyword);
        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field("newsTitle");
        highlightBuilder.field("newsBody");
        //关闭多个高亮
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {

            //解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("newsTitle");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();//原来的结果


            //用高亮字段替换我们原来的字段
            if(title!=null){
                Text[] fragments = title.fragments();
                String newTitle="";
                for (Text fragment : fragments) {
                    newTitle+=fragment;
                }
                sourceAsMap.put("newsTitle",newTitle);
            }

            Map<String, HighlightField> highlightFields1 = hit.getHighlightFields();
            HighlightField newsBody = highlightFields.get("newsBody");
            Map<String, Object> sourceAsMap1 = hit.getSourceAsMap();//原来的结果
            if(newsBody!=null){
                Text[] fragments = newsBody.fragments();
                String newBody="";
                for (Text fragment : fragments) {
                    newBody+=fragment;
                }
                sourceAsMap.put("newsBody",newBody);
            }

            list.add(sourceAsMap);
        }

        return list;


    }
}
