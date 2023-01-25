package org.lrj.code.test;

public class Demo01 {
    private int i = 10;

}

class one{
    int i = 10;
}

class Two implements Runnable{
    private Demo01 demo01;
   public  Two(){

    }

    public Two(Demo01 demo01){
       this.demo01 = demo01;
    }


    @Override
    public void run() {

        int a = 0;
        int b = 0;
        int k = a/ b;
    }
}
