package base.thread;
/*
    非线程安全会在多个线程对同一个对象中的实例变量进行并发访问时发生，产生的后果是“脏读”，也就是取到的数据
    起始是被修改过的，而线程安全就是以获得的实例变量的值是经过同步处理的，不会出现脏读现象。
    非线程安全问题存在于实例变量中，如果是方法内部的局部变量，则不存在非线程安全问题。
 */

public class Run30 {
    public static void main(String[] args){
        HasSelfPrivateNum numRef = new HasSelfPrivateNum();
        ThreadA threadA = new ThreadA(numRef);
        threadA.start();
        ThreadB threadB = new ThreadB(numRef);
        threadB.start();
    }

}
class HasSelfPrivateNum{
    private int num = 0;
    //非同步方法
    /*public void addI(String username){
        try{
            if(username.equals("a")){
                num = 100;
                System.out.println("a set over");
                Thread.sleep(2000);
            }else{
                num = 200;
                System.out.println("b set over");
            }
            System.out.println(username + " num = "+num);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }*/
    //同步方法
    synchronized public void addI(String username){
        try{
            if(username.equals("a")){
                num = 100;
                System.out.println("a set over");
                Thread.sleep(2000);
            }else{
                num = 200;
                System.out.println("b set over");
            }
            System.out.println(username + " num = "+num);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class ThreadA extends Thread{
    private HasSelfPrivateNum numRef;

    public ThreadA(HasSelfPrivateNum numRef) {
         super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super.run();
        numRef.addI("a");
    }
}
class ThreadB extends Thread{
    private HasSelfPrivateNum numRef;

    public ThreadB(HasSelfPrivateNum numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super.run();
        numRef.addI("b");
    }
}