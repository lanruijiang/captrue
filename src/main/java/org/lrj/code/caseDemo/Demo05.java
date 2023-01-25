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
import java.util.*;

public class Demo05 {
    public static void main(String[] args) throws IOException {
        List<String> list = Demo05.getTitle("https://www.zwwx.org/book/0/382/");
        System.out.println(list.size());
        Demo05.write(list);

    }

    public static void write(List<String> list) throws IOException {

        List<String> letters = new ArrayList<>();
        letters.add("www.ZWwx.ORG"); // 下载爱阅小说app，阅读最新章节内容无广告免费
        letters.add("下载爱阅小说app，阅读最新章节内容无广告免费 ");

        FileWriter out = new FileWriter("/Users/lanruijiang/Desktop/document/fiction/ttjx/ttjx.txt");
        BufferedWriter fileWriter = new BufferedWriter(out);
        for (String title : list) {

            File file = new File("/Users/lanruijiang/Desktop/document/fiction/ttjx/" + title + ".txt");
            if (file.exists()) {

                StringBuffer stringBuffer = new StringBuffer();
                FileReader fileReader = new FileReader(file);
                int i = fileReader.read();
                while (i != -1) {
                    char c = (char) i;
                    stringBuffer.append(c);
                    i = fileReader.read();
                }

                for (String letter : letters) {
                    String result = stringBuffer.toString().replaceAll(letter, "");
                    stringBuffer = new StringBuffer(result);
                }
                int index = stringBuffer.lastIndexOf("想要看最新章节内容");
                stringBuffer.replace(index, stringBuffer.length(), "");
                fileWriter.write(title);
                fileWriter.newLine();
                fileWriter.write(stringBuffer.toString());
                fileWriter.newLine();
                fileWriter.flush();
                fileReader.close();
            }
        }
        fileWriter.flush();
        fileWriter.close();

    }

    public static List<String> getTitle(String url) {
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
        Elements elements = document.getElementsByTag("dd");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String title = element.select("a").text();
            list.add(title);

        }

        return list;


    }

}
