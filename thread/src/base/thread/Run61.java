package base.thread;
/*
    关键字synchronized可以使多个线程访问同一个资源具有同步性，而且它还具有将线程工作内存中私有变量与
    公共内存中的变量同步功能，在下面程序中验证.
    得到这个结果是各线程间的数据没有可见性造成的，而关键字synchronized可以具有可见性。
    关键字synchronized可以保证在同一时刻，只有一个线程可以执行某一个方法或者某一个代码块。它
    包含两个特征：互斥性和可见性。同步synchronized不仅可以解决一个线程看到对象处于不一致的状态，还可以
    保证进入同步方法或者同步代码块的每一个线程，都看到由同一个锁保护之前所有的修改效果。
 */
public class Run61 {
    public static void main(String[] args){
        try {
            MyService61 service = new MyService61();
            MyThread61_A a = new MyThread61_A(service);
            a.start();
            Thread.sleep(1000);
            MyThread61_B b = new MyThread61_B(service);
            b.start();
            System.out.println("已经发起停止命令了");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class MyService61{
    private boolean isContinueRun = true;
   /* public void runMethod(){
        while (isContinueRun==true){

        }
        System.out.println("停下来了！");
    }*/
   //使用同步代码块可以保证可见性
    public void runMethod(){
        String anyString = new String();
        while (isContinueRun==true){
            synchronized (anyString){

            }
        }
        System.out.println("停下来了！");
    }
    public void stopMethod(){
        isContinueRun = false;
    }
}
class MyThread61_A extends Thread{
    private MyService61 service;

    public MyThread61_A(MyService61 service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.runMethod();
    }
}
class MyThread61_B extends Thread{
    private MyService61 service;

    public MyThread61_B(MyService61 service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.stopMethod();
    }
}