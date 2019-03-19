package com.andreyuhai.helper;

import com.andreyuhai.process.Process;

import java.util.List;

public abstract class By {

    public By() {
    }

    public abstract int compare(Process p1, Process p2);

    public static By arrivalTime() {
        return new ByArrivalTime();
    }

    public static By burstTime() {
        return new ByBurstTime();
    }

    public static By remainingCpuTime() { return new ByRemainingCpuTime();}

    public static By remainingCpuTimeAndArrivalTime() { return new ByRemainingCpuTimeAndArrivalTime();}

    static class ByArrivalTime extends By {

        public ByArrivalTime() {}

        @Override
        public int compare(Process p1, Process p2) {
            return p1.getArrival_time() - p2.getArrival_time();
        }
    }

    static class ByBurstTime extends By {

        public ByBurstTime() {}

        @Override
        public int compare(Process p1, Process p2) {
            return p1.getBurst_time() - p2.getBurst_time();
        }
    }

    static class ByRemainingCpuTime extends By {

        public ByRemainingCpuTime() {}

        @Override
        public int compare(Process p1, Process p2) {
            return p1.getRemaining_cpu_time() - p2.getRemaining_cpu_time();
        }
    }


    static class ByRemainingCpuTimeAndArrivalTime extends By {
        public ByRemainingCpuTimeAndArrivalTime() {}

        @Override
        public int compare(Process p1, Process p2) {
            int remainingCpu = p1.getRemaining_cpu_time() - p2.getRemaining_cpu_time();
            int arrivalTime = p1.getArrival_time() - p2.getArrival_time();

            if(remainingCpu == 0) {
                return arrivalTime;
            }
            return remainingCpu;
        }
    }
}
