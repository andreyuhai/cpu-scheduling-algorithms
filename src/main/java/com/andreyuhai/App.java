package com.andreyuhai;

import com.andreyuhai.process.ProcessGenerator;
import com.andreyuhai.processor.Processor;

public class App {
    public static void main(String[] args) {
//        // Creating a new processor
//        Processor processor = new Processor();
//
//        Random rnd = new Random(); // To create our random burst times
//        List<Process> processList = new ArrayList<>(); // For our process queue
//
//        // Creating a number of processes and pushing it into our processList
//        for(int i = 0; i < 20; i++) {
//            // Getting a random burst time
//            int burst_time = rnd.nextInt(10) + 1;
//            processList.add(new ProcessBuilder()
//                    .pid(i)
//                    .arrival_time(rnd.nextInt(1000))
//                    .burst_time(burst_time)
//                    .build());
//        }
//
//        // Setting a scheduling algorithm for our processor
//        processor.setProcessSchedulingAlgorithm(new FirstComeFirstServed());
//        // Setting the processList to be processed
//        processor.setProcessList(processList);
//        // Sorting processList
//        processor.sortProcessList(By.arrivalTime());
//        // Running it!
//        processor.run();

//        Process a = new ProcessBuilder()
//                .pid(0)
//                .arrival_time(4)
//                .burst_time(5)
//                .build();
//
//        Process b = new ProcessBuilder()
//                .pid(9)
//                .arrival_time(1)
//                .burst_time(2)
//                .build();
//
//        Process c = new ProcessBuilder()
//                .pid(2)
//                .arrival_time(2)
//                .burst_time(3)
//                .build();
//
        final int FCFS = 0;
        final int SJF = 1;
        final int SRTF = 2;
        final int RR = 3;

//        Processor processor = new Processor(SJF, 100);
//        Thread processorTH = new Thread(processor);


//        Processor processor = new Processor(SJF, 100);
        Processor processor = new Processor(SRTF, 200);
        Thread processorTH = new Thread(processor);
        Thread processScheduler = new Thread(new ProcessGenerator(processor, 200));

        processorTH.start();
        processScheduler.start();
    }


}
