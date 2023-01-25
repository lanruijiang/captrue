package org.lrj.code.fetchRobot;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CaptureArticleDAO_Impl implements CaptureArticleDAO {
    private HttpGet httpGet;
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse httpResponse;
    private HttpEntity entity;
    private PoolingHttpClientConnectionManager connectionManager;
    private RequestConfig requestConfig;

    @Override
    public List<Article> getCaptureArticle(String url, String baseurl) {
        String content = null;
        httpGet = new HttpGet(url);
        httpClient = HttpClients.createDefault();
        try {
            httpResponse = httpClient.execute(httpGet);
            entity = httpResponse.getEntity();

            content = EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Document document = Jsoup.parse(content);
        Elements elements = document.getElementsByTag("dd");

        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String title = element.select("a").text();
            String hrefUrl = baseurl + element.select("a").attr("href");
            Article article = new Article(0, title, hrefUrl);
            articles.add(article);
        }
        return articles;
    }

    @Override
    public boolean createArticleToDisk(Article article) {
        boolean b = true;
        String url = article.getArticleUrl();
        httpGet = new HttpGet(url);
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.ofDays(5000)).build());
        connectionManager.setDefaultMaxPerRoute(100);
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000,
                TimeUnit.MILLISECONDS).setConnectTimeout(5000, TimeUnit.MILLISECONDS).setResponseTimeout(10000, TimeUnit.MILLISECONDS).build();

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        String content = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(content);
        Element element = document.getElementById("content");
        FileWriter out = null;
        try {
            String title = article.getAritcleTitle();
            out = new FileWriter(Base.director + title + ".txt");
            out.write(element.text());
            out.flush();
            out.close();
            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }
}
