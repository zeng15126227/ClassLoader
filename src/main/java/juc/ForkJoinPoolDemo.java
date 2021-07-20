package juc;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolDemo {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static {
        for(int i=0; i<nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println("---" + Arrays.stream(nums).sum()); //stream api
    }

    static class AddTaskRet extends RecursiveTask<Long> {
        int start, end;

        AddTaskRet(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        protected Long compute() {

            if(end-start <= MAX_NUM) {
                long sum = 0L;
                for(int i=start; i<end; i++) sum += nums[i];
                return sum;
            }

            int middle = start + (end-start)/2;

            AddTaskRet subTask1 = new AddTaskRet(start, middle);
            AddTaskRet subTask2 = new AddTaskRet(middle, end);
            subTask1.fork();
            subTask2.fork();

            return subTask1.join() + subTask2.join();
        }

    }


    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool();
        AddTaskRet task = new AddTaskRet(0, nums.length);
        fjp.execute(task);
        long result = task.join();
        System.out.println(result);
    }
}
