package base.thread;
/*
    可重入锁支持在父子类继承的环境中
    下面实验表明，当存在父子类集成关系时，子类是完全可以通过可重入锁调用父类中的同步方法的。
 */
public class Run36 {
    public static void main(String[] args){
        MyThread36 thread = new MyThread36();
        thread.start();
    }
}
class MyThread36 extends Thread{
    @Override
    public void run() {
        Sub sub = new Sub();
        sub.operateISubMethod();
    }
}
class Main{
    public int i = 10;
    synchronized public void operateIMainMethod(){
        try {
            i--;
            System.out.println("main print i="+i);
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Sub extends Main{
    synchronized public void operateISubMethod(){
        try{
            while(i>0){
                i--;
                System.out.println("sub print i="+i);
                Thread.sleep(100);
                this.operateIMainMethod();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
