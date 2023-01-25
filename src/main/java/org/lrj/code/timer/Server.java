package org.lrj.code.timer;

import java.util.Timer;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        Share share = new Share();
        for(int i = 0 ; i < 5 ; i++) {
            OperationLongTimer operationLongTimer = new OperationLongTimer();
            operationLongTimer.setShare(share);
            // 创建工作线程
            Thread t = new Thread(operationLongTimer,"yyyy");
            // 创建定时器，指定时间内结束工作线程
            Timer timer = new Timer();
            timer.schedule(new TimeOutTask(t, timer), 1000);

            t.start();
            Thread.sleep(2000);
        }

        for(int i = 0 ; i < 5 ; i++) {
            OperationLongTimer operationLongTimer1 = new OperationLongTimer();
            operationLongTimer1.setShare(share);
            // 创建工作线程
            Thread t1 = new Thread(operationLongTimer1 , "xxxx");
            // 创建定时器，指定时间内结束工作线程
            Timer timer = new Timer();
            timer.schedule(new TimeOutTask(t1, timer), 1000);

            t1.start();
            Thread.sleep(2000);
        }


    }
}
