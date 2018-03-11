package base.thread;
/*
    关键字synchronized拥有锁重入功能，也就是在使用synchronized时，当一个线程得到一个对象锁之后，
    再次请求此对象锁时可以再次获得该对象的锁的。也说明，
    sychronized方法/块的内部调用本类的其他synchronized方法/块时是永远可以得到锁的
    可重入锁是自己可以再次获取自己的内部锁，比如A线程获得某个对象的锁，此时这个对象锁还没有是非，当其
    再次想要获取这个对象的锁时还是可以获得，如果不可锁重入的话，会造成死锁。
 */
public class Run35 {
    public static void main(String[] args){
        Thread35 thread = new Thread35();
        thread.start();
    }
}
class Service{
    synchronized public void service1(){
        System.out.println("service1");
        service2();
    }
    synchronized public void service2(){
        System.out.println("service2");
        service3();
    }
    synchronized public void service3(){
        System.out.println("service3");
    }
}
class Thread35 extends Thread{
    @Override
    public void run() {
        Service service = new Service();
        service.service1();
    }
}
