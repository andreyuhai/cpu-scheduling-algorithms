package com.andreyuhai.scheduler;

import com.andreyuhai.helper.By;
import com.andreyuhai.process.Process;

public class FirstComeFirstServed extends ProcessScheduler {

    public FirstComeFirstServed() {
        super(By.arrivalTime());
    }

    public FirstComeFirstServed(By by) {
        super(by);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Process getNextProcess() {
        synchronized (processQueue){
            Process currentProcess = null;
            while(processQueue.size() == 0) {
                try {
                    System.out.println("[Processor]\tWaiting for processes to enter the ready queue.\n");
                    processQueue.wait();
                    System.out.println("[Processor]\tProcess found in the ready queue.\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // To see what is exactly in the ready queue uncomment these lines below
                System.out.print("Current processes in the queue: ");
                for(Process proc : processQueue) {
                    System.out.print("[ID: " + proc.getPid() + "/" +proc.getArrival_time() + "] ");
                }
                System.out.println("\n");
                currentProcess = processQueue.peek();
                //processQueue.poll();
            }
            return currentProcess;
        }
    }
}
