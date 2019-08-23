package org.apache.ibatis.executor.statement;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockByAtomic {
     final AtomicInteger b = new AtomicInteger();
    private Lock lock = new ReentrantLock();
    public   void set1() {
        lock.lock();
        b.set(1);
        lock.unlock();
    }

    public   void set2() {
        lock.lock();
        b.set(2);
        lock.unlock();
    }

    public    void check() {
        lock.lock();
        {

            System.out.println("check"+b);
            if (b.intValue() != 1 && b.intValue() != 2) {
                System.out.println("shouldnot----"+b);
            }

        }
        lock.unlock();
    }
}