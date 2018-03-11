package base.thread;
/*
    当一个线程访问object的一个synchronized同步代码块时，另一个线程仍然可以访问该object对象中的
    非synchronized(this)同步代码块。

 */
public class Run40 {
    public static void main(String[] args){
        MyTask task = new MyTask();
        MyThread40_A threadA = new MyThread40_A(task);
        threadA.start();
        MyThread40_B threadB = new MyThread40_B(task);
        threadB.start();
    }
}
class MyTask{
    public void doLongTimeTask(){
        for (int i=0;i<1000;i++){
            System.out.println("nosynchronized threadName="+Thread.currentThread().getName()+" i="+(i+1));
        }
        System.out.println("");
        synchronized (this){
            for (int i=0;i<100;i++){
                System.out.println("synchronized threadName="+Thread.currentThread().getName()+" i="+(i+1));
            }
        }
    }
}
class MyThread40_A extends Thread{
    private MyTask task;

    public MyThread40_A(MyTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        task.doLongTimeTask();
    }
}
class MyThread40_B extends Thread{
    private MyTask task;

    public MyThread40_B(MyTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        task.doLongTimeTask();
    }
}