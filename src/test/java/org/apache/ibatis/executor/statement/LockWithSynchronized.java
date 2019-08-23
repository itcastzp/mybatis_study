package org.apache.ibatis.executor.statement;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockWithSynchronized {
    private int b = 0;

    public synchronized void set1() {

        b = 0;

    }

    public synchronized void set2() {

        b = 1;
    }

    public void check() {
        synchronized (this
        ) {

            System.out.println(b);
            if (0 != b && 1 != b) {
                System.out.println("should not in " + b);
            }

        }
    }
}