package base.thread;

public class Run19 {
    public static void main(String[] args){
        try {
            MyThread19 thread = new MyThread19();
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
class MyThread19 extends Thread{
    @Override
    public void run() {
        super.run();
        for (int i=0;i<300000;i++){
            if(this.interrupted()){
                System.out.println("已经是停止状态了，我要退出了");
                break;
            }
            System.out.println("i="+(i+1));
        }
        System.out.println("这里仍然可以输出，线程并未停止");
    }
}