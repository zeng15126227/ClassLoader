package juc2;

public class BreakDemo {
    public static void main(String[] args) {
        out:
        if(1==1){
            System.out.println("1");
            if(args.length!=0){
                return;
            }else {
                break out;
            }
        }
        System.out.println("2");

    }
}
