package base.thread;
/*
    同步synchronized方法无线等待与解决，
    同步方法容易造成死循环
    线程B永远得不到运行的机会，锁死了
 */
public class Run50 {
    public static void main(String[] args){
        Service50 service = new Service50();
        Thread50_A threadA = new Thread50_A(service);
        threadA.start();

    }
}
class Service50{
    synchronized public void methodA(){
        System.out.println("methodA begin");
        boolean isContinueRun = true;
        while (isContinueRun){

        }
        System.out.println("methodA end");
    }
    synchronized public void methodB(){
        System.out.println("methodB begin");
        System.out.println("methodB end");
    }
}
class Thread50_A extends Thread{
    private Service50 service;

    public Thread50_A(Service50 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.methodA();
    }
}
class Thread50_B extends Thread{
    private Service50 service;

    public Thread50_B(Service50 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.methodB();
    }
}