package sbu.cs;

import sbu.cs.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CPU_Simulator_Double_Core {

    public static class Task implements Runnable, Comparable<Task> {
        long processingTime;
        String ID;

        public Task(String ID, long processingTime) {
            this.processingTime = processingTime;
            this.ID = ID;
        }

        public long getProcessingTime() {
            return processingTime;
        }

        public String getID() {
            return ID;
        }

        /*
            Simulate running a task by utilizing the sleep method for the duration of
            the task's processingTime. The processing time is given in milliseconds.
        */
        @Override
        public void run() {
            try {
                Thread.sleep(this.processingTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public int compareTo(Task compareTo) {
            long compareTime = compareTo.processingTime;
            return (int) (this.processingTime - compareTime);
        }
    }

    public static ArrayList<String> startSimulation(ArrayList<Task> tasks) {
        ArrayList<String> executedTasks = new ArrayList<>();
        for (Task task : tasks) {
            Thread t = new Thread(task);
            try {
                t.start();
                t.join();
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
            executedTasks.add(task.getID());
        }
        return executedTasks;
    }

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

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("A", 100));
        tasks.add(new Task("B", 200));
        tasks.add(new Task("C", 50));
        tasks.add(new Task("D", 150));

        Collections.sort(tasks);

        ArrayList<Task> s1 = sliceOfTasks(tasks, 0, tasks.size() / 2);
        ArrayList<Task> s2 = sliceOfTasks(tasks, tasks.size() / 2, tasks.size());
        System.out.println(mergeList(startSimulation(s1), startSimulation(s2)));
    }
}
