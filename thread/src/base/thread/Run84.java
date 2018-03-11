package base.thread;

/*
 * @author zst
 * @data 2018-03-10-21:26
 *  方法join(long)中的参数是设定等待的时间
 *  方法join(long)的功能在内部是使用wait(long)方法来实现的，所以join(long)方法具有释放锁的特点
 *  而Thread.sleep(long)方法却不释放锁。
 */
public class Run84 {
    public static void main(String[] args){
        try{
            MyThread84 t1 = new MyThread84();
            t1.start();
            //join(2000)；表示主线程暂停2秒期间由t1线程执行（串行），两秒后并行执行。
            t1.join(2000);//当前main线程中调用t1线程的join方法，则main线程放弃CPU控制权，并返回
            //t1线程继续执行直到线程t1执行完毕，所以结果是t1线程执行完后，才到主线程执行，相当于在main线程
            //中同步t1线程，t1执行完了，main线程才有执行的机会
            //所以总结：Thread类中的join方法的主要作用就是同步，可以使得线程之间的并行执行变成串行执行
            System.out.println(" end timer ="+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread84 extends Thread{
    @Override
    public void run() {
        try{
            System.out.println("begin timer="+System.currentTimeMillis());
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
