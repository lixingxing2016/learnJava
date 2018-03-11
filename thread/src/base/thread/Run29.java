package base.thread;
/*
    在Java线程中有两种线程，一种是用户线程，另一种是守护线程
    守护线程是一种特殊的线程，当进程中不存在非守护线程了，则守护线程自动销毁。典型的守护线程就是垃圾回收线程，当进程中没有非
    守护线程了，则垃圾回收线程也就没有存在的必要了，自动销毁。
    任何一个守护线程都是整个JVM中所有非守护线程的保姆，只要当前JVM实例中存在任何一个非守护线程没有结束，守护线程就在工作，只有当
    最后一个非守护线程结束时，守护线程才随着JVM一同结束工作。Daemon的作用是为其他线程的运行提供便利服务。
 */
public class Run29 {
    public static void main(String[] args){
        try {
            MyThread29 thread = new MyThread29();
            thread.setDaemon(true);
            thread.start();
            Thread.sleep(5000);
            System.out.println("我离开thread对象也不再打印了，也就是停止了");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread29 extends Thread{
    private int i = 0;
    @Override
    public void run() {
        try{
            while (true){
                i++;
                System.out.println("i="+i);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}