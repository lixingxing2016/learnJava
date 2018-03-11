package base.thread;
/*
    为了唤醒所有的WAITING状态的线程，可以使用notifyAll()方法
    带一个参数的wait(long)方法的功能是等待某一时间内是否有线程对锁进行唤醒，如果超过这个时间则自动唤醒
 */
public class Run70 {
    private static Object lock = new Object();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try{
                synchronized (lock){
                    System.out.println("wait begin timer = "+System.currentTimeMillis()+"当前线程"+Thread.currentThread().getName());
                    lock.wait(5000);
                    System.out.println("wait end timer = "+System.currentTimeMillis()+"当前线程"+Thread.currentThread().getName());
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };
    private static Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
                synchronized (lock){
                    System.out.println("wait begin timer = "+System.currentTimeMillis()+"当前线程"+Thread.currentThread().getName());
                    lock.notify();
                    System.out.println("wait end timer = "+System.currentTimeMillis()+"当前线程"+Thread.currentThread().getName());
                }
        }
    };
    public static void main(String[] args)throws Exception{
        Thread t = new Thread(runnable);
        t.start();
        Thread.sleep(3000);
        Thread t2 = new Thread(runnable2);
        t2.start();
    }
}
