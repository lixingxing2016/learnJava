package base.thread;
/*
    1)A线程先持有object对象的Lock锁，B线程可以以异步方式调用object对象中的非synchronized类型的方法
    2）A线程先持有object对象的Lock锁，B线程如果在这时调用object对象中的synchronized类型的方法则需要等待
    也就是同步。
 */
public class Run33 {
    public static void main(String[] args){
        MyObject33 object = new MyObject33();
        Thread33_A threadA = new Thread33_A(object);
        threadA.setName("A");
        Thread33_B threadB = new Thread33_B(object);
        threadB.setName("B");
        threadA.start();
        threadB.start();
    }
}
class MyObject33{
    //同步方法
    synchronized public void methodA(){
        try{
            System.out.println("begin methodA threadName = "+Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("end endTime = "+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //非同步方法
    /*public void methodB(){
        try{
            System.out.println("begin methodB threadName = "+Thread.currentThread().getName()+" beginTime = "+System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }*/
    synchronized public void methodB(){
        try{
            System.out.println("begin methodB threadName = "+Thread.currentThread().getName()+" beginTime = "+System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Thread33_A extends Thread{
    private MyObject33 object;

    public Thread33_A(MyObject33 object) {
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        object.methodA();
    }
}
class Thread33_B extends Thread{
    private MyObject33 object;

    public Thread33_B(MyObject33 object) {
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        object.methodB();
    }
}