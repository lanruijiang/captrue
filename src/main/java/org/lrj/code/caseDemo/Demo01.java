package org.lrj.code.caseDemo;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class Demo01 {
    public static void main(String[] args) throws IOException, ParseException {
//        get("https://www.doucehua.com/xs/131279/83931809.html");
        get("https://vipreader.qidian.com/chapter/1035362595/742244369/");

    }

    public static void get(String url) throws IOException, ParseException {

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        System.out.println(content);
    }


}