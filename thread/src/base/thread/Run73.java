package base.thread;
/*
    等待通知模式最经典的案例是生产者/消费者模式，但此模式在使用上有几种变形，还有一些注意事项，但
    原理都是基于wait/notify.
    本示例是1个生产者和1个消费者进行数据的交互，在控制台中打印的日志get和set是交替运行的。
 */
public class Run73 {
    public static void main(String[] args){
        String lock = new String("");
        Product1 product = new Product1(lock);
        Consumer1 consumer = new Consumer1(lock);
        ThreadP p = new ThreadP(product);
        ThreadC c = new ThreadC(consumer);
        p.start();
        c.start();
    }

}
class Product1{
    private String lock;

    public Product1(String lock) {
        this.lock = lock;
    }
    public void setValue(){
        try{
            synchronized (lock){
                if(!ValueObject73.value.equals("")){
                    lock.wait();
                }
                String value = System.currentTimeMillis()+"_"+System.nanoTime();
                System.out.println("set的值是："+value);
                ValueObject73.value = value ;
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Consumer1{
    private String lock;

    public Consumer1(String lock) {
        this.lock = lock;
    }
    public void getValue(){
        try{
            synchronized (lock){
                if(ValueObject73.value.equals("")){
                    lock.wait();
                }
                System.out.println("get的值是"+ValueObject73.value);
                ValueObject73.value = "";
                lock.notify();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class ThreadP extends Thread{
    private Product1 product;

    public ThreadP(Product1 product) {
        this.product = product;
    }

    @Override
    public void run() {
        while(true){
            product.setValue();
        }
    }
}
class ThreadC extends Thread{
    private Consumer1 consumer;

    public ThreadC(Consumer1 consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            consumer.getValue();
        }
    }
}
class ValueObject73{
    public static String value="";
}