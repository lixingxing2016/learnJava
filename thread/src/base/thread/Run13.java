package base.thread;
/*
    getId()用于获取线程的唯一标识
 */
public class Run13 {
    public static void main(String[] args){
        Thread runThread = Thread.currentThread();
        System.out.println(runThread.getName()+" "+runThread.getId());
    }
}
