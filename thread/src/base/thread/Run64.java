package base.thread;
/*
    使用notify通知线程唤醒wait线程，需要notify对应的synchronized代码块执行完毕之后释放锁，才能触发
    wait线程的继续执行
 */
public class Run64 {
        public static void main(String[] args){
            try{
                Object lock = new Object();
                MyThread64_A a = new MyThread64_A(lock);
                a.start();
                Thread.sleep(3000);
                MyThread64_B b = new MyThread64_B(lock);
                b.start();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
}
class MyThread64_A extends Thread{
    private Object lock;

    public MyThread64_A(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock){
                System.out.println("开始 waitTime ="+System.currentTimeMillis());
                lock.wait();
                System.out.println("结束 waitTime ="+System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
class MyThread64_B extends Thread{
    private Object lock;

    public MyThread64_B(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("开始 notifyTime ="+System.currentTimeMillis());
            lock.notify();
            System.out.println("结束 notifyTime ="+System.currentTimeMillis());
        }
    }
}