package base.thread;

import java.io.*;

/*
    通过管道进行线程间通信：字符流
    此实验是两个线程中通过管道流进行字符数据的传输。
 */
public class Run79 {
    public static void main(String[] args){
        try {
            WriteData2 writeData1 = new WriteData2();
            ReadData2 readData1 = new ReadData2();

            PipedReader reader = new PipedReader();
            PipedWriter writer = new PipedWriter();
            writer.connect(reader);
            ThreadR2 threadR1 = new ThreadR2(readData1,reader);
            threadR1.start();
            Thread.sleep(2000);
            ThreadW2 threadW1 = new ThreadW2(writeData1,writer);
            threadW1.start();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class WriteData2{
    public void writeMethod(PipedWriter out){
        try {
            System.out.println("write:");
            for (int i=0;i<300;i++){
                String outData = ""+(i+1);
                out.write(outData);
                System.out.print(outData);
            }
            System.out.println();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
class ReadData2{
    public void readMethod(PipedReader input){
        try {
            System.out.println("read:");
            char[] charArray = new char[20];
            int readLength = input.read(charArray);
            while (readLength!=-1){
                String newData = new String(charArray,0,readLength);
                System.out.print(newData);
                readLength = input.read(charArray);
            }
            System.out.println();
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
class ThreadW2 extends Thread{
    private WriteData2 write;
    private PipedWriter out;

    public ThreadW2(WriteData2 write, PipedWriter out) {
        this.write = write;
        this.out = out;
    }

    @Override
    public void run() {
        write.writeMethod(out);
    }
}
class ThreadR2 extends Thread{
    private ReadData2 read;
    private PipedReader input;

    public ThreadR2(ReadData2 read, PipedReader input) {
        this.read = read;
        this.input = input;
    }

    @Override
    public void run() {
        read.readMethod(input);
    }
}
