package com.andreyuhai.algorithm;

import com.andreyuhai.helper.By;

public class ShortestRemainingTimeFirst extends SchedulingAlgorithm{

    public ShortestRemainingTimeFirst() {
        super(By.remainingCpuTimeAndArrivalTime());
    }

    public ShortestRemainingTimeFirst(int numOfProcessesToSimulate) {
        this();
        this.numOfProcessesToSimulate = numOfProcessesToSimulate;
    }

    @Override

    public int run() {
        System.out.println("========>>> SRTF RUNNING <<<========");

        int totalWaitTime = 0;

        while(numOfProcessesTerminated != numOfProcessesToSimulate) {
//            synchronized (processQueue) {
//                waitUntilProcessArrives();
//                processQueueSize = processQueue.size();
//                // To see what is exactly in the ready queue uncomment these lines below
//                System.out.print("[Processor]\tCurrent processes in the queue: ");
//                for(Process proc : processQueue) {
//                    System.out.print("[ID: " + proc.getPid() + "/" +proc.getRemaining_cpu_time() + "] ");
//                }
//                System.out.println("\n");
//                currentProcess = processQueue.peek();
//            }

            waitUntilProcessArrives();

            setCurrentProcess(false);

            for(int i = 0; i < currentProcess.getRemaining_cpu_time(); i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                currentProcess.setRemaining_cpu_time(currentProcess.getRemaining_cpu_time() - 1);
                printTime();
                System.out.println("[Process]\tID: " + currentProcess.getPid() + " ran for 1 ms");

                if(currentProcess.getRemaining_cpu_time() != 0) {
                    synchronized (processQueue){
                        if(processQueue.size() > processQueueSize) {
                            printTime();
                            System.out.println("[Processor]\tNew processes found in the queue!!!");
                            System.out.println("\t\t\t[Processor]\tThere were : " + processQueueSize + " processes in the queue, but now there are : " + processQueue.size());
                            processQueue.remove(currentProcess);
                            processQueue.add(currentProcess);
                            time.incrementAndGet();
                            printProcessQueue();
                            break;
                        }
                    }
                } else {
                    printTime();
                    System.out.println("[Process]\tID: " + currentProcess.getPid() + " has finished executing.");
                    executeProcess(currentProcess);
                    totalWaitTime += currentProcess.getWait_time();
                    processQueue.remove(currentProcess);
                    numOfProcessesTerminated++;
                }
                time.incrementAndGet();
            }
        }
        return totalWaitTime;
    }
}
