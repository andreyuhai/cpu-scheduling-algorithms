package com.andreyuhai.helper;

import com.andreyuhai.process.Process;

import java.util.Comparator;

public class ProcessTimeComparator implements Comparator {

    private By by;

    public ProcessTimeComparator(By by) {
        this.by = by;
    }

    @Override
    public int compare(Object o, Object t1) {
        return by.compare((Process) o, (Process) t1);
    }
}
