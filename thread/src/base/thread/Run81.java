package base.thread;

/*
 * @author zst
 * @data 2018-03-10-16:04
 * 在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，主线程往往将
 * 早于子线程结束之前结束。这时，如果主线程想等待子线程执行完毕之后再结束，比如子线程
 * 处理一个数据，主线程要取得这个数据中的值，就要用到join()方法了。方法join()的作用就是等待
 * 线程对象销毁
 */
public class Run81 {
    public static void main(String[] args){
        MyThread81 thread = new MyThread81();
        thread.start();
        //Thead.sleep(?);//不知道休眠多久
        System.out.println("我想在thread对象执行完毕后再执行");
        System.out.println("但上面代码中sleep多久不确定");

    }
}
class MyThread81 extends Thread{
    @Override
    public void run() {
        try {
            int secondValue = (int)(Math.random()*10000);
            System.out.println(secondValue);
            Thread.sleep(secondValue);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
