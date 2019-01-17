package person.seven.thread.deadthread;

import java.util.concurrent.TimeUnit;

public class LockClash {

    public static final String obj1 = "obj1";
    public static final String obj2 = "obj2";


    public static void main(String[] args) {
        new DeadLockA().start();
        new DeadLockB().start();

    }
}


class DeadLockA extends Thread{

    @Override
    public void run() {
        synchronized (LockClash.obj1){
            System.out.println("thread A get obj1 lock and come in");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread A try to get obj2 lock");
            synchronized (LockClash.obj2){
                System.out.println("thread A get obj1 lock");
            }
        }
    }
}


class DeadLockB extends Thread{

    @Override
    public void run() {
        synchronized (LockClash.obj2){
            System.out.println("thread B get obj1 lock and come in");
            System.out.println("thread B try to get obj2 lock");
            synchronized (LockClash.obj1){
                System.out.println("thread B get obj1 lock");
            }
        }
    }
}