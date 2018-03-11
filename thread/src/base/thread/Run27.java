package base.thread;
/*
     yield()方法的作用是放弃当前CPU资源，将它让给其他任务去占用CPU执行时间，但放弃的时间不确定，有可能刚刚放弃又
     马上获得CPU时间片
 */
public class Run27 {
    public static void main(String[] args){
        MyThread27 thread = new MyThread27();
        thread.start();
    }
}
class MyThread27 extends Thread{
    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<5000000;i++){
            Thread.yield();
            count = count + (i+1);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("用时："+(endTime-beginTime)+"毫秒");
    }
}