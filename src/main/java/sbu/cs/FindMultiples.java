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

public class FindMultiples {
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

        public int getLowerBound() {
            return lowerBound;
        }

        public int getUpperBound() {
            return upperBound;
        }

        @Override
        public void run() {
            int divisor = this.lowerBound;
            while (divisor <= this.upperBound) {
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
    public int getSum(int n) {
        int sum = 0;
        MultiplesRunnable task_1 = new MultiplesRunnable(1, (int) Math.floor(n / 4));
        MultiplesRunnable task_2 = new MultiplesRunnable((int) Math.floor(n / 4) + 1, 2 * (int) Math.floor(n / 4));
        MultiplesRunnable task_3 = new MultiplesRunnable(2 * (int) Math.floor(n / 4) + 1, 3 * (int) Math.floor(n / 4));
        MultiplesRunnable task_4 = new MultiplesRunnable(3 * (int) Math.floor(n / 4) + 1, n);
        Thread thread_1 = new Thread(task_1);
        Thread thread_2 = new Thread(task_2);
        Thread thread_3 = new Thread(task_3);
        Thread thread_4 = new Thread(task_4);
        try {
            thread_1.start();
            thread_1.join();
            thread_2.start();
            thread_2.join();
            thread_3.start();
            thread_3.join();
            thread_4.start();
            thread_4.join();
            sum += task_1.getSum();
            sum += task_2.getSum();
            sum += task_3.getSum();
            sum += task_4.getSum();
        } catch (InterruptedException ie){
            System.out.println(ie.getMessage());
            System.out.println(ie.getStackTrace());
        }
        return sum;
    }

    public static void main(String[] args) {
    }
}
