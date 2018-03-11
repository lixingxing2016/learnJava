package base.thread;

public class Run69 {
    public static void main(String[] args)throws Exception{
        Object lock = new Object();
        MyThread69_A a = new MyThread69_A(lock);
        a.start();
        MyThread69_B b = new MyThread69_B(lock);
        b.start();
        MyThread69_C c = new MyThread69_C(lock);
        c.start();
        Thread.sleep(1000);
        NotifyThread notifyThread = new NotifyThread(lock);
        notifyThread.start();
    }
}
class MyService69{
    public void testMethod(Object lock){
        try {
            synchronized (lock){
                System.out.println("begin wait() ThreadName="+Thread.currentThread().getName());
                lock.wait();
                System.out.println("end wait() ThreadName="+Thread.currentThread().getName());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread69_A extends Thread{
    private Object lock;

    public MyThread69_A(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        MyService69 service = new MyService69();
        service.testMethod(lock);
    }
}
class MyThread69_B extends Thread{
    private Object lock;

    public MyThread69_B(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        MyService69 service = new MyService69();
        service.testMethod(lock);
    }
}
class MyThread69_C extends Thread{
    private Object lock;

    public MyThread69_C(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        MyService69 service = new MyService69();
        service.testMethod(lock);
    }
}
class NotifyThread extends Thread{
    private Object lock;

    public NotifyThread(Object lock) {
        this.lock = lock;
    }
    //多次调用notify()方法唤醒了全部WAITING中的线程
    @Override
    public void run() {
       synchronized (lock){
           lock.notify();//方法notify()仅随机唤醒一个线程
           lock.notify();//方法notify()仅随机唤醒一个线程
           lock.notify();//方法notify()仅随机唤醒一个线程
           lock.notify();//方法notify()仅随机唤醒一个线程
           lock.notify();//方法notify()仅随机唤醒一个线程
           lock.notify();//方法notify()仅随机唤醒一个线程

       }
    }
}