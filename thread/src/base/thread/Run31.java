package base.thread;
/*
    多个对象对应多个锁
    下面示例是两个线程分别访问同一个类的两个不同实例的相同名称的同步方法，效果却是以异步方式运行的。
    由于创建了2个业务对象，在系统中产生了2个锁。
    synchronized取得的锁都是对象锁，而不是把一段代码或方法当做锁。
    在多个线程访问的是同一个对象的前提下，哪个线程先执行带synchronized关键字的方法，哪个线程就持有
    该方法所属对象的锁Lock，其他线程只能呈现等待状态。
    但如果多个线程访问多个对象，则JVM会创建多个锁。
 */
public class Run31 {
    public static void main(String[] args){
        HasSelfPrivateNum31 numRef1 = new HasSelfPrivateNum31();
        HasSelfPrivateNum31 numRef2 = new HasSelfPrivateNum31();
        ThreadA_31 threadA = new ThreadA_31(numRef1);
        threadA.start();
        ThreadB_31 threadB = new ThreadB_31(numRef2);
        threadB.start();
    }
}
class HasSelfPrivateNum31{
    private int num = 0;
    //同步方法
    synchronized public void addI(String username){
        try{
            if(username.equals("a")){
                num = 100;
                System.out.println("a set over");
                Thread.sleep(2000);
            }else{
                num = 200;
                System.out.println("b set over");
            }
            System.out.println(username + " num = "+num);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class ThreadA_31 extends Thread{
    private HasSelfPrivateNum31 numRef;

    public ThreadA_31(HasSelfPrivateNum31 numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super.run();
        numRef.addI("a");
    }
}
class ThreadB_31 extends Thread{
    private HasSelfPrivateNum31 numRef;

    public ThreadB_31(HasSelfPrivateNum31 numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super.run();
        numRef.addI("b");
    }
}