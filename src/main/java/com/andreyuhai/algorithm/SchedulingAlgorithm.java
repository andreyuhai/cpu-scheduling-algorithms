package com.andreyuhai.algorithm;

import com.andreyuhai.helper.By;
import com.andreyuhai.helper.ProcessTimeComparator;
import com.andreyuhai.process.Process;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SchedulingAlgorithm {

    final AtomicInteger time; // Because AtomicInteger is thread safe
    int numOfProcessesToSimulate;
    int numOfProcessesTerminated;
    final PriorityQueue<Process> processQueue;
    Process currentProcess;
    int processQueueSize;

    SchedulingAlgorithm(By by) {
        Comparator<Process> comparator = new ProcessTimeComparator(by);
        this.processQueue = new PriorityQueue<>(comparator);
        this.time = new AtomicInteger(0);
    }

    public abstract int run();

    @SuppressWarnings("Duplicates")
    int executeProcess(Process process) {
        process.setCompletion_time(time.intValue());
        process.setTurnaround_time(time.intValue() - process.getArrival_time());
        process.setWait_time(process.getTurnaround_time() - process.getBurst_time());

        printTime();
        System.out.println("[Process]\tID: " + process.getPid() + " has been executed completely.");
        System.out.println("\t\t\t\t\t\tArrival time: " + process.getArrival_time());
        System.out.println("\t\t\t\t\t\tBurst time: " + process.getBurst_time());
        System.out.println("\t\t\t\t\t\tCompletion time: " + process.getCompletion_time());
        System.out.println("\t\t\t\t\t\tTurnaround time: " + process.getTurnaround_time());
        System.out.println("\t\t\t\t\t\tWait time: " + process.getWait_time() + "\n");

        return process.getWait_time();
    }

    ;

    public void enqueueProcess(Process process) {
        synchronized (time) {
            process.setArrival_time(time.intValue());
        }
        synchronized (processQueue) {
            processQueue.add(process);
            //processQueue.notifyAll();
        }
    }

    public void enqueuePresetProcess(Process process) {
        while(process.getArrival_time() != time.intValue()) {
            if(process.getArrival_time() == time.intValue()){
                synchronized (processQueue){
                    processQueue.add(process);
                }
            }
        }
        synchronized (processQueue) {
            processQueue.add(process);
        }
    }

    void waitUntilProcessArrives() {
        while (processQueue.size() == 0) {
            printTime();
            System.out.println("[Processor]\tWaiting for processes to enter the ready queue.\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time.incrementAndGet();
            if (processQueue.size() > 0) {
                System.out.println("\t\t\t[Processor]\tProcess found in the ready queue.\n");
                processQueueSize = processQueue.size();
            }
        }
        synchronized (processQueue) {
            processQueueSize = processQueue.size();
        }
    }

    void setCurrentProcess(boolean poll_after_peek) {
        synchronized (processQueue) {
            currentProcess = processQueue.peek();
            if (poll_after_peek)
                processQueue.poll();
        }
    }

    void printProcessQueue() {

        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@ Currently there are " + processQueue.size() + " processes in the queue.");
        for (Process process : processQueue) {
            System.out.print("@ [ID: " + process.getPid() + "/" + process.getRemaining_cpu_time() + "] ");
        }
        System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

    }

    void printTime() {
        System.out.print("[Time: " + time.intValue() + "]\t");
    }
}
