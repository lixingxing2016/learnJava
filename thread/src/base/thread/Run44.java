package base.thread;
/*
    由于对象监视器不同，所以运行结果是异步的
    同步代码块放在非同步synchronized方法中进行声明，并不能保证调用方法的线程的执行同步性，也就是线程
    调用方法的顺序是无序的，虽然在同步块中执行的顺序是同步的，这样极易出现脏读问题。
 */
public class Run44 {
    public static void main(String[] args) {
        Service44 service = new Service44();
        MyThread44_A threadA = new MyThread44_A(service);
        threadA.setName("A");
        threadA.start();
        MyThread44_B threadB = new MyThread44_B(service);
        threadB.setName("B");
        threadB.start();
    }
}
class Service44{
   private String anyString = new String();
    public void a() {
        try {
            synchronized (anyString) {
                System.out.println("a begin");
                Thread.sleep(3000);
                System.out.println("a end");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    synchronized public void b(){
        System.out.println("b begin");
        System.out.println("b end");
    }

}
class MyThread44_A extends Thread{
    private Service44 service;

    public MyThread44_A(Service44 service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.a();
    }
}
class MyThread44_B extends Thread{
    private Service44 service;

    public MyThread44_B(Service44 service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.b();
    }
}