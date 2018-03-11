package base.thread;
/*
    脏读，在读取实例变量时，此值已经被其他线程更改过。
    出现脏读是因为public void getValue()方法不是同步的，所以可以在任意时候进行调用，
    解决办法是加上同步synchronized关键字

    当A线程调用anyObject对象加入synchronized关键字的X方法时，A线程获得了对象的锁，所以其他
    线程必须等待A线程执行完毕之后才可以调用X方法，但B线程可以随意调用其他非synchronized同步方法

    当A线程调用anyObject对象加入synchronized关键字的X方法时，A线程就获得了X方法所在对象的锁，所以其他
    线程必须等A线程执行完X方法才可以调用X方法，而B线程如果调用声明了synchronized关键字的非X方法时，必须
    等A线程将X方法执行完，也就是释放对象锁之后才可以调用，这时A线程已经执行了一个完整任务。
 */
public class Run34 {
    public static void main(String[] args){
        try{
            PublicVar publicVar = new PublicVar();
            Thread34 thread = new Thread34(publicVar);
            thread.start();
            Thread.sleep(200);//打印结果受此值大小影响
            publicVar.getValue();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class PublicVar{
    public String username = "A";
    public String password = "AA";
    synchronized public void setValue(String username,String password){
        try {
            this.username = username;
            Thread.sleep(5000);
            this.password = password;
            System.out.println("setValue method thread name = "+Thread.currentThread().getName()+" username="+username+ " password="+password);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //非同步方法，会出现脏读情况
   /* public void getValue(){
        System.out.println("getValue method thread name="+Thread.currentThread().getName()+" username="+username+" password="+password);
    }*/
   //同步方法，解决脏读问题
    synchronized public void getValue(){
        System.out.println("getValue method thread name="+Thread.currentThread().getName()+" username="+username+" password="+password);
    }
}
class Thread34 extends Thread{
    private PublicVar publicVar;

    public Thread34(PublicVar publicVar) {
        this.publicVar = publicVar;
    }

    @Override
    public void run() {
        super.run();
        publicVar.setValue("B","BB");
    }
}