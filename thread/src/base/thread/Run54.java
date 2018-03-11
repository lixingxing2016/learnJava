package base.thread;
/*
    锁对象的改变
    在将任何数据类型作为同步锁时，需要注意，是否有多个线程同时持有锁对象，如果同时持有相同的
    锁对象，则这些线程之间就是同步的；如果分别获得锁对象，这些线程之间就是异步的。

    去掉主线程中代码Thread.sleep(50);，线程A和B持有的锁都是"123"，虽然将锁改成了"456"，但结果
    还是同步的，因为A和B共同争抢的锁是"123".
    注意：只要对象不变，即使对象的属性被改变，运行的结果还是同步的。
 */
public class Run54 {
    public static void main(String[] args) throws InterruptedException{
        Service54 service = new Service54();
        Thread54_A threadA = new Thread54_A(service);
        threadA.setName("A");
        Thread54_B threadB = new Thread54_B(service);
        threadB.setName("B");
        threadA.start();
        //Thread.sleep(50);//A线程启动50毫秒后线程B获取的锁是"456"，不再是"123"
        threadB.start();
    }
}
class Service54{
    private String lock = "123";
    public void testMethod(){
        try{
            synchronized (lock){
                System.out.println(Thread.currentThread().getName()+ " beginTime="+System.currentTimeMillis());
                lock = "456";
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+" endTime="+System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Thread54_A extends Thread{
    private Service54 service;

    public Thread54_A(Service54 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.testMethod();
    }
}
class Thread54_B extends Thread{
    private Service54 service;

    public Thread54_B(Service54 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.testMethod();
    }
}
