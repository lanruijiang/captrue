package org.lrj.code.fetchRobot.thread;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareList {

    public static List<Map<String, String>> getTitleAndAddress(String captrueurl, String baseurl) {
        List<Map<String,String>> list = new ArrayList<>();
        Map<String, String> map = null;
        HttpGet httpGet = new HttpGet(captrueurl);
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
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByTag("dd");


        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String title = element.select("a").text();
            String href = baseurl + element.select("a").attr("href");
            map = new HashMap<String, String>();
            map.put(title, href);
            list.add(map);
        }

        return list;
    }

}
