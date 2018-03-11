package base.thread;
/*
    测试同步代码块synchronized(inner2)对inner2上锁后，其他线程只能以同步方式调用inner2中的静态同步方法，
    因为是同一个锁对象。
 */
public class Run53 {
    public static void main(String[] args){
        final Out53.Inner1 inner1 = new Out53.Inner1();
        final Out53.Inner2 inner2 = new Out53.Inner2();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                inner1.method1(inner2);
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                inner1.method2();
            }
        },"t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                inner2.method1();
            }
        },"t3");
        t1.start();
        t2.start();
        t3.start();
    }
}
class Out53{
    static class Inner1{
        public void method1(Inner2 inner2){
            String threadName = Thread.currentThread().getName();
            synchronized (inner2){
                System.out.println(threadName+" 进入Inner1类中的method1方法");
                for (int i=0;i<10;i++){
                    System.out.println("i="+i);
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                System.out.println(threadName+" 离开Inner1类中的method1方法");
            }
        }
        public synchronized void method2(){
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+" 进入Inner1类中method2方法");
            for(int j=0;j<10;j++){
                System.out.println("j="+j);
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }
    static class Inner2{
        public synchronized void method1(){
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+" 进入Inner2类中的method1方法");
            for (int k=0;k<10;k++){
                System.out.println("k="+k);
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println(threadName+" 离开Inner2类中的method1方法");
        }
    }
}
