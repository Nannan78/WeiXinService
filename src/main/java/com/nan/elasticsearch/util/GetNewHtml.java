package com.nan.elasticsearch.util;


import com.nan.elasticsearch.entity.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class GetNewHtml {
    public static void main(String[] args) throws IOException, ParseException {
       ArrayList<Page> newsDetail = GetNewHtml.getNewsDetail();
        System.out.println("=======================================");
        System.out.println(newsDetail.size());
        for (Page page : newsDetail) {
            System.out.println(page);
        }



    }

  /*  public static void parseBililong() throws IOException {
        String url = "https://www.hainanu.edu.cn/stm/vnew/shtml_liebiao.asp@bbsid=95.shtml";
        Document document = Jsoup.parse(new URL(url), 30000);

        Elements lis = document.getElementsByTag("li");
        Elements elementsByClass = document.getElementsByClass("news-list2");
        int i=0;
        for (Element byClass : elementsByClass) {
            Page page = new Page();
            Elements li1 = byClass.getElementsByTag("li");
            for (Element li : li1) {
                //网址
                System.out.println("https://www.hainanu.edu.cn"+li.getElementsByTag("a").attr("href"));
                String newsUrl="https://www.hainanu.edu.cn"+li.getElementsByTag("a").attr("href");
                page.setNewsUrl(newsUrl);

                System.out.println(li.getElementsByClass("news-title").text());//标题
                page.setNewsTitle(li.getElementsByClass("news-title").text());

                System.out.println(li.getElementsByTag("span").text());//新闻发布时间
                page.setNewsDate(li.getElementsByTag("span").text());

                //正文
                Document innerdocument = Jsoup.parse(new URL(newsUrl), 30000);
                System.out.println(innerdocument.getElementsByClass("neirong").text());
                page.setNewsBody(innerdocument.getElementsByClass("neirong").text());

                //随机ID
                System.out.println(UUID.randomUUID());
                page.setNewsID(UUID.randomUUID().toString());

                //创建索引时间
                System.out.println(new Date());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                page.setIndexTime(dateFormat.format(new Date()));

                //新闻发布时间二
                System.out.println(li.getElementsByTag("span").text());//新闻发布时间
                page.setNewsDate2(li.getElementsByTag("span").text());

            }
        }



    }*/
    private static Pattern NUMBER_PATTERN = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{2}:\\d{1,2}:\\d{1,2}");
    public static ArrayList<Page>  getNewsDetail() throws IOException, ParseException {

        ArrayList<Page> pages = new ArrayList<>();
        String url="";
        for(int i=0;i<10;i++){
            if(i == 0){
                url = "https://www.hainanu.edu.cn/stm/vnew/shtml_liebiao.asp@bbsid=95.shtml";
            }else{
                 url = "https://www.hainanu.edu.cn/stm/vnew/shtml_liebiao.asp@bbsid=95&pa="+i+".shtml";
            }

            Document document = Jsoup.parse(new URL(url), 1000000);

            Elements lis = document.getElementsByTag("li");
            Elements elementsByClass = document.getElementsByClass("news-list2");


            for (Element byClass : elementsByClass) {

                Elements li1 = byClass.getElementsByTag("li");
                for (Element li : li1) {
                    //网址
                    Page page = new Page();
                    String newsUrl="https://www.hainanu.edu.cn"+li.getElementsByTag("a").attr("href");
                    page.setNewsUrl(newsUrl);


                    page.setNewsTitle(li.getElementsByClass("news-title").text());




                    //正文
                    Document innerdocument = Jsoup.parse(new URL(newsUrl), 1000000);
                    page.setNewsBody(innerdocument.getElementsByClass("neirong").text());

                    //随机ID
                    page.setNewsID(UUID.randomUUID().toString());

                    //创建索引时间
                    page.setDocTime(new Date());

                    //新闻发布显示时间一
                    page.setNewsDate(li.getElementsByTag("span").text());
                    //新闻发布具体时间二
                    //有的新闻没有具体发布时间，就用大致的时间了
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
                    page.setNewsDate2(dateFormat2.parse(li.getElementsByTag("span").text()));
                    pages.add(page);
                }
            }
        }

        return pages;
    }
}
