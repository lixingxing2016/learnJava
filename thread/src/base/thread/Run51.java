package base.thread;
/*
    Java线程死锁是一个经典的多线程问题，因为不同的线程都在等待根本不可能被释放的锁，、
    从而导致所有的任务都无法继续完成。在多线程技术中，死锁是必须避免的，因为会
    造成线程的假死。
    死锁是程序设计的bug，在设计程序时要避免双方互相持有对方的锁的情况。
    只要互相等待对方释放锁就有可能出现死锁。
 */

public class Run51 {
    public static void main(String[] args){
        try{
            DealThread t1 = new DealThread();
            t1.setFlag("a");
            Thread threadA = new Thread(t1);
            threadA.start();
            Thread.sleep(100);
            t1.setFlag("b");
            Thread threadB = new Thread(t1);
            threadB.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class DealThread implements Runnable{
    public String username;
    public Object lock1 = new Object();
    public Object lock2 = new Object();
    public void setFlag(String username){
        this.username = username;
    }
    @Override
    public void run() {
        if (username.equals("a")){
            synchronized (lock1){
                try {
                    System.out.println("username="+username);
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("按照lock1->lock2代码顺序执行了");
                }
            }
        }
        if (username.equals("b")){
            synchronized (lock2){
                try{
                    System.out.println("username="+username);
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (lock1){
                    System.out.println("按lock2->lock1代码顺序执行了");
                }
            }
        }
    }
}