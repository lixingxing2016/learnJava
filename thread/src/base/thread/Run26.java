package base.thread;
/*
    在使用suspend与resume方法时，若使用不当，容易造成公共的同步对象的独占，使得其他线程
    无法访问公共同步对象。
 */
public class Run26 {
    public static void main(String[] args){
        try{
            final SynchronizedObject object = new SynchronizedObject();
            Thread thread1 = new Thread(){
                @Override
                public void run() {
                    object.printString();
                }
            };
            thread1.setName("a");
            thread1.start();
            Thread.sleep(1000);
            Thread thread2 = new Thread(){
                @Override
                public void run() {
                    System.out.println("thread2启动了，但进入不了printString()方法，只打印了一个begin");
                    System.out.println("因为printString90方法被a线程锁定并且永远suspend暂停了！");
                    object.printString();
                }
            };
            thread2.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class SynchronizedObject{
    synchronized public void printString(){
        System.out.println("begin");
        if(Thread.currentThread().getName().equals("a")){
            System.out.println("a线程永远suspend");
            Thread.currentThread().suspend();
        }
        System.out.println("end");
    }
}