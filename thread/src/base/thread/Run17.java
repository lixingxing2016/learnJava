package base.thread;
/*
    interupted()方法测试当前线程是否已经是中断状态，执行后具有将状态标志清除为false的功能
    isInterrupted()方法测试线程Thread对象是否已经是中断状态，但不清除状态标志
 */
public class Run17 {
    public static void main(String[] args){
        try {
            MyThread17 thread = new MyThread17();
            thread.start();
            Thread.sleep(1000);
            thread.interrupt();
            System.out.println("是否停止1？=" + thread.isInterrupted());
            System.out.println("是否停止2？=" + thread.isInterrupted());
        }catch (InterruptedException e)   {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
class MyThread17 extends Thread{
    @Override
    public void run() {
        super.run();
        for (int i=0;i<300000;i++){
            System.out.println("i="+(i+1));
        }
    }
}