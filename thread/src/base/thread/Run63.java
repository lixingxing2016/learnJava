package base.thread;
/*
    方法wait()作用是使当前执行代码的线程进行等待，wait()是Object类的方法，该方法
    用来将当前线程置入"预执行队列"中，并且在wait()所在的代码行处停止执行，直到
    接到通知或被中断为止。在调用wait()方法之前，线程必须获得该对象的对象级别锁，
    即只能在同步方法或同步块中调用wait()方法。在执行wait方法后，当前线程释放锁。
    在从wait()返回前，线程与其他线程竞争重新获得锁。如果调用wait()时没有持有
    适当的锁，则抛出IllegalMonitorStateException，它是RuntimeException的一个子类，
    因此，不需要try-catch语句进行捕获。
    方法notify()也要在同步方法或同步块中调用，即在调用之前，线程也必须获得该对象的
    对象级别锁。如果调用notify()时没有持有适当的锁，也会抛出IllegalMonitorStateException，
    该方法用来通知那些可能等待该对象的对象锁的其他线程，如果有多个线程等待，则由
    线程规划器随机挑选出其中一个呈现wait状态的线程，对其发出通知notify，并使它等待
    获取该对象的对象锁。需要说明的是，在执行notify()方法后，当前线程不会马上释放
    该对象锁，成wait状态的线程也并不能马上获取该对象锁，要等到执行notify()方法的
    线程将程序执行完，也就是退出synchronized代码块之后，当前线程才会释放锁，而呈
    wait状态所在的线程才可以获取该对象锁。当第一个获得了改对象锁的wait线程运行
    完毕之后，它会释放掉该对象锁，此时如果该对象没有再次使用notify语句，则即使
    该对象已经空闲，其他wait状态等待的线程由于没有得到该对象的通知，还会继续阻塞在wait
    状态，直到这个对象发出一个notify或notifyAll.
    总结一下：wait使线程停止运行，而notify使停止的线程继续运行
 */
public class Run63 {
    public static void main(String[] args){
        try{
            String lock = new String("");
            System.out.println("syn上面");
            synchronized (lock){
                System.out.println("syn第一行");
                lock.wait();//线程一直在等待，不继续向下运行了，需要使用notify()方法使程序继续向下运行
                System.out.println("wait下面的代码");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
//没有对象监视器，也就是没有同步加锁 Exception in thread "main" java.lang.IllegalMonitorStateException
/*public class Run63 {
    public static void main(String[] args){
        try{
            String newString = new String("");
            newString.wait();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}*/
