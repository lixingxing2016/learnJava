package base.thread;
/*
    多生产多消费：操作值假死
    假死的线性其实就是线程进入WAITING等待状态。如果全部线程都进入WAITING状态，则程序就
    不再执行任何业务功能了，整个项目呈停止状态，这在使用生产者与消费者模式中经常遇到。
    在代码中确实已经通过wait/notify进行通信了，但不能保证notify唤醒的是不同类，也许是
    同类，比如"生产者"唤醒"生产者"，或者"消费者"唤醒"消费者"这样的情况，如果按照这样情况
    运行的比率积少成多，就会导致所有的线程都不能继续运行下去，就都在等待，都呈WAITING状态，
    程序最后也就呈假死状态，不能继续运行下去了。


    分析步骤：
    1）生产者1进行生产，生产完毕后发出通知（但此通知属于通知过早，因为消费者线程还未启动），并
    释放锁，准备进入下一次的while循环
    2）生产者1进入了下一次while循环，迅速再次持有锁，发现产品并没有被消费，所以生产者1呈现等待状态
    3）生产者2被启动后，生产者2发现产品还没有被消费，所以生产者2也呈现等待状态
    4）消费者2被启动后，消费者2持有锁，将产品消费并发出通知（发出的通知唤醒了生产者1），运行结束后
    释放锁，等待消费者2进入下次循环。
    5）消费者2进入下一次的while循环，并持有锁，发现产品并未生产，所以释放锁并呈等待状态
    6）消费者1被启动，快速持有锁，发现产品并未生产，所以释放锁并呈等待状态。
    7）由于消费者2已经将产品消费，唤醒了生产者1并顺利生产后释放锁，并发出通知（此通知唤醒了生产者2），
    生产者1准备进入下一次的while
    8）生产者1进入下一次while循环再次持有锁，发现产品并未消费，所以生产者1也呈等待状态。
    9)生产者1唤醒了生产者2，生产者2发现产品还未被消费，所以线程2也呈等待状态。

    总结：假死出现的主要原因是有可能连续唤醒同类
 */
public class Run74 {
    public static void main(String[] args){
        String lock = new String("");
        Product2 product = new Product2(lock);
        Consumer2 consumer = new Consumer2(lock);
        ThreadP2[] ps = new ThreadP2[2];
        ThreadC2[] cs = new ThreadC2[2];
        for (int i=0;i<2;i++){
            ps[i] = new ThreadP2(product);
            ps[i].setName("生产者"+(i+1));
            cs[i] = new ThreadC2(consumer);
            cs[i].setName("消费者"+(i+1));
            ps[i].start();
            cs[i].start();
        }
    }
}
class Product2{
    private String lock;

    public Product2(String lock) {
        this.lock = lock;
    }
    public void setValue(){
        try{
            synchronized (lock){
                while(!ValueObject73.value.equals("")){
                    System.out.println("生产者"+Thread.currentThread().getName()+" WAITING了");
                    lock.wait();
                }
                System.out.println("生产者 "+Thread.currentThread().getName()+"Running了");
                String value = System.currentTimeMillis()+"_"+System.nanoTime();
                ValueObject73.value = value;
                lock.notify();//此通知属于通知过早，并释放锁
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Consumer2{
    private String lock;

    public Consumer2(String lock) {
        this.lock = lock;
    }
    public void getValue(){
        try{
            synchronized (lock){
                while(ValueObject73.value.equals("")){
                    System.out.println("消费者 "+Thread.currentThread().getName()+" WAITING了");
                    lock.wait();
                }
                System.out.println("消费者"+Thread.currentThread().getName()+"Running");
                ValueObject73.value = "";
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class ThreadP2 extends Thread{
    private Product2 product;

    public ThreadP2(Product2 product) {
        this.product = product;
    }

    @Override
    public void run() {
        while (true){
            product.setValue();
        }
    }
}
class ThreadC2 extends Thread{
    private Consumer2 consumer;

    public ThreadC2(Consumer2 consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true){
            consumer.getValue();
        }
    }
}