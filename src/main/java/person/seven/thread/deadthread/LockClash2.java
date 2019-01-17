package person.seven.thread.deadthread;

public class LockClash2 {

    public static void main(String[] args) {
        LockObj obj1 = new LockObj();
        LockObj obj2 = new LockObj();
        new LockThread(obj1, obj2).start();
        new LockThread(obj2, obj1).start();
    }


}


class LockThread extends Thread {

    private LockObj obj1;
    private LockObj obj2;

    public LockThread(LockObj obj1, LockObj obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    @Override
    public void run() {
        obj1.Method1(obj2);
    }
}

class LockObj {


    public synchronized void Method1(LockObj obj){
        System.out.println("get lock and come in");
        obj.Method2(obj);
        System.out.println("deal end and come out");
    }

    /**
     * 处理方式，缩小锁的范围
     * @param obj
     */
//    public void Method1(LockObj obj) {
//        synchronized (this) {
//            System.out.println("get lock and come in");
//        }
//        obj.Method2(obj);
//        System.out.println("deal end and come out");
//    }

    public synchronized void Method2(LockObj obj) {
        System.out.println("deal method2");

    }

}
