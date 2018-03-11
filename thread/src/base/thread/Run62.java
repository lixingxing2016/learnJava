package base.thread;

import java.util.ArrayList;
import java.util.List;

/*
    线程是操作系统中独立的个体，线程间通信是线程成为整体的必用方案之一，可以说，使线程间进行通信后，
    系统之间的交互性更强大，在大大提高CPU利用率的同时还会使程序员对各线程任务在处理的过程中进行
    有效的把控与监督。
    本章重点：
    1）使用wait/notify实现线程间通信
    2）生产者/消费者模式的实现
    3）方法join的使用
    4）ThreadLocal类的使用
    下面是不使用等待/通知机制实现线程间通信 使用sleep()结合while(true)死循环来实现多个线程间通信.
    虽然两个线程间实现了通信，但有一个弊端是，线程B不停的通过while循环轮询机制来检测某一个条件，这样
    会浪费CPU资源。
    如果轮询的时间间隔很小，更浪费CPU资源；如果轮询的时间间隔很大，有可能会娶不到想得到的数据，所以就
    需要一种机制来实现减少CPU的资源浪费，而且还可以实现在多个线程间通信，它就是wait/notify机制
 */
public class Run62 {
    public static void main(String[] args){
        MyList service = new MyList();
        MyThread62_A a = new MyThread62_A(service);
        a.setName("a");
        a.start();
        MyThread62_B b = new MyThread62_B(service);
        b.setName("b");
        b.start();
    }
}
class MyList{
    private List list = new ArrayList();
    public void add(){
        list.add("hello");
    }
    public int size(){
        return list.size();
    }
}
class MyThread62_A extends Thread{
    private MyList list;

    public MyThread62_A(MyList list) {
        this.list = list;
    }

    @Override
    public void run() {
        try {
            for (int i=0;i<10;i++){
                list.add();
                System.out.println("添加了"+(i+1)+"个元素");
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread62_B extends Thread{
    private MyList list;

    public MyThread62_B(MyList list) {
        this.list = list;
    }

    @Override
    public void run() {
        try {
           while (true){
               if(list.size()==5){
                   System.out.println("list.size()==5，线程b要退出了");
                   throw new InterruptedException();
               }
           }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}