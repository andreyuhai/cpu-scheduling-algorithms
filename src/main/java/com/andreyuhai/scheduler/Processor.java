package com.andreyuhai.scheduler;


import com.andreyuhai.process.Process;

public class Processor implements Runnable{

    private int time;
    private int schedulingAlgorithm;
    private int numOfProcessesToBeSimulated;
    private ProcessScheduler processScheduler;
    private int quantumTime;

    public Processor(int schedulingAlgorithm, int numOfProcessesToBeSimulated) {
        this.numOfProcessesToBeSimulated = numOfProcessesToBeSimulated;
        this.schedulingAlgorithm = schedulingAlgorithm;
        switch (schedulingAlgorithm) {
            case ProcessScheduler.FCFS:
                this.processScheduler = new FirstComeFirstServed();
                break;
            case ProcessScheduler.SJF:
                this.processScheduler = new ShortestJobFirst();
                break;
            case ProcessScheduler.SRTF:
                break;
            case ProcessScheduler.RR:
                break;
        }
    }

    public Processor(int schedulingAlgorithm, int numOfProcessesToBeSimulated, int quantumTime) {
        this(schedulingAlgorithm, numOfProcessesToBeSimulated);
        this.quantumTime = quantumTime;
    }

    @Override
    public void run() {

        int numOfProcessesTerminated = 0;
        int totalWaitTime = 0;

        while(numOfProcessesToBeSimulated != numOfProcessesTerminated) {
            Process currentProcess = processScheduler.getNextProcess();

            if(schedulingAlgorithm != ProcessScheduler.RR) {
                quantumTime = currentProcess.getBurst_time();
            }

//            for(int i = 0; i < quantumTime; i++) {
//                time++;
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            if(setRemainingCpuTime(currentProcess) == 0){
                numOfProcessesTerminated++;
                processScheduler.removeProcess(currentProcess);
            }

            totalWaitTime += executeProcess(currentProcess);
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@ Total wait time of " + numOfProcessesToBeSimulated + " processes: " + totalWaitTime + " ms");
        System.out.println("@ Average wait time: " + totalWaitTime / numOfProcessesToBeSimulated + " ms");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    public synchronized void enqueue(Process process) {
        processScheduler.enqueueProcess(process, time);
    }

    public int executeProcess(Process process) {
        process.setCompletion_time(time);
        process.setTurnaround_time(time - process.getArrival_time());
        process.setWait_time(process.getTurnaround_time() - process.getBurst_time());

        System.out.println("[Process]\tID: " + process.getPid() + " is running...");
        System.out.println("\t\t\tArrival time: " + process.getArrival_time());
        System.out.println("\t\t\tBurst time: " + process.getBurst_time());
        System.out.println("\t\t\tCompletion time: " + process.getCompletion_time());
        System.out.println("\t\t\tTurnaround time: " + process.getTurnaround_time());
        System.out.println("\t\t\tWait time: " + process.getWait_time() + "\n");

        return process.getWait_time();
    }

    private int setRemainingCpuTime(Process process) {
        try{
            process.setRemaining_cpu_time(process.getRemaining_cpu_time() - quantumTime);

            if(process.getRemaining_cpu_time() < 0)
                throw new Exception("Remaning CPU time can not be less than 0.");


        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return process.getRemaining_cpu_time();
    }
}
