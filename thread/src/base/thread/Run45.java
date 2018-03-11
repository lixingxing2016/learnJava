package base.thread;

import java.util.ArrayList;
import java.util.List;

/*
    线程A与线程B执行时异步的，有可能出现脏读环境。由于线程执行方法的顺序不确定，所以当A和B
    两个线程执行带有分支判断的方法时，就会出现逻辑上的错误，有可能出现脏读

    synchronized(非this对象x)格式的写法将x对象本质作为对象监视器，这样就有如下3个结论：
    1）当多个线程同时执行synchronized(x){}同步代码块时呈现同步效果
    2）当其他线程执行x对象中synchronized同步方法时呈现同步效果
    3）当其他线程执行x对象方法里面的synchronized(thhis)代码块时也呈现同步效果。
    需要注意：如果其他线程调用不加synchronized关键字的方法时，还是异步调用
 */
public class Run45 {
    public static void main(String[] args)throws InterruptedException{
        MyOneList list = new MyOneList();
        MyThread45_A threadA = new MyThread45_A(list);
        threadA.setName("A");
        threadA.start();
        MyThread45_B threadB = new MyThread45_B(list);
        threadB.setName("B");
        threadB.start();
        Thread.sleep(6000);
        System.out.println("listSize="+list.getSize());
    }

}
class MyOneList{
    private List list = new ArrayList();
    synchronized public void add(String data){
        list.add(data);
    }
    synchronized public int getSize(){
        return list.size();
    }
}
class MyService45 {
    public MyOneList addServiceMethod(MyOneList list, String data) {
        try {
            //这里不进行同步操作，脏读出现了，原因是两个线程以异步方式返回list参数的size大小
            synchronized (list) {//由于list参数对象在项目中是一份实例，单例的，也正需要对list参数的
                //getSize（）方法做同步的调用，所以就对list参数进行同步处理
                if (list.getSize() < 1) {
                    Thread.sleep(2000);//模拟从远程需要花费2秒取回数据
                    list.add(data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
class MyThread45_A extends Thread{
    private MyOneList list;

    public MyThread45_A(MyOneList list) {
        this.list = list;
    }

    @Override
    public void run() {
        MyService45 service = new MyService45();
        service.addServiceMethod(list,"A");
    }
}
class MyThread45_B extends Thread{
    private MyOneList list;

    public MyThread45_B(MyOneList list) {
        this.list = list;
    }

    @Override
    public void run() {
        MyService45 service = new MyService45();
        service.addServiceMethod(list,"B");
    }
}