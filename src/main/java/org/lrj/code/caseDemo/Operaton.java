package org.lrj.code.caseDemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operaton {
    public static void main(String[] args) {
//        Map<String,String> map = Demo04.getTitleAndAddress("https://www.zwwx.org/book/117/117441/");
        Map<String,String> map = Demo04.getTitleAndAddress("https://www.zwwx.org/book/0/382/");

//        map.forEach((title,url)->{
//            Demo02.get(url,title);
//        });

//        map.forEach((title,url)-> Demo02.get(url,title));

        File file = new File("/Users/lanruijiang/Desktop/document/fiction/ttjx/");
        File[] files = file.listFiles();
        List<String> listFile = new ArrayList<String>();
        for(File f : files){
            listFile.add(f.getName());
        }

        int i = 0 ;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String title = entry.getKey().trim();

            if(listFile.contains(title+".txt")){
                continue;
            }
            String url = entry.getValue();
            i++;
            Demo02.get(url, title);
            System.out.println(i);

        }


    }
}
