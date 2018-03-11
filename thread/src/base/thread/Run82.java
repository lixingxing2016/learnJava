package base.thread;

/*
 * @author zst
 * @data 2018-03-10-16:19
 * 方法join的作用是使所属的线程对象x正常执行run()方法中的任务，而使当前线程z进行无限期的阻塞，等待线程x
 * 销毁后再继续执行线程z后面的代码
 */
public class Run82 {
    public static void main(String[] args){
        try {
            MyThread82 thread = new MyThread82();
            thread.start();
            thread.join();//join()等待调用该方法的所属线程死亡
            System.out.println("我想在thread对象执行完毕后我再执行，我做到了");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread82 extends Thread{
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