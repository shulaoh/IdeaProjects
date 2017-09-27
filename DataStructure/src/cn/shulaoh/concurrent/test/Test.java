package cn.shulaoh.concurrent.test;

public class Test {

    public static void main (String[] args) {
        Runnable run = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello: " + threadName);
        };

        run.run();

        Thread thread = new Thread(run);
        thread.start();

        System.out.println("Done.");

        IChange change = () -> {
            System.out.println("change");
        };

        change.change();
    }

}
