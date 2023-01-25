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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Demo03 {
    public static void main(String[] args) {
      Map<String,String> map =   Demo03.getTitleAndAddress("https://www.zwwx.org/book/117/117441/");
      System.out.println(map);

    }

    public static Map<String,String> getTitleAndAddress(String url)  {

        Map<String,String> map = new HashMap<>();
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
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByTag("dl");
        Element root = elements.get(0);
        Elements rootElements = root.children();

        for(int i = 0 ; i < rootElements.size() ; i++){
            Element element = rootElements.get(i);
            String title = element.select("a").text();
            String href ="https://www.zwwx.org/" + element.select("a").attr("href");
            map.put(title,href);
        }

        return  map;


    }

}
