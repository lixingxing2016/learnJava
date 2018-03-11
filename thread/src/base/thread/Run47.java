package base.thread;
/*
    同步synchronized(class)代码块的作用其实和synchronized static方法的作用一样
 */
public class Run47 {
    public static void main(String[] args){
        Service47 service1 = new Service47();
        Service47 service2 = new Service47();
        Thread47_A threadA = new Thread47_A(service1);
        threadA.setName("A");
        threadA.start();
        Thread47_B threadB = new  Thread47_B(service2);
        threadB.setName("B");
        threadB.start();
    }
}
class Service47 {
    public static void printA() {
        synchronized (Service47.class) {
            try {
                System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入printA");
                Thread.sleep(3000);
                System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开printA");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printB() {
        synchronized (Service47.class) {
            System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入printB");
            System.out.println("线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开printB");
        }
    }
}
class Thread47_A extends Thread{
    private Service47 service;

    public Thread47_A(Service47 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.printA();
    }
}
class Thread47_B extends Thread{
    private Service47 service;

    public Thread47_B(Service47 service) {
        this.service = service;
    }
    @Override
    public void run() {
        service.printB();
    }
}