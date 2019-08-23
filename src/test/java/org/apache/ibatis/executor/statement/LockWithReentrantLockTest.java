package org.apache.ibatis.executor.statement;

import org.junit.jupiter.api.Test;

class LockWithReentrantLockTest {

    @Test
    public  void TestReentrantLock(){
        final LockWithReentrantLock v = new LockWithReentrantLock();
// 线程 1：设置 b = 0
        final Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    v.set1();
                }
            };
        };
        t1.start();
// 线程 2：设置 b = -1
        final Thread t2 = new Thread() {
            public void run() {
                while (true) {
                    v.set2();
                }
            };
        };
        t2.start();
// 线程 3：检查 0 != b && -1 != b
        final Thread t3 = new Thread() {
            public void run() {
                while (true) {
                    v.check();
                }
            };
        };
        t3.start();
    }
    @Test
    public  void TestMonitorLock(){
        {
            final LockWithSynchronized v = new LockWithSynchronized();

// 线程 1：设置 b = 0
            final Thread t1 = new Thread() {
                public void run() {
                    while (true) {
                        v.set1();
                    }
                };
            };
            t1.start();
// 线程 2：设置 b = -1
            final Thread t2 = new Thread() {
                public void run() {
                    while (true) {
                        v.set2();
                    }
                };
            };
            t2.start();
// 线程 3：检查 0 != b && -1 != b
            final Thread t3 = new Thread() {
                public void run() {
                    while (true) {
                        v.check();
                    }
                };
            };
            t3.start();
        }

    }

    @Test
    public void testAtomic() {
        {

                final LockByAtomic v = new LockByAtomic();

// 线程 1：设置 b = 0
                final Thread t1 = new Thread() {
                    public void run() {
                        int i = 0;
                        while (i<11) {
                            v.set1();
                            i++;
                        }
                    };
                };
                t1.start();

                final Thread t2 = new Thread() {
                    public void run() {
                        int i = 0;
                        while (i<10) {
                            v.set2();
                            i++;
                        }
                    };
                };
                t2.start();
 // 线程 3：检查 0 != b && -1 != b
                final Thread t3 = new Thread() {
                    public void run() {
                        while (true) {
                            v.check();
                        }
                    };
                };
                t3.start();
                System.out.println("+++"+v.b);


        }
    }

    @Test
    public void testThreadStop(){
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(1);
                }
            }
        }.start();

    }

    public static void main(String[] args) {
        final LockByAtomic v = new LockByAtomic();
// 线程 1：设置 b = 0
        final Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    v.set1();
                }
            };
        };
        t1.start();
// 线程 2：设置 b = -1
        final Thread t2 = new Thread() {
            public void run() {
                while (true) {
                    v.set2();
                }
            };
        };
        t2.start();
// 线程 3：检查 0 != b && -1 != b
        final Thread t3 = new Thread() {
            public void run() {
                while (true) {
                    v.check();
                }
            };
        };
        t3.start();

    }
}