package base.thread;

import java.util.Random;

/*
    在Java中，线程的优先级具有继承性，比如A线程启动B线程，则B线程的优先级与A是一样的
    可以使用setPriority()设置线程的优先级
    高优先级的线程总是大部分先执行完，但不代表高优先级的线程全部先执行完。
 */
public class Run28 {
    public static void main(String[] args){
        for (int i=0;i<5;i++){
            MyThread28 t1 = new MyThread28();
            t1.setPriority(10);
            t1.start();
            MyThread28_1 t2 = new MyThread28_1();
            t2.setPriority(1);
            t2.start();
        }
    }
}
class MyThread28 extends Thread{
    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        long addResult = 0;
        for(int j=0;j<10;j++){
            for(int i=0;i<50000;i++){
                Random random = new Random();
                random.nextInt();
                addResult = addResult + i;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("thread1用时："+(endTime-beginTime)+"毫秒");
    }
}
class MyThread28_1 extends Thread{
    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        long addResult = 0;
        for(int j=0;j<10;j++){
            for(int i=0;i<50000;i++){
                Random random = new Random();
                random.nextInt();
                addResult = addResult + i;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("thread2用时："+(endTime-beginTime)+"毫秒");
    }
}
