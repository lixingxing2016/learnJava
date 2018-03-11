package base.thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/*
    在Java语言中提供了各种各样的输入/输出流Stream，使我们能够方法的对数据进行操作，其中
    管道流(pipeStream)是一种特殊的流，用于在不同线程间直接传送数据。一个线程发送数据
    到输出管道，另一个线程从输入管道中读数据。通过使用管道，实现不同线程间的通信。
    在Java的JDK中提供了4个类来使线程间可以通信：
    1）PipedInputStream和PipedOutputStream
    2)PipedReader和PipedWriter

    使用代码inputStream.connect(outputStream)或者outputStream.connect(inputStream)的作用
    是使两个Stream之间产生通信连接，这样才可以将数据进行输入与输出。
    从结果来看，两个线程通过管道流成功进行数据的传输
    但在此实验中，首先是读取线程启动，由于当时没有数据被写入，所以线程阻塞在int readLength=in.read(byteArray)
    代码中，直到有数据被写入，才继续向下运行
 */
public class Run78 {
    public static void main(String[] args){
        try {
            WriteData1 writeData1 = new WriteData1();
            ReadData1 readData1 = new ReadData1();

            PipedInputStream inputStream = new PipedInputStream();
            PipedOutputStream outputStream = new PipedOutputStream();
            outputStream.connect(inputStream);
            ThreadR1 threadR1 = new ThreadR1(readData1,inputStream);
            threadR1.start();
            Thread.sleep(2000);
            ThreadW1 threadW1 = new ThreadW1(writeData1,outputStream);
            threadW1.start();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class WriteData1{
    public void writeMethod(PipedOutputStream out){
        try {
            System.out.println("write:");
            for (int i=0;i<300;i++){
                String outData = ""+(i+1);
                out.write(outData.getBytes());
                System.out.print(outData);
            }
            System.out.println();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
class ReadData1{
    public void readMethod(PipedInputStream input){
        try {
            System.out.println("read:");
            byte[] byteArray = new byte[20];
            int readLength = input.read(byteArray);
            while (readLength!=-1){
                String newData = new String(byteArray,0,readLength);
                System.out.print(newData);
                readLength = input.read(byteArray);
            }
            System.out.println();
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
class ThreadW1 extends Thread{
    private WriteData1 write;
    private PipedOutputStream out;

    public ThreadW1(WriteData1 write, PipedOutputStream out) {
        this.write = write;
        this.out = out;
    }

    @Override
    public void run() {
        write.writeMethod(out);
    }
}
class ThreadR1 extends Thread{
    private ReadData1 read;
    private PipedInputStream input;

    public ThreadR1(ReadData1 read, PipedInputStream input) {
        this.read = read;
        this.input = input;
    }

    @Override
    public void run() {
        read.readMethod(input);
    }
}