package base.thread;
/*
    方法notify()被执行后，不释放锁
 */
public class Run67 {
    public static void main(String[] args){
        Object lock = new Object();
        MyThread67_A a = new MyThread67_A(lock);
        a.start();
        MyThread67_B b = new MyThread67_B(lock);
        b.start();
        MyThread67_B c = new MyThread67_B(lock);
        c.start();
    }
}
class MyService67{
    public void testMethod(Object lock){
        try{
            synchronized (lock){
                System.out.println("begin wait() ThreadName="+Thread.currentThread().getName());
                lock.wait();//wait()方法释放锁
                System.out.println("end wait() ThreadName="+Thread.currentThread().getName());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void synNotifyMethod(Object lock){
        try {
            synchronized (lock) {
                System.out.println("begin notify() ThreadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
                lock.notify();//notify()方法不释放锁
                Thread.sleep(5000);
                System.out.println("end wait() ThreadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread67_A extends Thread{
    private  Object lock;
    public MyThread67_A(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        MyService67 service = new MyService67();
        service.testMethod(lock);
    }
}
class MyThread67_B extends Thread{
    private  Object lock;
    public MyThread67_B(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        MyService67 service = new MyService67();
        service.synNotifyMethod(lock);
    }
}
