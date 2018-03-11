package base.thread;
/*
    同步不可继承
 */
public class Run38 {
    public static void main(String[] args) {
        Sub38 subRef = new Sub38();
        MyThread38_A a = new MyThread38_A(subRef);
        a.setName("A");
        a.start();
        MyThread38_B b = new MyThread38_B(subRef);
        b.setName("B");
        b.start();
    }
}
class Main38{
    //父类是同步方法
    synchronized public void serviceMethod(){
        try{
            System.out.println("int main下一步sleep begin threadName="+Thread.currentThread().getName()+ " time="+System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("int main下一步sleep end threadName="+Thread.currentThread().getName()+ " time="+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Sub38 extends Main38{
    //子类是非同步方法
   /* @Override
    public  void serviceMethod() {
        try {
            System.out.println("int sub 下一步sleep begin threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("int sub下一个sleep end threadName="+Thread.currentThread().getName()+" time="+System.currentTimeMillis());
            super.serviceMethod();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }*/
   //子类同步方法
   @Override
    synchronized public  void serviceMethod() {
        try {
            System.out.println("int sub 下一步sleep begin threadName=" + Thread.currentThread().getName() + " time=" + System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("int sub下一个sleep end threadName="+Thread.currentThread().getName()+" time="+System.currentTimeMillis());
            super.serviceMethod();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class MyThread38_A extends Thread{
    private Sub38 sub;

    public MyThread38_A(Sub38 sub) {
        this.sub = sub;
    }

    @Override
    public void run() {
        sub.serviceMethod();
    }
}
class MyThread38_B extends Thread{
    private Sub38 sub;

    public MyThread38_B(Sub38 sub) {
        this.sub = sub;
    }

    @Override
    public void run() {
        sub.serviceMethod();
    }
}