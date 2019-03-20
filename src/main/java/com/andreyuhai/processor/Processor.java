package com.andreyuhai.processor;

import com.andreyuhai.algorithm.*;
import com.andreyuhai.process.Process;

public class Processor implements Runnable {

    private int numOfProcessesToBeSimulated;
    private SchedulingAlgorithm schedulingAlgorithm;

    public Processor(int schedulingAlgorithm, int numOfProcessesToBeSimulated) {
        this.numOfProcessesToBeSimulated = numOfProcessesToBeSimulated;
        switch (schedulingAlgorithm) {
            case 0:
            default:
                this.schedulingAlgorithm = new FirstComeFirstServed(numOfProcessesToBeSimulated);
                break;
            case 1:
                this.schedulingAlgorithm = new ShortestJobFirst(numOfProcessesToBeSimulated);
                break;
            case 2:
                this.schedulingAlgorithm = new ShortestRemainingTimeFirst(numOfProcessesToBeSimulated);
                break;
        }
    }

    public Processor(int schedulingAlgorithm, int numOfProcessesToBeSimulated, int quantumTime) {
        this.numOfProcessesToBeSimulated = numOfProcessesToBeSimulated;
        if (schedulingAlgorithm == 3) {
            this.schedulingAlgorithm = new RoundRobin(numOfProcessesToBeSimulated, quantumTime);
        }
    }

    @Override
    public void run() {

        int totalWaitTime = schedulingAlgorithm.run();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@ Total wait time of " + numOfProcessesToBeSimulated + " processes: " + totalWaitTime + " ms");
        System.out.println("@ Average wait time: " + totalWaitTime / numOfProcessesToBeSimulated + " ms");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

    //============== GETTER AND SETTERS ==============//

    public SchedulingAlgorithm getSchedulingAlgorithm() {
        return schedulingAlgorithm;
    }

    public void setProcessSchedulingAlgorithm(SchedulingAlgorithm schedulingAlgorithm) {
        this.schedulingAlgorithm = schedulingAlgorithm;
    }

    //============== GETTER AND SETTERS ==============//

    public synchronized void enqueue(Process process) {
        schedulingAlgorithm.enqueueProcess(process);
    }

    public synchronized void enqueueManual(Process process) {
        schedulingAlgorithm.enqueuePresetProcess(process);
    }

}
