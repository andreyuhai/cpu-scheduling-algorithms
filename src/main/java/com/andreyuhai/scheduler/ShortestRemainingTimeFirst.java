package com.andreyuhai.scheduler;

import com.andreyuhai.helper.By;
import com.andreyuhai.process.Process;

public class ShortestRemainingTimeFirst extends ProcessScheduler {

    public ShortestRemainingTimeFirst() {
        super(By.burstTime());
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Process getNextProcess() {
        synchronized (processQueue) {
            while(processQueue.size() == 0) {
                try {
                    System.out.println("[Processor]\tWaiting for processes to enter the ready queue.\n");
                    processQueue.wait();
                    System.out.println("[Processor]\tProcess found in the ready queue.\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Process currentProcess = processQueue.peek();
            return currentProcess;
        }
    }
}
