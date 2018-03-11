package base.thread;

/*
 * @author zst
 * @data 2018-03-10-21:48
 * 在join()方法中，当前线程执行wait(long)方法后，当前线程的锁会被释放，那么其他线程就可以调用此线程中的同步方法了
 * 而Thread.sleep(long)方法却不释放锁
 * 由于线程A使用Thread.sleep(long)方法一直持有线程B的锁，时间达到6秒，所以线程C只有在线程A时间到达6秒后释放
 * 线程B的锁时才能调用线程B中的同步方法 synchronized public void bService()
 * 该实验说明了Thread.sleep(long)方法不释放锁
 */
public class Run85 {
    public static void main(String[] args){
        try {
            MyThread85_B b = new MyThread85_B();
            MyThread85_A a = new MyThread85_A(b);
            a.start();//启动a线程(线程b在线程a中启动)
            Thread.sleep(1000);//主线程休眠1秒后c线程启动
            MyThread85_C c = new MyThread85_C(b);
            c.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread85_A extends Thread{
    private MyThread85_B b;

    public MyThread85_A(MyThread85_B b) {
        this.b = b;
    }

    @Override
    public void run() {
        try{
            synchronized (b){//线程A运行与线程C运行调用的同步方法是同一个锁对象
                b.start();//启动B线程，此时b和a并行执行
                Thread.sleep(6000);//不释放锁
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread85_B extends Thread{
    @Override
    public void run() {
        try {
            System.out.println("b run begin timer="+System.currentTimeMillis());
            Thread.sleep(5000);//线程b休眠5秒
            System.out.println("b run end timer="+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //同步方法
    synchronized public void bService(){
        System.out.println("打印了bService timer="+System.currentTimeMillis());
    }
}
class MyThread85_C extends Thread{
    private MyThread85_B threadB;

    public MyThread85_C(MyThread85_B threadB) {
        this.threadB = threadB;
    }

    @Override
    public void run() {
        threadB.bService();//在线程C中调用线程B类对象的同步方法
    }
}