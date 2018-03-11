package base.thread;
//Ctrl+y删除光标所在行
/*
    方法isAlive()用于测试线程是否处于活动状态，活动状态是线程已经启动尚未终止，
 */
public class Run10 {
    public static void main(String[] args){
        CountOperate1 countOperate1 = new CountOperate1();
        Thread t1 = new Thread(countOperate1);//将countOperate1赋值给target（即将要运行的目标）
        System.out.println("main begin t1 isAlive="+t1.isAlive());
        t1.setName("A");
        t1.start();
        System.out.println("main end t1 isAlive="+t1.isAlive());
    }
}
class CountOperate1 extends Thread{
    public CountOperate1(){
        System.out.println("CountOperate1--begin");
        System.out.println("Thread.currentThread().getName()="+Thread.currentThread().getName());
        System.out.println("Thread.currentThread().isAlive()="+Thread.currentThread().isAlive());
        System.out.println("this.getName()="+this.getName());
        System.out.println("this.isAlive()="+this.isAlive());//仅仅运行构造方法，还未运行线程，this代表CountOperate1实例，故this.isAlive()为false
        System.out.println("CountOperate1--end");
    }
    //在run()方法被执行的时候，this.getName()实际上返回的是target.getName()，而Thread.currentThread().getName()实际上是t1.getName()
    @Override
    public void run() {
        System.out.println("run--begin");
        System.out.println("Thread.currentThread().getName()="+Thread.currentThread().getName());
        System.out.println("Thread.currentThread().isAlive()="+Thread.currentThread().isAlive());
        System.out.println("this.getName()="+this.getName());
        System.out.println("this.isAlive()="+this.isAlive());//this指向的还是new CountOperate1()创建的那个线程实例，
        // 为false表示new CountOperate1()创建的线程实例处于尚未启动状态
        // 而不是new Thread(countOperate1)创建的那个实例t1

        System.out.println("run--end");
    }
}
