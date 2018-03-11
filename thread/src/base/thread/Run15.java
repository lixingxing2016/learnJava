package base.thread;
/*
    interrupted()用来判断thread对象所代表的线程
    测试当前线程是否已经中断，线程的中断状态由该方法清除。换句话说，如果连续两次调用该方法，则第二次会返回false（在第一次调用已经清除了
    中断状态后，且第二次调用检查完中断状态之前，当前线程再次中断的情况除外）。
    当线程被创建并启动以后，它既不是一启动就进入了执行状态，也不是一直处于执行状态。
    在线程的生命周期中，它要经过新建(New)、就绪（Runnable）、运行（Running）、阻塞(Blocked)和死亡(Dead)5种状态。
    尤其是当线程启动以后，它不可能一直"霸占"着CPU独自运行，所以CPU需要在多条线程之间切换，于是线程状态也会多次在运行、阻塞之间切换
    1. 新建状态，当程序使用new关键字创建了一个线程之后，该线程就处于新建状态，此时仅由JVM为其分配内存，并初始化其成员变量的值
    2. 就绪状态，当线程对象调用了start()方法之后，该线程处于就绪状态。Java虚拟机会为其创建方法调用栈和程序计数器，等待调度运行
    3. 运行状态，如果处于就绪状态的线程获得了CPU，开始执行run()方法的线程执行体，则该线程处于运行状态
    4. 阻塞状态，当处于运行状态的线程失去所占用资源之后，便进入阻塞状态
    5. 在线程的生命周期当中，线程的各种状态的转换过程
 */
public class Run15 {
    public static void main(String[] args){
        try{
            MyThread15 thread = new MyThread15();
            thread.start();
            Thread.sleep(10000);//在main函数里面，用于暂停main主线程
            thread.interrupt();//给thread表示的线程做一个中断标志
            System.out.println("是否停止1？="+thread.interrupted());
            System.out.println("是否停止2？="+thread.interrupted());
        }catch (InterruptedException e){
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end!");
    }
}
class MyThread15 extends Thread{
    @Override
    public void run() {
        super.run();
        for (int i=0;i<300000;i++){
            System.out.println("i="+(i+1));
        }
    }
}
