package base.thread;
/*
    方法sleep()作用是在指定的毫秒数内让当前正在执行的线程休眠（暂停执行），这个正在执行的线程是指this.currentThread()线程
 */
public class Run11 {
    public static void main(String[] args){
        MyThread11 myThread11 = new MyThread11();
        System.out.println("begin="+System.currentTimeMillis());
        myThread11.run();
        System.out.println("end="+System.currentTimeMillis());
    }
}
class MyThread11 extends Thread{
    @Override
    public void run() {
        try {
            System.out.println("run threadName=" + this.currentThread().getName() + " begin");
            Thread.sleep(2000);//休眠2秒钟
            System.out.println("run threadName=" + this.currentThread().getName() + " end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
