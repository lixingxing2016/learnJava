package base.thread;
/*
    当方法wait()被执行后，锁被自动释放，但执行完notify()方法，锁却不自动释放
 */
public class Run66 {
    public static void main(String[] args){
        Object lock = new Object();
        MyThread66_A a = new MyThread66_A(lock);
        a.start();
        MyThread66_B b = new MyThread66_B(lock);
        b.start();
    }
}

class MyService66{
    public void testMethod(Object lock){
        try{
            synchronized (lock){
                System.out.println("begin wait()");
                //lock.wait();//wait()方法释放锁
                Thread.sleep(4000);//sleep()方法并不释放锁
                System.out.println("end wait()");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread66_A extends Thread{
    private  Object lock;
    public MyThread66_A(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        MyService66 service = new MyService66();
        service.testMethod(lock);
    }
}
class MyThread66_B extends Thread{
    private  Object lock;
    public MyThread66_B(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
       MyService66 service = new MyService66();
       service.testMethod(lock);
    }
}