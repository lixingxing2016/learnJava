package base.thread;

import java.util.ArrayList;
import java.util.List;

/*
    一生产与一消费---操作栈：解决wait条件改变与假死
    本示例是使用一个生产者向堆栈List对象中放入数据，而一个从List堆栈中取出数据。List的最大
    容量还是1、
    一生产与多消费--操作栈
 */
public class Run75 {
    //一生产者一消费者
   /* public static void main(String[] args) {
        MyStack myStack = new MyStack();
        Product5 p = new Product5(myStack);
        Consumer5 c = new Consumer5(myStack);
        ThreadP5 pThread = new ThreadP5(p);
        ThreadC5 cThread = new ThreadC5(c);
        pThread.start();
        cThread.start();
    }*/
   //一生产者多消费者
    /*
        出现问题的原因是在MyStack.java类中使用了if语句作为条件判断，因为条件发生改变时并没有得到及时的响应，
        所以多个呈wait状态的线程被唤醒，继而执行list.remove(0)代码而出现异常。
        解决这个的方法是将if改成while语句即可。
     */
    public static void main(String[] args){
        MyStack myStack = new MyStack();
        Product5 p = new Product5(myStack);
        Consumer5 c1 = new Consumer5(myStack);
        Consumer5 c2 = new Consumer5(myStack);
        Consumer5 c3 = new Consumer5(myStack);
        Consumer5 c4 = new Consumer5(myStack);
        Consumer5 c5 = new Consumer5(myStack);
        ThreadP5 pThread = new ThreadP5(p);
        ThreadC5 cThread1 = new ThreadC5(c1);
        ThreadC5 cThread2 = new ThreadC5(c2);
        ThreadC5 cThread3 = new ThreadC5(c3);
        ThreadC5 cThread4 = new ThreadC5(c4);
        ThreadC5 cThread5 = new ThreadC5(c5);
        pThread.start();
        cThread1.start();
        cThread2.start();
        cThread3.start();
        cThread4.start();
        cThread5.start();
    }
}
class MyStack{
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
class Product5 {
    private MyStack myStack;

    public Product5(MyStack myStack) {
        this.myStack = myStack;
    }
    public void pushService(){
        myStack.push();
    }
}
class Consumer5{
    private MyStack myStack;

    public Consumer5(MyStack myStack) {
        this.myStack = myStack;
    }
    public void popService(){
        System.out.println("pop="+myStack.pop());
    }
}
class ThreadP5 extends Thread{
    private Product5 p;

    public ThreadP5(Product5 p) {
        this.p = p;
    }

    @Override
    public void run() {
        while (true){
            p.pushService();
        }
    }
}
class ThreadC5 extends Thread{
    private Consumer5 c;

    public ThreadC5(Consumer5 c) {
        this.c = c;
    }

    @Override
    public void run() {
        while (true){
            c.popService();
        }
    }
}