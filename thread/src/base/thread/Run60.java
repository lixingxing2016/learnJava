package base.thread;

import java.util.concurrent.atomic.AtomicLong;

/*
    原子类在具有有逻辑性的情况下输出结果也具有随机性
    下面程序出现的随机性是因为addAndGet()方法是原子性的，但方法和方法之间的调用
    却不是原子性的，解决这样的问题必须要用同步
 */
public class Run60 {
    public static void main(String[] args){
        try {
            MyService60 service = new MyService60();
            MyThread60[] array = new MyThread60[5];//创建数组，然后对数组的每一个元素进行初始化
            for (int i=0;i<array.length;i++){
                array[i] = new MyThread60(service);
            }
            for(int i=0;i<array.length;i++){
                array[i].start();
            }
            Thread.sleep(1000);
            System.out.println(MyService60.aiRef.get());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyService60{
    public static AtomicLong aiRef = new AtomicLong();
    //非同步方法
    /*public void addNum(){
        System.out.println(Thread.currentThread().getName()+" 加了100之后的值是："+aiRef.addAndGet(100));
        aiRef.addAndGet(1);
    }*/
    //同步方法
    synchronized public void addNum(){
        System.out.println(Thread.currentThread().getName()+" 加了100之后的值是："+aiRef.addAndGet(100));
        aiRef.addAndGet(1);
    }
}
class MyThread60 extends Thread{
    private MyService60 myService;

    public MyThread60(MyService60 myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        myService.addNum();
    }
}