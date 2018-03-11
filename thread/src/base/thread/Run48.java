package base.thread;
/*
    在JVM中具有String常量池缓存的功能
    将synchronized(string) 同步块与String联合使用时，要注意常量池带来的一些例外。
    出现这样的情况是因为String的两个值都是AA，两个线程持有相同的锁，所以造成B不能执行，
    这就是String常量池锁带来的问题，因此在大多数情况下，同步synchronized代码块都不使用
    String作为锁对象，而改用其他，比如new Object()实例化一个Object对象，但它并不放入缓存中。
 */
public class Run48 {
    public static void main(String[] args){
        Service48 service = new Service48();
        Thread48_A threadA = new Thread48_A(service);
        threadA.setName("A");
        threadA.start();
        Thread48_B threadB = new Thread48_B(service);
        threadB.setName("B");
        threadB.start();
    }
}
class Service48{
        public static void print(String string){
            try{
                synchronized (string){
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
class Thread48_A extends Thread{
    private Service48 service;

    public Thread48_A(Service48 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.print("AA");
    }
}
class Thread48_B extends Thread{
    private Service48 service;

    public Thread48_B(Service48 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.print("AA");
    }
}
