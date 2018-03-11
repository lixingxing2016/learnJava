package base.thread;


public class Run9 {
    public static void main(String[] args) throws InterruptedException {
        MyThread9 myThread9 = new MyThread9();
        System.out.println("begin=="+myThread9.isAlive());
        myThread9.start();
//        Thread.sleep(1000);因为mythread对象已经在1秒内执行完毕，此时end=false
        System.out.println("end=="+myThread9.isAlive());//end=true，但此值不确定的，打印true值是因为mythread还没有执行完毕，所以输出true
    }
}

class MyThread9 extends Thread{
    @Override
    public void run() {
        System.out.println("run="+this.isAlive());
    }
}