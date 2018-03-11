package base.thread;
/*
    `将JVM设置为-server时出现死循环。在启动MyTread57.java线程时，变量private boolean isRunning=true；存在于
    公共堆栈以及线程私有栈中。在JVM被设置为-server模式时为了线程运行的效率，线程一直在私有栈中取得isRunning的
    值时true，而代码thread.setRunning(false)虽然被执行，更新的却是公共堆栈中的isRunning变量值false，所以
    一直就是死循环状态。
    这个问题其实是线程私有堆栈中的值和公共堆栈中的值不同步造成的。解决这样问题就要使用volatile关键字，其
    主要作用是当线程访问isRunning这个变量时，强制性从公共堆栈中取值。
    使用volatile关键字增加了实例变量在多个线程之间的可见性。但volatile关键字最致命的缺点是不支持原子性。
    synchronized和volatile比较：
    1）关键字volatile是线程同步的轻量级实现，所以volatile性能比synchronized好，并且volatile只能修饰变量，
    而synchronized可以修饰方法，以及代码块，随着JDK新版本发布，synchronized关键字在执行效率上得到了很大提升。
    2）多线程访问volatile不会发生阻塞，而synchronized会出现阻塞
    3）volatile能保证数据的可见性，但不能保证原子性，而synchronized可以保证原子性，也可以间接保证可见性，
    因为它会将线程私有堆栈和公共堆栈中的数据做同步操作。
    4）再次重申，关键字volatile解决的是变量在多个线程之间的可见性，而synchronized关键字解决的是多个线程之间
    访问资源的同步性。
    线程安全包含原子性和可见性两个方面，Java的同步机制都是围绕这两个方法来确保线程安全的。
 */
public class Run57 {
    public static void main(String[] args){
        try{
            MyThread57 thread = new MyThread57();
            thread.start();
            Thread.sleep(1000);
            thread.setRunning(false);
            System.out.println("已经赋值为false了");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class MyThread57 extends Thread{
    volatile private boolean isRunning = true;
    public boolean isRunning(){
        return isRunning;
    }
    public void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入run了");
        while (isRunning==true){

        }
        System.out.println("线程被停止了");
    }
}
