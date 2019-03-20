package com.andreyuhai.algorithm;

import com.andreyuhai.helper.By;

public class FirstComeFirstServed extends SchedulingAlgorithm {

    public FirstComeFirstServed() {
        super(By.arrivalTime());
    }

    public FirstComeFirstServed(By by) {
        super(by);
    }

    public FirstComeFirstServed(int numOfProcessesToSimulate) {
        this();
        this.numOfProcessesToSimulate = numOfProcessesToSimulate;
    }

    @Override

    public int run() {
        System.out.println("========>>> FCFS RUNNING <<<========");

        int totalWaitTime = 0;

        while (numOfProcessesTerminated != numOfProcessesToSimulate) {

            waitUntilProcessArrives();

            setCurrentProcess(true);

            // Trying to simulate real life clock so in that 10 ms sleep time
            // other processes can enter the ready queue at an arbitrary time.
            for(int i = 0; i < currentProcess.getBurst_time(); i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time.incrementAndGet();
            }

            totalWaitTime += executeProcess(currentProcess);
            numOfProcessesTerminated++;
        }
        return totalWaitTime;
    }
}
