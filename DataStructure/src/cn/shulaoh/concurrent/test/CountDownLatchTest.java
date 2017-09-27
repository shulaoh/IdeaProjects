package cn.shulaoh.concurrent.test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static class Worker implements Runnable {

        private String name;

        private CountDownLatch countDownLatch;

        public Worker(String name, CountDownLatch latch) {
            this.countDownLatch = latch;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Worker: " + name + " is working.");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();

        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread worker1 = new Thread(new Worker("test1", countDownLatch));
        Thread worker2 = new Thread(new Worker("test2", countDownLatch));
        worker1.start();
        worker2.start();


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do some thing.");
    }
}
