package base.thread;
/*
    多个线程调用同一个对象中的不同名称的synchronized同步方法或synchronized(this)同步代码块时，
    调用的效果就是按照顺序执行，也就是同步的，阻塞的
    说明synchronized同步方法或者synchronized(this)同步代码块有两种作用。
    1）对于其他synchronized同步方法或synchronized(this)同步代码块调用呈现阻塞状态
    2）同一时间只有一个线程可以执行synchronized同步方法或synchronized(this)同步代码块中的代码
    synchronized(非this对象)格式的作用只有一种：synchronized(非this对象)同步代码块
    1）在多个线程持有“对象监视器”为同一个对象的前提下，同一时间只有一个线程可以执行
    synchronized(非this对象)同步代码块中的代码。
    2）当持有“对象监视器”为同一个对象的前提下，同一时间只有一个线程可以执行
    synchronized(非this对象)同步代码块中的代码。
    锁非this对象具有一定得优点：如果在一个类中有很多个synchronized方法，这是虽然能实现同步，
    但会受到阻塞，所以影响运行效率；但如果使用同步代码块锁非this对象，则
    synchronized(非this)代码块中的程序与同步方法是异步的，不与其他锁this同步方法争抢this锁，
    则可以提高运行效率。
    使用synchronized(非this对象X)同步代码块格式进行同步操作时，对象监视器必须是同一个对象，如果不是
    同一个对象监视器，运行的结果就是异步调用了，会交叉运行。
 */
public class Run43 {
    public static void main(String[] args){
        Service43 service = new Service43();
        MyThread43_A threadA = new MyThread43_A(service);
        threadA.setName("A");
        threadA.start();
        MyThread43_B threadB = new MyThread43_B(service);
        threadB.setName("B");
        threadB.start();
    }
}
class Service43{
    private String username;
    private String password;
    //private String anyString = new String();最开始部分

    public void setUsernamePassword(String username,String password) {
        try {
            String anyString = new String();//修改后部分，每调用一次该方法创建一个anyString对象
            synchronized (anyString) {
                System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"进入同步代码块");
                this.username = username;
                Thread.sleep(3000);
                this.password = password;
                System.out.println("线程名称为："+Thread.currentThread().getName()+"在"+System.currentTimeMillis()+"离开同步代码块");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
class MyThread43_A extends Thread{
    private Service43 service;

    public MyThread43_A(Service43 service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.setUsernamePassword("a","aa");
    }
}
class MyThread43_B extends Thread{
    private Service43 service;

    public MyThread43_B(Service43 service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.setUsernamePassword("b","bb");
    }
}