package base.thread;

import java.util.ArrayList;
import java.util.List;

/*
    多生产与一消费：操作栈
    本示例是使用生产者向堆栈List对象中放入数据，使用消费者从List堆栈中取出数据，
    List的最大容量还是1，实验环境是多个生产者与一个消费者
 */
public class Run76 {
    public static void main(String[] args){
        MyStack6 myStack = new MyStack6();
        Product6 p1 = new Product6(myStack);
        Product6 p2 = new Product6(myStack);
        Product6 p3 = new Product6(myStack);
        Product6 p4 = new Product6(myStack);
        Product6 p6 = new Product6(myStack);
        ThreadP6 pThread1 = new ThreadP6(p1);
        ThreadP6 pThread2 = new ThreadP6(p2);
        ThreadP6 pThread3 = new ThreadP6(p3);
        ThreadP6 pThread4 = new ThreadP6(p4);
        ThreadP6 pThread5 = new ThreadP6(p6);
        pThread1.start();
        pThread2.start();
        pThread3.start();
        pThread4.start();
        pThread5.start();
        Consumer6 c1 = new Consumer6(myStack);
        ThreadC6 cThread = new ThreadC6(c1);
        cThread.start();
    }
}
class MyStack6{
    private List list = new ArrayList();
    synchronized public void push(){
        try {
            /*if(list.size()==1){
                this.wait();
            }*/
            while(list.size()==1){
                this.wait();
            }
            list.add("anyString="+Math.random());
            //this.notify();随机唤醒一个线程，唤醒一个同类的线程，则会导致假死
            this.notifyAll();//唤醒所有正在等待的线程
            System.out.println("push="+list.size());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    synchronized public String pop(){
        String returnValue="";
        try {
           /* if (list.size()==0){
                System.out.println("pop操作中的："+Thread.currentThread().getName()+"线程呈wait状态");
                this.wait();
            }*/
            while (list.size()==0) {
                System.out.println("pop操作中的：" + Thread.currentThread().getName() + "线程呈wait状态");
                this.wait();
            }
            returnValue = ""+list.get(0);
            list.remove(0);
            //this.notify();
            this.notifyAll();
            System.out.println("pop="+list.size());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return returnValue;
    }
}
class Product6 {
    private MyStack6 myStack;

    public Product6(MyStack6 myStack) {
        this.myStack = myStack;
    }
    public void pushService(){
        myStack.push();
    }
}
class Consumer6{
    private MyStack6 myStack;

    public Consumer6(MyStack6 myStack) {
        this.myStack = myStack;
    }
    public void popService(){
        System.out.println("pop="+myStack.pop());
    }
}
class ThreadP6 extends Thread{
    private Product6 p;

    public ThreadP6(Product6 p) {
        this.p = p;
    }

    @Override
    public void run() {
        while (true){
            p.pushService();
        }
    }
}
class ThreadC6 extends Thread{
    private Consumer6 c;

    public ThreadC6(Consumer6 c) {
        this.c = c;
    }

    @Override
    public void run() {
        while (true){
            c.popService();
        }
    }
}
