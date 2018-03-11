package base.thread;
/*
    关键字volatile的主要作用是使变量在多个线程间可见
    下面程序停不下来的原因是main线程一直在处理while()循环，导致程序不能执行后面的代码，
    解决的办法是使用多线程技术
 */
public class Run55 {
    public static void main(String[] args){
        MyPrintString printString = new MyPrintString();
        printString.printStringMethod();
        System.out.println("我要停止这个线程stopThread="+Thread.currentThread().getName());
        printString.setContinuePrint(false);
    }
}
class MyPrintString{
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
}