package com.andreyuhai.process;

public class ProcessBuilder {

    private int pid;
    private int burst_time;
    private int arrival_time;


    public ProcessBuilder() { }

    public ProcessBuilder pid(int pid) {
        this.pid = pid;
        return this;
    }

    public ProcessBuilder burst_time(int burst_time) {
        this.burst_time = burst_time;
        return this;
    }

    public ProcessBuilder arrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
        return this;
    }

    public Process build() {
        return new Process(pid, arrival_time, burst_time);
    }
}
