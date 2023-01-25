package org.lrj.code.timer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLongTimer implements Runnable{

    private Share share;

    @Override
    public void run() {
        int i = share.getI();
        i--;
        share.setI(i);
        System.out.println(Thread.currentThread().getName()+ "ok" + i);

        try {
            while (!Thread.interrupted()) {
                Thread.sleep(500);
            }
        } catch(InterruptedException e){
            System.out.println("超时......");
        }
    }

}
