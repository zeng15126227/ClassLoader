package juc.interview1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class interView_v1 {

    static List<Integer> list = new ArrayList<>();
    static Thread t1=null;
    static Thread t2=null;

    public static void main(String[] args) {


        t2 = new Thread(()->{
            System.out.println("t2开始。。。");
            LockSupport.park();
            System.out.println("第五个元素已添加");
            System.out.println("t2结束。。。");
            LockSupport.unpark(t1);
        },"t2");

        t1 = new Thread(()->{
            for(int i=0;i<10;i++){
                System.out.println("add:"+i);
                list.add(i);
                if(list.size()==5){
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        });

        t2.start();
        t1.start();
    }
}
