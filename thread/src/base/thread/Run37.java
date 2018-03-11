package base.thread;
/*
    出现异常，锁自动释放
    当一个线程执行的代码出现异常时，其所持有的锁会自动释放
    如下所示，线程a出现异常并释放锁，线程b进行方法正常打印。
 */
public class Run37 {
    public static void main(String[] args){
        try{
            Service37 service = new Service37();
            Thread37_A threadA = new Thread37_A(service);
            threadA.setName("a");
            threadA.start();
            Thread.sleep(500);
            Thread37_B threadB = new Thread37_B(service);
            threadB.setName("b");
            threadB.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Service37{
    synchronized public void testMethod(){
        if (Thread.currentThread().getName().equals("a")){
            System.out.println("ThreadName="+Thread.currentThread().getName()+ " run beginTime="+System.currentTimeMillis());
            int i = 1;
            while(i==1){
                if ((""+Math.random()).substring(0,8).equals("0.123456")){
                    System.out.println("ThreadName="+Thread.currentThread().getName()+ " run exceptionTime="+System.currentTimeMillis());
                    Integer.parseInt("a");
                }
            }
        }else {
            System.out.println("Thread B run Time="+System.currentTimeMillis());
        }
    }
}
class Thread37_A extends Thread{
    private Service37 service ;
    public Thread37_A(Service37 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.testMethod();
    }
}
class Thread37_B extends Thread{
    private Service37 service ;
    public Thread37_B(Service37 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.testMethod();
    }
}
