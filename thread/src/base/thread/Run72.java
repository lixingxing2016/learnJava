package base.thread;
/*
    在使用wait/notify模式时，还需要注意wait等待的条件发生了变化，也容易造成程序逻辑的混乱
    下面程序出现异常原因是有两个实现删除remove()操作的线程，他们在Thread.sleep(1000)之前
    都执行了wait()方法，呈等待状态，当加操作的线程在1秒之后被运行时，通知了所有呈wait等待
    状态的减操作的线程，那么第一个实现减操作的线程能够正确的删除list中索引为0的数据，但第二个
    实现减操作的线程则出现索引溢出的异常，因为list中仅仅添加了一个数据，也只能删除一个数据
 */

import java.util.ArrayList;
import java.util.List;

public class Run72 {
    public static void main(String[] args) throws InterruptedException{
        String lock = new String("");
        Add add = new Add(lock);
        Subtract subtract = new Subtract(lock);
        ThreadSubtract threadSubtract = new ThreadSubtract(subtract);
        threadSubtract.setName("substract1");
        threadSubtract.start();
        ThreadSubtract threadSubtract2 = new ThreadSubtract(subtract);
        threadSubtract2.setName("substract2");
        threadSubtract2.start();
        Thread.sleep(1000);
        ThreadAdd threadAdd = new ThreadAdd(add);
        threadAdd.setName("add");
        threadAdd.start();
    }
}
class Add{
    private String lock;

    public Add(String lock) {
        this.lock = lock;
    }
    public void add(){
        synchronized (lock){
            ValueObject.list.add("anyString");
            lock.notifyAll();
        }
    }
}
class Subtract{
    private String lock;

    public Subtract(String lock) {
        this.lock = lock;
    }
    public void subtract(){
        try {
            synchronized (lock){
                while (ValueObject.list.size()==0){
                    System.out.println("wait begin ThreadName="+Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("wait end ThreadName="+Thread.currentThread().getName());
                }
                /*if(ValueObject.list.size()==0){
                    System.out.println("wait begin ThreadName="+Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("wait end ThreadName="+Thread.currentThread().getName());
                }*/
                ValueObject.list.remove(0);
                System.out.println("list size="+ValueObject.list.size());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class ValueObject{
    public static List list = new ArrayList();
}
class ThreadAdd extends Thread{
    private Add p;

    public ThreadAdd(Add p) {
        this.p = p;
    }

    @Override
    public void run() {
        p.add();
    }
}
class ThreadSubtract extends Thread{
    private Subtract s;

    public ThreadSubtract(Subtract s) {
        this.s = s;
    }

    @Override
    public void run() {
        s.subtract();
    }
}