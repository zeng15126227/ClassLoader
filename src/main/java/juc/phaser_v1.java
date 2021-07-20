package juc;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class phaser_v1 {

    static MarriagePhaser phaser = new MarriagePhaser();

    public static void main(String[] args) {
        phaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("嘉宾" + i)).start();
        }
        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();

    }

    static class Person implements Runnable {

        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {
            milliSleep();
            System.out.printf("%s 到达现场\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep();
            System.out.printf("%s 吃完\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void hug() {
            if (name.equals("新郎") || name.equals("新娘")) {
                milliSleep();
                System.out.printf("%s 礼成\n", name);
                phaser.arriveAndAwaitAdvance();
            } else {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            hug();
        }
    }

    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐：" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完：" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("婚礼结束，新郎新娘礼成！" + registeredParties);
                    return true;
                default:
                    return true;

            }
        }
    }

    static void milliSleep() {

        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
