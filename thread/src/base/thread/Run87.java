package base.thread;

/*
 * @author zst
 * @data 2018-03-11-9:00
 * 方法join(2000)大部分先运行
 *
 */
public class Run87 {
    public static void main(String[] args){
        try{
            MyThread87_B b = new MyThread87_B();
            MyThread87_A a = new MyThread87_A(b);
            a.start();//启动a线程
            b.start();//启动b线程
            b.join(2000);//表示main线程会等待b线程执行2秒，2秒后，main线程和b线程之间的执行顺序由串行变为普通的并行
            System.out.println("main end "+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread87_A extends Thread{
    private MyThread87_B t;

    public MyThread87_A(MyThread87_B t) {
        this.t = t;
    }

    @Override
    public void run() {
        try {
            synchronized (t){//同步代码块 线程b作为锁对象
                System.out.println("begin A ThreadName="+Thread.currentThread().getName()+" "+System.currentTimeMillis());
                Thread.sleep(5000);//让当前线程休眠5秒，不释放锁对象
                System.out.println(" end A ThreadName="+Thread.currentThread().getName()+System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread87_B extends Thread{
    //同步方法，需要使用对象锁
    @Override
    synchronized public void run() {
        try {
            System.out.println("begin B ThreadName="+Thread.currentThread().getName()+" "+System.currentTimeMillis());
            Thread.sleep(5000);//让当前线程休眠5秒
            System.out.println(" end B ThreadName="+Thread.currentThread().getName()+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
