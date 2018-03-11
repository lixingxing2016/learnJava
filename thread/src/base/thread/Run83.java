package base.thread;

/*
 * @author zst
 * @data 2018-03-10-20:09
 * 在join过程中，如果当前线程对象被中断，则当前线程出现异常
 * 方法join()和interrupt()方法如果彼此遇到，则会出现异常。
 * 但线程按钮还呈红色，原因是线程ThreadA还在继续运行，线程ThreadA并未出现异常，是
 * 正常执行的状态。
 *
 */
public class Run83 {
    public static void main(String[] args){
        try {
            MyThread83_B b = new MyThread83_B();
            b.start();//启动b线程
            Thread.sleep(500);//让主线程休眠500ms
            MyThread83_C c = new MyThread83_C(b);
            c.start();//启动c线程

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread83_A extends Thread{
    @Override
    public void run() {
        for (int i=0;i<Integer.MAX_VALUE;i++){
            String newString = new String();
            Math.random();
        }
    }
}
class MyThread83_B extends Thread{
    @Override
    public void run() {
       try {
           MyThread83_A a = new MyThread83_A();
           a.start();//启动A线程
           a.join();//使所属线程对象a正常执行run()方法中的任务，而使当前线程b进入无限等待
           System.out.println("线程B在run end处打印了");
       }catch (InterruptedException e){
           System.out.println("线程B在catch处打印了");
           e.printStackTrace();
       }
    }
}
class MyThread83_C extends Thread{
    private MyThread83_B threadB;

    public MyThread83_C(MyThread83_B threadB) {
        this.threadB = threadB;
    }

    @Override
    public void run() {
        threadB.interrupt();//中断当前线程
    }
}