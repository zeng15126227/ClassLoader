package juc.interview2;

public class interview_v5 {

    enum ready2Run {T1,T2}
    static volatile ready2Run ready = ready2Run.T1;
    static Thread t1,t2 = null;

    public static void main(String[] args) {

        int[] a = {1,2,3,4,5,6,7,8,9,10,
                11,12,13,14,15,16,17,18,19,20,
                21,22,23,24,25,26};
        char[] b = "ABCDEFGHIGKLMNOPQRSTUVWXYZ".toCharArray();

        t1 = new Thread(()->{
            for(int t:a){
                while(ready!=ready2Run.T1){}
                System.out.println(t);
                ready = ready2Run.T2;
            }
        },"t1");

        t2 = new Thread(()->{
            for(char t:b){
                while(ready!=ready2Run.T2){}
                System.out.println(t);
                ready = ready2Run.T1;
            }
        },"t2");

        t1.start();
        t2.start();

    }
}
