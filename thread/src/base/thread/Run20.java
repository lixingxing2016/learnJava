package base.thread;
/*
    通过抛出异常来终止线程
 */
public class Run20 {
    public static void main(String[] args){
        try {
            MyThread20 thread = new MyThread20();
            thread.start();
            Thread.sleep(1000);
            thread.interrupt();
        }catch (InterruptedException e){
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end!");
    }
}
class MyThread20 extends Thread{
    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < 300000; i++) {
                if (this.interrupted()) {
                    System.out.println("已经是停止状态了，我要退出了");
                    throw new InterruptedException();
                }
                System.out.println("i=" + (i + 1));
            }
            System.out.println("for循环下面");
        }catch (InterruptedException e){
            System.out.println("进MyThread20.java类run方法中的catch了");
            e.printStackTrace();
        }
    }
}