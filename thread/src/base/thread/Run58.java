package base.thread;
/*
    关键字volatile虽然增加了实例变量在多个线程之间的可见性，但它却不具备同步性，也就不具备原子性。
    下面程序说明了volatile的非同步性。

    关键字volatile主要的使用场合是在多个线程中可以感知实例变量被更改了，并且可以获取到最新的值使用，
    也就是用多线程读取共享变量时可以获得最新值使用。
    关键字volatile提示线程每次从共享内存中读取变量，而不是从本线程的堆栈中读取，这样就保证的同步数据的
    可见性。但仍要注意：如果修改实例变量中的数据，比如i++，其实这样的操作并不是一个原子操作，也就是非
    线程安全的。
    表达式i++的操作步骤分解如下：
    1）从内存中读取出i的值
    2）计算i=i+1的值
    3）将i的值写入内存中。
    加入在第二步中计算值的时候，另外一个线程也修改了i值，那么这个时候会出现脏数据。解决办法是使用
    synchronized关键字。
     volatile变量在内存中工作有如下6个阶段：
     1）read和load阶段：从主存复制变量到当前线程
     2）use和assign阶段：执行代码，改变共享变量值
     3）store和write阶段：用工作内存数据刷新主存对应变量的值
     在多线程环境中，use和assign是多次出现的，但这一操作并不是原子性，也就是在read和load之后，如果
     主内存Count变量发生修改之后，线程工作内存中的值由于已经加载，不会产生对应的变化，也就是线程的
     私有内存和公共内存中的变量不同步，所以计算出来的结果和预期的不一致，也就出现了非线程安全问题。
 */

public class Run58 {
    public static void main(String[] args){
        MyThread58[] threadArray = new MyThread58[100];
        for(int i=0;i<100;i++){
            threadArray[i] = new MyThread58();
        }
        for (int i=0;i<100;i++){
            threadArray[i].start();
        }
    }
}
class MyThread58 extends Thread{
    volatile public static int count;
    //非同步方法
   /* private static void addCount(){
        for (int i=0;i<100;i++){
            count++;
        }
        System.out.println("count="+count);
    }*/
   //同步方法(因为是static，所以synchronized对应的锁时MyThread58.class这个类，新创建的100个线程对应同一个锁)
    synchronized private static void addCount(){
        for (int i=0;i<100;i++){
            count++;
        }
        System.out.println("count="+count);
    }

    @Override
    public void run() {
        addCount();
    }
}
