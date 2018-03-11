package base.thread;
/*
    使用stop()方法停止线程是非常暴力的，会抛出java.lang.ThreadDeath异常，但在通常情况下，此异常不需要显式捕获，
    使用stop()方法释放锁会给数据造成不一致性的结果。如果出现这样的情况，程序处理数据就有可能遭到破坏，最终导致
    程序执行的流程错误。
 */
public class Run23 {
    public static void main(String[] args){
        try{
            MyThread23 thread = new MyThread23();
            thread.start();
            Thread.sleep(8000);
            thread.stop();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread23 extends Thread{
    private int i=0;
    @Override
    public void run() {
        super.run();
        try {
            while(true){
                i++;
                System.out.println("i="+i);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}