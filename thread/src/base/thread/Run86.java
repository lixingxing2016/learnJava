package base.thread;

/*
 * @author zst
 * @data 2018-03-10-22:19
 * 由于线程A释放了线程B的对象锁，所以线程C可以调用线程B的同步方法synchronized public void bService()
 * 再次说明join(long)方法具有释放锁的特点
 *
 */
public class Run86 {
    public static void main(String[] args){
        try {
            MyThread86_B b = new MyThread86_B();
            MyThread86_A a = new MyThread86_A(b);
            a.start();//启动a线程(线程b在线程a中启动)
            Thread.sleep(1000);//主线程休眠1秒后c线程启动
            MyThread86_C c = new MyThread86_C(b);
            c.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread86_A extends Thread{
    private MyThread86_B b;

    public MyThread86_A(MyThread86_B b) {
        this.b = b;
    }

    @Override
    public void run() {
        try{
            synchronized (b){//线程A运行与线程C运行调用的同步方法是同一个锁对象
                b.start();//启动B线程，此时b和a并行执行
                //Thread.sleep(6000);//不释放锁
                b.join();//当前线程a一直等待线程b执行完毕后再执行，等待中释放了锁
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread86_B extends Thread{
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
class MyThread86_C extends Thread{
    private MyThread86_B threadB;

    public MyThread86_C(MyThread86_B threadB) {
        this.threadB = threadB;
    }

    @Override
    public void run() {
        threadB.bService();//在线程C中调用线程B类对象的同步方法
    }
}