package base.thread;
/*
    验证同步synchronized(this)代码块与synchronized方法一样是锁定当前对象的

 */
public class Run42 {
    public static void main(String[] args)throws InterruptedException{
        MyTask42 task = new MyTask42();
        MyThread42_A threadA = new MyThread42_A(task);
        threadA.start();
        Thread.sleep(100);
        MyThread42_B threadB = new MyThread42_B(task);
        threadB.start();
    }
}

class MyTask42{
    //非同步方法
    /*public void otherMethod(){
        System.out.println("run--otherMethod");
    }*/
    synchronized public void otherMethod(){
        System.out.println("run--otherMethod");
    }
    //同步代码块
    public void doLongTimeTask(){
        synchronized (this){
            for (int i=0;i<10000;i++){
                System.out.println("synchronized threadName="+Thread.currentThread().getName()+" i="+(i+1));
            }
        }
    }
}
class MyThread42_A extends Thread{
    private MyTask42 task;

    public MyThread42_A(MyTask42 task) {
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        task.doLongTimeTask();
    }
}
class MyThread42_B extends Thread{
    private MyTask42 task;

    public MyThread42_B(MyTask42 task) {
        this.task = task;
    }

    @Override
    public void run() {
        super.run();
        task.otherMethod();
    }
}