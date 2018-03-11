package base.thread;
/*
    如果通知过早，则会打乱程序的正常运行逻辑
    如果先通知了，则wait方法也就没有必要执行了
 */
public class Run71 {
    private String lock = new String("");
    private boolean isFirstRunB = false;
    private Runnable runnableA = new Runnable() {
        @Override
        public void run() {
            try {
                synchronized (lock){
                    while (isFirstRunB==false){
                        System.out.println("begin wait");
                        lock.wait();
                        System.out.println("end wait");
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };
    private Runnable runnableB = new Runnable() {
        @Override
        public void run() {
            synchronized (lock){
                System.out.println("begin notify");
                lock.notify();
                System.out.println("end notify");
                isFirstRunB = true;
            }
        }
    };
    public static void main(String[] args) throws InterruptedException{
        /*Run71 run = new Run71();
        Thread b = new Thread(run.runnableB);
        b.start();
        Thread.sleep(100);
        Thread a = new Thread(run.runnableA);
        a.start();*/
        Run71 run = new Run71();
        Thread a = new Thread(run.runnableA);
        a.start();
        Thread.sleep(100);
        Thread b = new Thread(run.runnableB);
        b.start();

    }

}

