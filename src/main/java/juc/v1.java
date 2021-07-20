package juc;

public class v1 {
    static class m3 extends Thread{
        @Override
        public void run() {
            System.out.println("m1 static");
        }
    }
    static class m4 implements Runnable{

        @Override
        public void run() {
            System.out.println("m4 static");
        }
    }

    public static void main(String[] args) {
        new m3().start();
        new Thread(new m4()).start();
        new m1().start();
        new Thread(new m2()).start();
        new Thread(()->{
            System.out.println("lambda m");
        }).start();
    }


}

class m1 extends Thread{
    @Override
    public void run() {
        System.out.println("m1");
    }
}

class m2 implements Runnable{
    public void run() {
        System.out.println("m4");
    }
}


