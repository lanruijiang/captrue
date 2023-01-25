package org.lrj.code.fetchRobot;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lrj.code.caseDemo.Demo02;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FetchRobot {

    public static void get(String url, String title, String director) {
        HttpGet httpGet = new HttpGet(url);

//        CloseableHttpClient httpClient = HttpClients.createDefault();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.ofDays(5000)).build());
        connectionManager.setDefaultMaxPerRoute(100);


        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000,
                TimeUnit.MILLISECONDS).setConnectTimeout(5000, TimeUnit.MILLISECONDS).setResponseTimeout(10000, TimeUnit.MILLISECONDS).build();


        CloseableHttpClient httpClient = null;
        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
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
        Element element = document.getElementById("content");
        FileWriter out = null;
        try {
//            out = new FileWriter("/Users/lanruijiang/Desktop/document/fiction/ttjx/"+title+".txt");
            out = new FileWriter(director + title + ".txt");
            out.write(element.text());
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
//            String href ="https://www.zwwx.org" + element.select("a").attr("href");
            String href = baseurl + element.select("a").attr("href");
            map = new HashMap<String, String>();
            map.put(title, href);
            list.add(map);
        }
        System.out.println(list.size());
        return list;
    }

    /**
     * @param director   String director = "/Users/lanruijiang/Desktop/document/fiction/ttjx/";
     * @param baseurl    baseurl = "https://www.zwwx.org/
     * @param captrueurl captrueurl = "https://www.zwwx.org/book/0/382/"
     */
    public static void create(String director, String baseurl, String captrueurl) {
        List<Map<String, String>> list = getTitleAndAddress(captrueurl, baseurl);
//        map.forEach((title,url)->{
//            Demo02.get(url,title);
//        });
//        map.forEach((title,url)-> Demo02.get(url,title));
        File file = new File(director);
        File[] files = file.listFiles();
        List<String> listFile = new ArrayList<String>();
        if (files.length > 0) {
            for (File f : files) {
                listFile.add(f.getName());
            }
        }
//        for(int i = list.size()-1 ; i > (list.size() - 200) ; i--){
            for(int i = 0 ; i < list.size()  ; i++){

            Map<String,String> map = list.get(i);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String title = entry.getKey().trim();
                if(listFile.contains(title+".txt")){
                    continue;
                }
                String url = entry.getValue().trim();
                get( url,  title,  director);
            }
            System.out.println(i);
        }

    }

    public static void main(String[] args) {
        String director = "/Users/lanruijiang/Desktop/document/fiction/zgdsydx/";
        String baseurl = "https://www.zwwx.org";
        String captrueurl = "https://www.zwwx.org/book/75/75294/";
        FetchRobot.create(director,baseurl,captrueurl);
    }
}
