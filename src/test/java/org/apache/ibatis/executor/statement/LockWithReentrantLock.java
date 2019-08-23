package org.apache.ibatis.executor.statement;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockWithReentrantLock {
    private int b = 0;
    private Lock lock = new ReentrantLock();
    public void set1() {
        lock.lock();
        b = 0;
        lock.unlock();
    }

    public void set2() {
        lock.lock();

        b = 1;
        lock.unlock();
    }

    public void check() {

        {
            lock.lock();
            System.out.println(b);
            if (0 != b && 1 != b) {
                System.out.println("should not in " + b);
            }
            lock.unlock();

        }
    }
}