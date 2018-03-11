package base.thread;
/*
    当两个并发线程访问同一个对象object中的synchronized(this)同步代码块时，一段时间内只能
    有一个线程被执行，另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码。‘

 */
public class Run39 {
    public static void main(String[] args){
        ObjectService service = new ObjectService();
        Thread39_A threadA = new Thread39_A(service);
        threadA.setName("a");
        threadA.start();
        Thread39_B threadB = new Thread39_B(service);
        threadB.setName("b");
        threadB.start();
    }
}

class ObjectService{
    public void serviceMethod(){
        try{
            synchronized (this){
                System.out.println("begin time="+System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("end time="+System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Thread39_A extends Thread{
    private ObjectService service;

    public Thread39_A(ObjectService service) {
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        service.serviceMethod();
    }
}
class Thread39_B extends Thread{
    private ObjectService service;

    public Thread39_B(ObjectService service) {
        this.service = service;
    }

    @Override
    public void run() {
        super.run();
        service.serviceMethod();
    }
}