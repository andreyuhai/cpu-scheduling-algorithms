package com.andreyuhai.scheduler;

import com.andreyuhai.helper.By;
import com.andreyuhai.helper.ProcessTimeComparator;
import com.andreyuhai.process.Process;

import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class ProcessScheduler {

    public final static int FCFS = 0;
    public final static int SJF = 1;
    public final static int SRTF = 2;
    public final static int RR = 3;

    final PriorityQueue<Process> processQueue;
    Comparator<Process> comparator;

    public ProcessScheduler(By by) {
        this.comparator = new ProcessTimeComparator(by);
        this.processQueue = new PriorityQueue<>(comparator);
    }

    public abstract Process getNextProcess();

    public void enqueueProcess(Process process, int arrivalTime) {
        process.setArrival_time(arrivalTime);
        synchronized(processQueue) {
            processQueue.add(process);
            processQueue.notifyAll();
        }
    }

    public void removeProcess(Process process) {
        synchronized (processQueue){
            processQueue.remove(process);
        }
    }
}
