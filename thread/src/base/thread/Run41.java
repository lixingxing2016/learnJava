package base.thread;
/*
    在使用同步synchronized(this)代码块时需要注意，当一个线程访问object的一个synchronized(this)代码块时，
    其他线程对同一个object中所有其他synchronized(this)同步代码块的访问将被阻塞，这说明
    synchronized使用的对象监视器是同一个。
 */
public class Run41 {
    public static void main(String[] args){
        ObjectService41 service = new ObjectService41();
        MyThread41_A threadA = new MyThread41_A(service);
        threadA.setName("A");
        threadA.start();
        MyThread41_B threadB = new MyThread41_B(service);
        threadB.setName("B");
        threadB.start();
    }

}
class ObjectService41{
    public void serviceMethodA(){
        try{
            synchronized (this){
                System.out.println("A begin time="+System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("A end time="+System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void serviceMethodB(){
        synchronized (this){
            System.out.println("B begin time="+System.currentTimeMillis());
            System.out.println("B end time="+System.currentTimeMillis());
        }
    }
}
class MyThread41_B extends Thread{
    private ObjectService41 service;

    public MyThread41_B(ObjectService41 service) {
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        service.serviceMethodA();
    }
}
class MyThread41_A extends Thread{
    private ObjectService41 service;

    public MyThread41_A(ObjectService41 service) {
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        service.serviceMethodB();
    }
}
