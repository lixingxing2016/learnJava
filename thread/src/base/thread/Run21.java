package base.thread;
/*
    如果在sleep状态下停止某个线程，会进入catch语句，并且清除停止状态值，使之成为false
 */
public class Run21 {
    public static void main(String[] args){
        try {
            MyThread21 thread = new MyThread21();
            thread.start();
            Thread.sleep(200);
            thread.interrupt();
        }catch (InterruptedException e){
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end");
    }
}

class MyThread21 extends Thread{
    @Override
    public void run() {
        super.run();
        try {
           System.out.println("run begin");
           Thread.sleep(200000);
        }catch (InterruptedException e){
            System.out.println("在沉睡中被停止，进入catch "+this.isInterrupted());
            e.printStackTrace();
        }
    }
}
