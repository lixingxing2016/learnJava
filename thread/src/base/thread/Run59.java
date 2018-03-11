package base.thread;

import java.util.concurrent.atomic.AtomicInteger;

/*
    除了在i++操作时使用synchronized关键字实现同步外，还可以使用AtomicInteger原子类进行实现。
    原子操作时不能分割的整体，没有其他线程能够中断或检查正在原子操作的变量。一个原子（atomic）
    类型就是一个原子操作可用的类型，它可以在没有锁的情况下做到线程安全。
 */
public class Run59 {
    public static void main(String[] args){
        AddCountThread count = new AddCountThread();
        Thread t1 = new Thread(count);
        t1.start();
        Thread t2 = new Thread(count);
        t2.start();
        Thread t3 = new Thread(count);
        t3.start();
        Thread t4 = new Thread(count);
        t4.start();
        Thread t5 = new Thread(count);
        t5.start();

    }
}
class AddCountThread extends Thread{
    private AtomicInteger count = new AtomicInteger(0);
    @Override
    public void run() {
        for (int i=0;i<10000;i++){
            System.out.println("当前线程："+Thread.currentThread().getName()+"计数值="+count.incrementAndGet());
        }
    }
}
