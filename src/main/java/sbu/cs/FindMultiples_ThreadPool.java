package sbu.cs;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.

    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.

    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.

    Use the tests provided in the test folder to ensure your code works correctly.
 */

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FindMultiples_ThreadPool {
    public static class MultiplesRunnable implements Runnable {
        private int sum;
        private int lowerBound;
        private int upperBound;

        //Constructor

        public MultiplesRunnable(int lowerBound, int upperBound) {
            this.sum = 0;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        //Getter

        public int getSum() {
            return sum;
        }

        @Override
        public void run() {
            int divisor = this.lowerBound;
            while (divisor <= this.upperBound && !Thread.currentThread().isInterrupted()) {
                if (divisor % 3 == 0 || divisor % 5 == 0 || divisor % 7 == 0) {
                    this.sum += divisor;
                }
                divisor++;
            }
        }
    }


    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public static int getSum(int n) {
        int sum = 0;
        ArrayList<MultiplesRunnable> tasks = new ArrayList<>();
        ExecutorService threads = Executors.newFixedThreadPool(4);
        tasks.add(new MultiplesRunnable(1, (int) Math.floor(n / 4)));
        tasks.add(new MultiplesRunnable((int) Math.floor(n / 4) + 1, 2 * (int) Math.floor(n / 4)));
        tasks.add(new MultiplesRunnable(2 * (int) Math.floor(n / 4) + 1, 3 * (int) Math.floor(n / 4)));
        tasks.add(new MultiplesRunnable(3 * (int) Math.floor(n / 4) + 1, n));
        for (MultiplesRunnable task : tasks){
            threads.execute(task);
        }
        threads.shutdown();

        try {
            threads.awaitTermination(10000, TimeUnit.MILLISECONDS);
            for (int i = 0; i < tasks.size(); i++){
                sum += tasks.get(i).getSum();
            }
        } catch (InterruptedException ie) {
            ie.getMessage();
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(getSum(10));
    }
}
