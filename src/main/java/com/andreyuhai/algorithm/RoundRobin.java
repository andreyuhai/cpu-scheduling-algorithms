package com.andreyuhai.algorithm;

import com.andreyuhai.helper.By;
import com.andreyuhai.process.Process;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends SchedulingAlgorithm {

    private int quantumTime;

    RoundRobin(int quantumTime) {
        super(By.arrivalTime());
        this.quantumTime = quantumTime;
    }

    @Override
    public int run() {

        System.out.println("========>>> FCFS RUNNING <<<========");

        int totalWaitTime = 0;
        Queue<Process> roundRobinQueue = new LinkedList<>();
        synchronized(processQueue) {
            waitUntillProcessArrives();
            currentProcess = processQueue.peek();
            roundRobinQueue.add(currentProcess);
        }

        for(Process process : roundRobinQueue) {
            for(int i = 0; i < quantumTime; i++) {
                time.incrementAndGet();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

}
