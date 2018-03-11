package base.thread;
/*
    关键字synchronized还可以应用在static静态方法上，如果这样写，那是对当前*.java文件对应的Class类进行
    持锁。
    下面程序异步的原因是持有不同的锁，线程C是对象锁，线程A、B是Class锁，而Class锁可以对类的所有实例起作用
 */

public class Run46 {
    public static void main(String[] args){
        Service46 service = new Service46();
        Thread46_A threadA = new Thread46_A(service);
        threadA.setName("A");
        threadA.start();
        Thread46_B threadB = new Thread46_B(service);
        threadB.setName("B");
        threadB.start();
        Thread46_C threadC = new Thread46_C(service);
        threadC.setName("C");
        threadC.start();
    }
}
class Service46{
    synchronized public static void printA(){
        try{
            System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"进入printA");
            Thread.sleep(3000);
            System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"离开printA");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    synchronized public static void printB(){
        System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"进入printB");
        System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"离开printB");
    }
    synchronized public  void printC(){
        System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"进入printC");
        System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"离开printC");
    }
}

class Thread46_A extends Thread{
    private Service46 service;

    public Thread46_A(Service46 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.printA();
    }
}
class Thread46_B extends Thread{
    private Service46 service;

    public Thread46_B(Service46 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.printB();
    }
}
class Thread46_C extends Thread{
    private Service46 service;

    public Thread46_C(Service46 service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.printC();
    }
}
