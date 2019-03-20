package com.andreyuhai.process;

import com.andreyuhai.processor.Processor;

public class ProcessBuilder {

    private int pid;
    private int burst_time;
    private int arrival_time;
    private boolean preset;
    private Processor processor;


    public ProcessBuilder() { }

    public ProcessBuilder pid(int pid) {
        this.pid = pid;
        return this;
    }

    public ProcessBuilder burst_time(int burst_time) {
        this.burst_time = burst_time;
        return this;
    }

    public ProcessBuilder preset(boolean preset) {
        this.preset = preset;
        return this;
    }

    public ProcessBuilder arrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
        return this;
    }

    public ProcessBuilder processor(Processor processor) {
        this.processor = processor;
        return this;
    }

    public Process build() {
        return new Process(processor, pid, burst_time, arrival_time, preset);
    }
}
