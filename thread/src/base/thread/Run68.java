package base.thread;
/*
    当线程呈wait()状态时，调用线程对象的interrupt()方法会出现InterruptedException异常。
    总结：
    1）执行完同步代码块就会释放对象的锁
    2）在执行同步代码块的过程中，遇到异常而导致线程终止，锁也会被释放
    3）在执行同步代码块的过程中，执行了锁所属对象的wait()方法，这个线程会释放对象锁，而
    此线程会进入线程等待池中，等待被唤醒
 */
public class Run68 {
    public static void main(String[] args){
        try {
            Object lock = new Object();
            MyThread68_A a = new MyThread68_A(lock);
            a.start();
            Thread.sleep(5000);
            a.interrupt();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyService68{
    public void testMethod(Object lock){
        try {
            synchronized (lock){
                System.out.println("begin wait()");
                lock.wait();
                System.out.println("end wait()");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("出现异常了，因为呈wait状态的线程被interrupt了！");
        }
    }
}
class MyThread68_A extends Thread{
    private Object lock;

    public MyThread68_A(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        MyService68 service = new MyService68();
        service.testMethod(lock);
    }
}