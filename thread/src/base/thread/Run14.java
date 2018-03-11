package base.thread;
/*
    大多数停止一个线程的操作使用Thread.interrupt()方法，但这个方法不会终止一个正在运行的线程，还需要加入一个判断才可以完成
    线程的停止。
    在Java中有3种方法可以终止正在运行的线程：
    1）使用退出标志，使线程正常退出，也就是当run方法完成后线程终止
    2）使用stop方法强行终止线程，但不推荐使用这个方法，因为stop和suspend及resume一样，都是作废过期的方法，使用它们可能会产生不可预料的结果。
    3）使用interrupt中断线程
    本示例将调用interrupt方法来停止线程，但interrupt()方法的使用效果并不像for+break语句那样，马上就停止循环。调用interrupt()方法仅仅是在
    当前线程中打了一个停止的标志，并不是真正的停止线程。
    从运行结果来看，调用interrupt方法并没有停止线程。
      Java线程之中，一个线程的生命周期分为：初始、就绪、运行、阻塞以及结束。当然，其中也可以有四种状态，初始、就绪、运行以及结束。
       一般而言，可能有三种原因引起阻塞：等待阻塞、同步阻塞以及其他阻塞（睡眠、jion或者IO阻塞）；
       对于Java而言，
       等待阻塞是调用wait方法产生的，
       同步阻塞则是由同步块（synchronized）产生的，
       睡眠阻塞是由sleep产生的，
       jion阻塞是由jion方法产生的。
       言归正传，要中断一个Java线程，可调用线程类（Thread）对象的实例方法：interrupte()；
       然而interrupte()方法并不会立即执行中断操作；具体而言，这个方法只会给线程设置一个为true的中断标志（中断标志只是一个布尔类型的变量），
       而设置之后，则根据线程当前的状态进行不同的后续操作。如果，线程的当前状态处于非阻塞状态
       ，那么仅仅是线程的中断标志被修改为true而已；如果线程的当前状态处于阻塞状态，
       那么在将中断标志设置为true后，还会有如下三种情况之一的操作：
       如果是wait、sleep以及jion三个方法引起的阻塞，那么会将线程的中断标志重新设置为false，并抛出一个InterruptedException；
       如果在中断时，线程正处于非阻塞状态，则将中断标志修改为true,而在此基础上，一旦进入阻塞状态，则按照阻塞状态的情况来进行处理；
       例如，一个线程在运行状态中，其中断标志被设置为true,则此后，一旦线程调用了wait、jion、sleep方法中的一种，
       立马抛出一个InterruptedException，且中断标志被清除，重新设置为false。
 */
public class Run14 {
    public static void main(String[] args){
        try{
            MyThread14 thread = new MyThread14();
            thread.start();
            Thread.sleep(2000);
            thread.interrupt();
        }catch (InterruptedException e){
            System.out.println("main catch");
            e.printStackTrace();
        }
    }
}
class MyThread14 extends Thread{
    @Override
    public void run() {
        super.run();
        for (int i=0;i<50000;i++){
            System.out.println("i="+(i+1));
        }
    }
}