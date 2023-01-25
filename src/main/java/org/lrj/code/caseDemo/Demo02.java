package org.lrj.code.caseDemo;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.http.HttpClient;

public class Demo02 {
    public static void main(String[] args) throws IOException, ParseException {
//        get("https://www.doucehua.com/xs/131279/83931809.html");
//          get("https://www.zwwx.org/book/117/117441/644829.html");

    }

    public static void get(String url , String title)  {

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpEntity entity = httpResponse.getEntity();
        String content = null;
        try {
            content = EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(content);
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
//        System.out.println(element.text());

//        String  title =  document.getElementsByTag("h1").text();


        FileWriter out = null;
        try {
            out = new FileWriter("/Users/lanruijiang/Desktop/document/fiction/ttjx/"+title+".txt");
            out.write(element.text());
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
