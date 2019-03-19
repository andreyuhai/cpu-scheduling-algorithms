package com.andreyuhai.scheduler;

import com.andreyuhai.helper.By;

public class ShortestJobFirst extends FirstComeFirstServed{

    // SJF is basically FCFS where you sort processes
    // by their burst times hence just the constructor is enough :)

    public ShortestJobFirst(){
        super(By.burstTime());
    }
}
