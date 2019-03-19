package com.andreyuhai.algorithm;

import com.andreyuhai.helper.By;

public class ShortestJobFirst extends FirstComeFirstServed {

    public ShortestJobFirst() {
        super(By.burstTime());
    }

    public ShortestJobFirst(int numOfProcessesToSimulate) {
        this();
        this.numOfProcessesToSimulate = numOfProcessesToSimulate;
    }
}
