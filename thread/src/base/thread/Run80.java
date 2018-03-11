package base.thread;
/*

 */
public class Run80 {
    public static void main(String[] args){
        DBTools dbTools = new DBTools();
        for(int i=0;i<20;i++){
            BackupB output = new BackupB(dbTools);
            output.start();
            BackupA input = new BackupA(dbTools);
            input.start();

        }
    }
}
class DBTools{
    volatile private boolean prevIsA = false;
    synchronized public void backupA(){
        try {
            while (prevIsA==true){
                wait();
            }
            for (int i=0;i<5;i++){
                System.out.println("AAAAA");
            }
            prevIsA = true;
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    synchronized public void backupB(){
        try {
            while (prevIsA==false){
                wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("BBBBB");
            }
            prevIsA = false;
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class BackupA extends Thread{
    private DBTools dbTools;

    public BackupA(DBTools dbTools) {
        this.dbTools = dbTools;
    }

    @Override
    public void run() {
        dbTools.backupA();
    }
}
class BackupB extends Thread{
    private DBTools dbTools;

    public BackupB(DBTools dbTools) {
        this.dbTools = dbTools;
    }

    @Override
    public void run() {
        dbTools.backupB();
    }
}