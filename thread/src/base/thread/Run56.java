package base.thread;
/*
    下面的代码的格式运行在server服务器模式中64bit的JVM上时，会出现死循环，解决的办法是使用volatile关键字
    关键字volatile作用是强制从公共堆栈中取得变量的值，而不是从线程私有数据栈中取得变量的值。
 */
public class Run56 {
    public static void main(String[] args){
        PrintString56 printString = new PrintString56();
        new Thread(printString).start();
        System.out.println("我要停止这个线程stopThread="+Thread.currentThread().getName());
        printString.setContinuePrint(false);
    }
}
class PrintString56 implements Runnable{
    private boolean isContinuePrint = true;
    public boolean isContinuePrint(){
        return isContinuePrint;
    }
    public void setContinuePrint(boolean isContinuePrint){
        this.isContinuePrint = isContinuePrint;
    }
    public void printStringMethod(){
        try {
            while(isContinuePrint==true){
                System.out.println("run printStringMethod threadName="+Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        printStringMethod();
    }
}