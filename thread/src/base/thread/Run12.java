package base.thread;

public class Run12 {
    public static void main(String[] args){
        MyThread12 myThread11 = new MyThread12();
        System.out.println("begin="+System.currentTimeMillis());
        myThread11.start();
        System.out.println("end="+System.currentTimeMillis());
    }
}
class MyThread12 extends Thread{
    @Override
    public void run() {
        try {
            System.out.println("run threadName=" + this.currentThread().getName() + " begin="+System.currentTimeMillis());
            Thread.sleep(2000);//休眠2秒钟
            System.out.println("run threadName=" + this.currentThread().getName() + " end="+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
