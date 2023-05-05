![image-20230505161446630](https://s8.uupload.ir/files/untitled_0ov0.png)

																# 							Sixth Assignment Report

															## 							Mohammad Hossein Basouli   401222020





















## Introduction:

- ### A Brief description of the assignment:

  1. **Simulating a CPU processing** a sequential program. 
     - [x] **Bonus Task : ** Simulating the same thing with a double core CPU. 

  2.  **Calculating the sum** of all the m's that has gcd(n, m) = 1 and are lower than n.
     - [x] **Bonus Task :** Doing the same thing using thread pool (cached or fixed).

  3. **Execute two distinct tasks using multithreading** and terminate them if they take longer than a specified time.

  

## Design and Implementation: 

- ### An explanation on the approach for each task:

  1. **CPU_Simulator :** 

     - We need to simply implement the **Comparable** and **Runnable** interfaces for class of our Task to sort the tasks before we start executing one. here is the implementation: 

       ```java
       public static class Task implements Runnable, Comparable<Task> {
           .....
               @Override
               public void run() {
                   try {
                       Thread.sleep(this.processingTime);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
       
               @Override
               public int compareTo(Task compareTu) {
                   long compareTime = compareTu.processingTime;
                   return (int) (this.processingTime - compareTime);
               }
           }
       ```

     - Then start the tasks one after the other and join them to make sure they get completed before we return the result.

       

  2. **CPU_Simulator (Bonus Task) :**

     - We have to **slice** the tasks into two pieces of task and then start Simulating on each one of them and then **merge** them into a list. Here is the implementation :

       ```java
           public static ArrayList<Task> sliceOfTasks(ArrayList<Task> tasks, int lowerBound, int upperBound) {
               ArrayList<Task> slice = new ArrayList<>(upperBound - lowerBound + 1);
               for (int i = lowerBound; i < upperBound; i++) {
                   slice.add(tasks.get(i));
               }
               return slice;
           }
       
           public static ArrayList<String> mergeList(List<String> l1, List<String> l2) {
               ArrayList<String> mergedList = new ArrayList<>();
               mergedList.addAll(l1);
               mergedList.addAll(l2);
               return mergedList;
           }
       ```

  3.  **FindMultiples :**

     - First we define a class that implements the **Runnable** for each task and then initialize the tasks by hand to assign them to a **thread** that calculates the m's in each bound. Then we add all the sums together.

       ```java
       public class FindMultiples {
           public static class MultiplesRunnable implements Runnable {
               private int sum;
               private int lowerBound;
               private int upperBound;
       
               //Constructor
       ....
               //Getter
       ....
               @Override
               public void run() {
                   //Some calculations...
               }
           }
       
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
                   thread_2.start();
                   thread_3.start();
                   thread_4.start();
                   thread_1.join();
                   thread_2.join();
                   thread_3.join();
                   thread_4.join();
                   sum += task_1.getSum();
                   sum += task_2.getSum();
                   sum += task_3.getSum();
                   sum += task_4.getSum();
               } catch (InterruptedException ie){
                   ie.getMessage();
                   ie.printStackTrace();
               }
               return sum;
           }
       ```

       

  4. **FindMultiples (Bonus) :**

     - We do the same thing but the difference is that this time we use **thread_pool** (**fixed thread_pool**).

       ```java
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
       ```

       

  5. **UserInterrupts :**

     - We need to get the current time before starting each **thread** that **tasks** have been assigned to, and then we should check while the **thread** is **running** and **alive** that it has not taken longer than **3 seconds**. If it has, we should terminate it.

  

## Testing and Evaluation:

- As we can see from the Unit tests results everything works great.

  





