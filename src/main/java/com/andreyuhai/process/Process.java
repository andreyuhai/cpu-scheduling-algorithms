package com.andreyuhai.process;

import com.andreyuhai.processor.Processor;

import java.util.Random;

public class Process implements Runnable{

    Processor processor;

    private int pid; // Process ID
    private int arrival_time; // The time at which the process has been pushed into the queue
    private int burst_time; // The time needed for the process to terminate.
    private int completion_time; // The time at which the process has terminated.
    private int turnaround_time; // Completion time - arrival time
    private int wait_time; // The elapsed time between turnaround time and burst time
    private int remaining_cpu_time; // To use in Round Robin
    private boolean preset;

    public Process(Processor processor, int pid, int burst_time){
        this.processor = processor;
        this.pid = pid;
        this.burst_time = burst_time;
        this.remaining_cpu_time = burst_time;
    }

    public Process(int pid, int arrival_time, int burst_time) {
        this.pid = pid;
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.remaining_cpu_time = burst_time;
    }

    public Process(Processor processor, int pid, int burst_time, int arrival_time, boolean preset) {
        this(processor, pid, burst_time);
        this.arrival_time = arrival_time;
        this.preset = preset;
    }

    @Override
    public void run() {
        if(preset){
            enqueuePreset();
        } else {
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            enqueue();
        }
    }

    //---------- GETTER AND SETTERS ----------//
    public int getWait_time() {
        return wait_time;
    }

    public void setWait_time(int wait_time) {
        this.wait_time = wait_time;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public void setCompletion_time(int completion_time) {
        this.completion_time = completion_time;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getTurnaround_time() {
        return turnaround_time;
    }

    public void setTurnaround_time(int turnaround_time) {
        this.turnaround_time = turnaround_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }

    public int getCompletion_time() {
        return completion_time;
    }

    public int getRemaining_cpu_time() {
        return remaining_cpu_time;
    }

    public void setRemaining_cpu_time(int remaining_cpu_time) {
        this.remaining_cpu_time = remaining_cpu_time;
    }

    //---------- GETTER AND SETTERS ----------//

    private void enqueue() {
        processor.enqueue(this);
    }

    private void enqueuePreset() {
        processor.enqueueManual(this);
    }

}
