package org.lrj.code.fetchRobot.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Synchronized;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation implements Runnable{

    private List<Map<String,String>> list;

    @Override
    public synchronized   void  run() {
            String director = "/Users/lanruijiang/Desktop/document/fiction/fdzhw/";
            String baseurl = "https://www.zwwx.org";
            String captrueurl = "https://www.zwwx.org/book/16/16560/";
            System.out.println(Thread.currentThread().getName());
            FetchRobot.create(list, director, baseurl, captrueurl);

    }
}
