package base.thread;

import java.util.ArrayList;
import java.util.List;

/*
    线程A启动并进入wait状态，线程B启动并对list进行add操作，当list.size()为5时，发出通知notify，
    线程A进入monitor状态，此时由于线程B仍持有lock锁，所以继续执行直到释放lock锁，线程A才进入running状态，
    变成可运行状态。
    notify()方法执行后并不立即释放锁。
    关键字synchronized可以将任何一个Object对象作为同步对象来看待，而Java为每个Object都实现了wait()和notify()
    方法，它们必须用在被synchronized同步的Object临界区内，通过调用wait()方法可以使处于临界区内的线程进入等待状态，
    同时释放被同步对象的锁。而notify操作可以唤醒一个因调用了wait操作而处于阻塞状态的线程，使其进入就绪状态。
    被重新唤醒的线程会试图重新获得临界区的控制权，也就是锁，并继续执行临界区内wait之后的代码。
    如果发出notify操作时没有处于阻塞状态中的线程，那么该命令会被忽略。
    wait()方法可以使调用该方法的线程释放共享资源的锁，然后从运行状态退出进入等待队列，直到被再次唤醒。
    notify()方法可以随机唤醒等待队列中等待同一共享资源的一个线程，并使该线程退出等待队列，进入可运行状态，
    也就是notify()方法仅通知一个线程。
    notifyAll()方法可以使所有正在等待队列中等待同一共享资源的全部线程从等待状态退出，进入可运行状态。
    此时，优先级最高的那个线程最先执行，但也有可能是随机执行的，因为这要取决于JVM虚拟机的实现。

    1）新创建一个新的线程对象后，再调用它的start()方法，系统会为此线程分配CPU资源，使其处于Runnable状态，
    这是一个准备运行的阶段。如果线程抢占到CPU资源，此线程就处于Running状态。
    2）Runnable状态和Running状态可以互相切换，因为有可能线程运行一段时间后，有其他高优先级的线程抢占了CPU资源，
    这时此线程就从Running状态变成了Runnable状态。
    线程进入Runable状态大部分分为如下5种情况：
    1.调用sleep()方法后经过的时间超过了指定的休眠时间
    2.线程调用的阻塞IO已经返回，阻塞方法执行完毕
    3.线程成功的获得了试图同步的监视器
    4.线程正在等待某个通知，其他线程发出了通知
    5.处于挂起状态的线程调用了resume恢复方法。
    3）Blocked是阻塞的意思，例如遇到了一个IO操作，此时CPU处于空闲状态，可能会转而把CPU时间片分配给其他线程，这时
    可以成为暂停状态。Blocked状态结束后，进入Runable状态，等待系统重新分配资源。
    出现阻塞的情况大体分为如下5种：
    1.线程调用sleep方法，主动放弃占用的处理器资源
    2.线程调用了阻塞式IO方法，在该方法返回前，该线程被阻塞
    3.线程试图获得一个同步监视器，但该同步监视器正被其他线程所持有
    4.线程等待某个通知
    5.程序调用了suspend方法将该线程挂起，此方法容易导致死锁，尽量避免使用该方法。
    4)run()方法运行结束后进入销毁阶段，整个线程执行完毕。
    每个锁对象都有两个队列，一个是就绪队列，一个是阻塞队列。就绪队列存储了将要获得锁的线程，阻塞队列
    存储了被阻塞的线程。一个线程被唤醒后，才会进入就绪队列，等待CPU的调度；反之，一个线程被wait后，
    就会进入阻塞队列，等待下一次被唤醒。
 */
public class Run65 {
    public static void main(String[] args){
        try{
            Object lock = new Object();
            MyThread65_A a = new MyThread65_A(lock);
            a.start();
            Thread.sleep(50);
            MyThread65_B b = new MyThread65_B(lock);
            b.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
class MyList65{
    private static List list = new ArrayList();
    public static void add(){
        list.add("anyString");
    }
    public static int size(){
        return list.size();
    }
}
class MyThread65_A extends Thread{
    private  Object lock;
    public MyThread65_A(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        try{
            synchronized (lock){
                if (MyList65.size()!=5){
                    System.out.println("wait begin "+System.currentTimeMillis());
                    lock.wait();
                    System.out.println("wait end "+System.currentTimeMillis());
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread65_B extends Thread{
    private  Object lock;
    public MyThread65_B(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        try{
            synchronized (lock){
               for (int i=0;i<10;i++){
                   MyList65.add();
                   if (MyList65.size()==5){
                       lock.notify();
                       System.out.println("已发出通知");
                   }
                   System.out.println("添加了"+(i+1)+" 个元素");
                   Thread.sleep(1000);
               }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}