package base.thread;
/*
    在内部类有有两个同步方法，但使用的却是不同的锁，打印的结果是异步的
 */
public class Run52 {
    public static void main(String[] args){
        final OutClass.Inner inner= new OutClass.Inner();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                inner.method1();
            }
        },"A");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                inner.method2();
            }
        },"B");
        t1.start();
        t2.start();
    }
}
class OutClass{
    static class Inner{
        public void method1(){
            synchronized (Inner.class){
                for(int i=1;i<=10;i++){
                    System.out.println(Thread.currentThread().getName()+"i="+i);
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        public synchronized void method2(){
            for(int i=11;i<=20;i++){
                System.out.println(Thread.currentThread().getName()+"i="+i);
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

}