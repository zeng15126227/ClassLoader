package juc2;

import java.io.IOException;

public class thisEscape {
    private int m = 8;

    public thisEscape() {
        new Thread(()->{
            System.out.println(m);
        }).start();
    }

    public static void main(String[] args) throws IOException {
        new thisEscape();
        //System.in.read();
    }
}
