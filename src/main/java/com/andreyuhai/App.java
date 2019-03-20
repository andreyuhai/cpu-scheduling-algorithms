package com.andreyuhai;

import com.andreyuhai.process.ProcessGenerator;
import com.andreyuhai.processor.Processor;
import com.andreyuhai.process.ProcessBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

//        final int FCFS = 0;
//        final int SJF = 1;
//        final int SRTF = 2;
//        final int RR = 3;

//        Processor processor = new Processor(RR, 10, 2);
        // Preparing our processor and its cpu scheduling algorithm
//        Processor processor = new Processor(FCFS, 1);
//        Thread processorTH = new Thread(processor);

//        processorTH.start();

        // To schedule some random processes
//        Thread processScheduler = new Thread(new ProcessGenerator(processor, 100));
//        processScheduler.start();
//        Thread p1 = new Thread(
//                new ProcessBuilder()
//                .pid(0)
//                .arrival_time(0)
//                .burst_time(1)
//                .preset(true)
//                .processor(processor)
//                .build()
//        );
//        p1.start();




//
        App app = new App();
        app.start();


    }

    void printProcessMenu() {
        System.out.println("\n= = = = C P U  S C H E D U L I N G  A L G O R I T H M S = = = =\n");
        System.out.println("\tHow would you like your processes?");
        System.out.println("\t0. Exit");
        System.out.println("\t1. Random");
        System.out.println("\t2. Manual");
    }


    void printMenu() {
        System.out.println("\n= = = = C P U  S C H E D U L I N G  A L G O R I T H M S = = = =\n");
        System.out.println("\tChoose one of the algorithms to simulate");
        System.out.println("\t0. Exit");
        System.out.println("\t1. FCFS");
        System.out.println("\t2. SJF");
        System.out.println("\t3. SRTF");
        System.out.println("\t4. RR");
    }


    int promptUserInput(String promptMessage){
        Scanner sc = new Scanner(System.in);
        System.out.print("\n" + promptMessage);
        return sc.nextInt();
    }

    void start(){

        final int EXIT = 0;
        final int RANDOM = 1;
        final int MANUAL = 2;

        final int FCFS = 1;
        final int SJF = 2;
        final int SRTF =3;
        final int RR = 4;

        while(true){
            printMenu();
            int userInput = promptUserInput("Your choice: ");
            int processCreationType = 0;
            int schedulingAlgorithm = 0;
            int quantumTime = 0;

            switch (userInput) {
                case EXIT:
                    System.exit(0);
                    break;
                case FCFS:
                    schedulingAlgorithm = FCFS;
                    break;
                case SJF:
                    schedulingAlgorithm = SJF;
                    break;
                case SRTF:
                    schedulingAlgorithm = SRTF;
                    break;
                case RR:
                    schedulingAlgorithm = RR;
                    quantumTime = promptUserInput("Quantum time: ");
                    break;
            }


            int numOfProcesses = promptUserInput("How many processes to create/test: ");

            Processor processor = null;
            if(schedulingAlgorithm == RR){
                processor = new Processor(schedulingAlgorithm - 1, numOfProcesses, quantumTime);
            } else {
                processor = new Processor(schedulingAlgorithm - 1, numOfProcesses);
            }

            printProcessMenu();
            processCreationType = promptUserInput("Choice of process creation: ");
            switch (processCreationType) {
                case EXIT:
                    System.exit(0);
                    break;
                case MANUAL:
                    List<Thread> processThreadList = new ArrayList<>();
                    for(int i = 0; i < numOfProcesses; i++) {
                        processThreadList.add(createProcess(i, processor));
                    }
                    new Thread(processor).start();
                    for(Thread th : processThreadList)
                        th.start();
                    break;
                case RANDOM:
                    Thread processorTH = new Thread(processor);
                    processorTH.start();
                    new Thread(new ProcessGenerator(processor, 100)).start();
                    try {
                        processorTH.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    Thread createProcess(int processId, Processor processor){
        System.out.println("\n[Process ID: " + processId + "]");
        int arrival_time = promptUserInput("Process arrival time:");
        int burst_time = promptUserInput("Process burst time: ");

        return new Thread(
                new ProcessBuilder()
                .pid(processId)
                .processor(processor)
                .preset(true)
                .arrival_time(arrival_time)
                .burst_time(burst_time)
                .build()
        );
    }

    public void clrscr(){
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new java.lang.ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }


}
