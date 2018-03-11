package base.thread;
/*
    多个线程调用同一个用关键字synchronized声明的方法一定是排队运行的。
    只有共享资源的读写访问才需要同步化操作，如果不是共享资源，那么没有同步必要，如
    下面代码。

 */
public class Run32 {
    public static void main(String[] args){
        MyObject object = new MyObject();
        Thread32_A threadA = new Thread32_A(object);
        threadA.setName("A");
        Thread32_B threadB = new Thread32_B(object);
        threadB.setName("B");
        threadB.start();
        threadA.start();
    }
}
class MyObject{
    //非同步方法
    /*public void method(){
        try{
            System.out.println("begin method threadName = "+Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }*/
    synchronized public void method(){
        try{
            System.out.println("begin method threadName = "+Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Thread32_A extends Thread{
    private MyObject object;

    public Thread32_A(MyObject object) {
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        object.method();
    }
}
class Thread32_B extends Thread{
    private MyObject object;

    public Thread32_B(MyObject object) {
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        object.method();
    }
}