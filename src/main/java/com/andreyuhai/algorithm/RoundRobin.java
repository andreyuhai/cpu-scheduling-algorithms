package com.andreyuhai.algorithm;

import com.andreyuhai.helper.By;
import com.andreyuhai.process.Process;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin extends SchedulingAlgorithm {

    private int quantumTime;
    private Queue<Process> roundRobinQueue;

    public RoundRobin(int numOfProcessesToSimulate, int quantumTime) {
        super(By.arrivalTime());
        this.numOfProcessesToSimulate = numOfProcessesToSimulate;
        this.quantumTime = quantumTime;
        this.roundRobinQueue = new LinkedList<>();
    }

    @Override
    public int run() {

        System.out.println("========>>> RR RUNNING <<<========");

        int totalWaitTime = 0;
        while (numOfProcessesToSimulate != numOfProcessesTerminated) {

            // For the first time
            waitUntilProcessArrives();

            moveProcessesToRRQueue();

            if(currentProcess != null) {
                roundRobinQueue.remove(currentProcess);
                roundRobinQueue.add(currentProcess);
            }

            System.out.println("\n===================================================");
            for (Process proc : roundRobinQueue) {
                System.out.print("= [ID : " + proc.getPid() + "/" + proc.getRemaining_cpu_time() + "] @ ");
            }
            System.out.println("\n===================================================\n");




            // Now run it check if it's done
            // If is done then remove from the queue
            // Check if there are new elements in processQueue

            do{
                currentProcess = roundRobinQueue.peek();
                if(currentProcess == null) {
                    System.out.println("There is nothing in the queue. EXITING now.");
                    break;
                }

                if (runProcessForQuantumTime()) {
                    totalWaitTime += currentProcess.getWait_time();
                    numOfProcessesTerminated++;
                    currentProcess = null;

                } else { // If process has not finished
                    if(processQueueHasProcesses()) {
                        break;
                    }
                    roundRobinQueue.remove(currentProcess);
                    roundRobinQueue.add(currentProcess);
                }
                System.out.println("RUN");
            }while(!processQueueHasProcesses());

        }
        return totalWaitTime;
    }

    void moveProcessesToRRQueue() {
        synchronized (processQueue) {
            System.out.println(processQueue.size() + " processes will be moved to RR queue");
            printProcessQueue();
            while (processQueue.peek() != null) {
                roundRobinQueue.add(processQueue.peek());
                processQueue.poll();
            }
            System.out.println("Now processQueue size is: " + processQueue.size());
        }
    }

    // Returns true if the process has finished executing completely.
    boolean runProcessForQuantumTime() {
        for (int i = 0; i < quantumTime; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentProcess.setRemaining_cpu_time(currentProcess.getRemaining_cpu_time() - 1);
            printTime();
            System.out.println("[Process]\tID: " + currentProcess.getPid() + " ran for 1 ms");
            if (currentProcess.getRemaining_cpu_time() == 0) {
                executeProcess(currentProcess);
                time.incrementAndGet();
                roundRobinQueue.remove(currentProcess);
                return true;
            }
            time.incrementAndGet();
        }
        return false;
    }

    boolean processQueueHasProcesses() {
        synchronized (processQueue) {
            return processQueue.size() > 0;
        }
    }

}
