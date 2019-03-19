package com.andreyuhai.process;

import com.andreyuhai.processor.Processor;

import java.util.Random;

public class ProcessGenerator implements  Runnable{

    private int numOfProcsToBeCreated;
    private Processor processor;

    public ProcessGenerator() {}

    public ProcessGenerator(Processor processor, int numOfProcsToBeCreated) {
        this.processor = processor;
        this.numOfProcsToBeCreated = numOfProcsToBeCreated;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        for(int i = 0; i < numOfProcsToBeCreated; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new Process(processor, i, rnd.nextInt(10) + 1)).start();
        }
    }

    public void setNumOfProcsToBeCreated(int numOfProcsToBeCreated) {
        this.numOfProcsToBeCreated = numOfProcsToBeCreated;
    }
}
