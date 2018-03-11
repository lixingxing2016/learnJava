package base.thread;
/*
    将方法interrupt()与return结合使用也能实现停止线程的效果
    不过仍建议使用抛异常的方式来实现线程的停止，因为在catch块中可以将异常
    上抛，使线程停止的事件得以传播。
 */
public class Run24 {
    public static void main(String[] args) throws InterruptedException{
        MyThread24 thread = new MyThread24();
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
class MyThread24 extends Thread{
    private int i=0;
    @Override
    public void run() {
      while(true){
          if(this.isInterrupted()){
              System.out.println("停止了");
              return;
          }
          System.out.println("timer="+System.currentTimeMillis());
      }
    }
}