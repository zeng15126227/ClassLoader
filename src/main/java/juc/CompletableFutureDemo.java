package juc;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        CompletableFuture<Double> priceTM = CompletableFuture.supplyAsync(()->priceTM());
        CompletableFuture<Double> priceTB = CompletableFuture.supplyAsync(()->priceTB());
        CompletableFuture<Double> priceJD = CompletableFuture.supplyAsync(()->priceJD());
        CompletableFuture.allOf(priceJD,priceTB,priceTM).join();

    }

    private static Double priceTM(){
        delay();
        return 1.0;
    }
    private static Double priceJD(){
        delay();
        return 2.0;
    }
    private static Double priceTB(){
        delay();
        return 3.0;
    }

    private static void delay(){
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
