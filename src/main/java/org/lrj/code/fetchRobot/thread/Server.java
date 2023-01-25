package org.lrj.code.fetchRobot.thread;

import java.util.List;
import java.util.Map;
import java.util.Timer;

public class Server {

    public static void main(String[] args) {
        boolean b = false;
        String baseurl = "https://www.zwwx.org";
        String captrueurl = "https://www.zwwx.org/book/16/16560/";
        List<Map<String, String>> list = ShareList. getTitleAndAddress(captrueurl, baseurl);
//        for(int i = 0 ; i < 10 ; i++) {
            Operation operation = new Operation();
            operation.setList(list);
            Thread t = new Thread(operation);
//            Timer timer = new Timer();
//            timer.schedule(new TimeOutTask(t, timer), 60000);
            t.start();
//            int size = operation.getList().size();
//            System.out.println(size);
//            if(size == 0){
//                b = false;
//            }
//        }
    }
}
