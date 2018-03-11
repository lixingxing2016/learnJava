package base.thread;
/*
    suspend()暂停线程
    resume()恢复线程
 */
public class Run25 {
    public static void main(String[] args){
        try {
            MyThread25 thread = new MyThread25();
            thread.start();
            Thread.sleep(5000);
            //A部分
            thread.suspend();
            System.out.println("A= "+ System.currentTimeMillis()+" i="+thread.getI());
            Thread.sleep(5000);
            System.out.println("A= "+ System.currentTimeMillis()+" i="+thread.getI());
            //B部分
            thread.resume();
            Thread.sleep(5000);
            //C部分
            thread.suspend();
            System.out.println("B= "+System.currentTimeMillis()+ " i="+thread.getI());
            Thread.sleep(5000);
            System.out.println("B= "+System.currentTimeMillis()+" i="+thread.getI());
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
class MyThread25 extends Thread{
    private long i=0;

    public long getI() {
        return i;
    }

    public void setI(long i) {
        this.i = i;
    }

    @Override
    public void run() {
        while (true){
            i++;
        }
    }
}