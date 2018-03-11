package base.thread;

public class Run49 {
    public static void main(String[] args){
        Service49 service = new Service49();
        Thread49_A threadA = new Thread49_A(service);
        threadA.setName("A");
        threadA.start();
        Thread49_B threadB = new Thread49_B(service);
        threadB.setName("B");
        threadB.start();
    }
}
class Service49{
    public static void print(Object object){
        try{
            synchronized (object){
                while (true){
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Thread49_A extends Thread{
    private Service49 service;

    public Thread49_A(Service49 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.print(new Object());
    }
}
class Thread49_B extends Thread{
    private Service49 service;

    public Thread49_B(Service49 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.print(new Object());
    }
}