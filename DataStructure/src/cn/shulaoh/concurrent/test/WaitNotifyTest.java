package cn.shulaoh.concurrent.test;


import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyTest {

    private final static int LIST_CAPACITY = 2;


    private static class Producer implements Runnable {

        private Queue<String> storage;

        private String name;

        public Producer(Queue<String> storage, String name) {
            this.storage = storage;
            this.name = name;
        }

        @Override
        public void run() {
            while(true) {
                synchronized (storage) {
                    while (storage.size() >= LIST_CAPACITY) {
                        try {
                            System.out.println("Producer: Thread " + Thread.currentThread().getName() + " is waiting");
                            storage.wait();
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                    }
                    storage.add(name);
                    System.out.println("Adding item: " + name);
                    storage.notifyAll();
                }
            }

        }
    }

    private static class Consumer implements Runnable {

        private Queue<String> storage;

        private String name;

        public Consumer(Queue<String> storage, String name) {
            this.storage = storage;
            this.name = name;
        }

        @Override
        public void run() {
            while(true) {
                synchronized (storage) {
                    while (storage.size() <= 0) {
                        try {
                            System.out.println("Consumer: Thread " + Thread.currentThread().getName() + " is waiting");
                            storage.wait();
                        } catch (Exception ex) {

                        }
                    }
                    storage.remove();
                    System.out.println("Removing item: " + name);
                    storage.notifyAll();

                }
            }

        }
    }

    public static void main(String[] args) {

        Queue<String> storage = new LinkedList<>();

        for (int i = 0; i < 1; i++) {
            Producer pr = new Producer(storage, String.valueOf(i));
            new Thread(pr).start();
        }

        for (int i = 0; i < 1; i++) {
            Consumer cs = new Consumer(storage, String.valueOf(i));
            new Thread(cs).start();
        }


    }

}
