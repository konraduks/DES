/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Konrad
 */
public class IOBinary /*implements Runnable */ {

    DataInputStream read;
    DataOutputStream write;
    //ObjectInputStream read;
    //ObjectOutputStream write;
    //DESEngine des;
    DESQueue dq;
    int flag;

    public IOBinary() {

    }

    public void openStreams(String pathRead, String pathWrite, String key) throws FileNotFoundException, IOException {
        dq = new DESQueue();
        dq.Stream(key);
        //des = new DESEngine(key);
        read = new DataInputStream(new FileInputStream(pathRead));
        write = new DataOutputStream(new FileOutputStream(pathWrite));
        //read = new ObjectInputStream(new FileInputStream(pathRead));
        //write = new ObjectOutputStream(new FileOutputStream(pathWrite));
    }

    public void ReadEncode() throws IOException {
        byte[] temp = new byte[8];
        while (read.available() > 7) {
            //System.out.println(read.available());
            read.read(temp);            
            //Write(des.EncodeBytetoByte(temp));
            Write(dq.EncodeFromFile(temp));
            //Write(temp);            
        }
        temp = new byte[read.available()];
        if (temp.length > 0) {
            read.read(temp);            
            Write(dq.EncodeFromFileLastBlock(temp));
        }
        closeStreams();
        //flag = 1;
        //run();

    }

    public void ReadDecode() throws IOException {
        byte[] temp = new byte[16];
        while (read.available() > 16) {            
            read.read(temp);
            //Write(temp);
            Write(dq.DecodeFromFile(temp));
        }        
        read.read(temp);
        Write(dq.DecodeFromFileLastBlock(temp));
        closeStreams();
        /*flag = 2;
         run();*/
    }

    private void Write(byte[] msg/*, int offset*/) throws IOException {
        //write.write(msg, offset*8, 8);        
        write.write(msg);
    }

    private void closeStreams() throws IOException {
        read.close();
        write.close();
    }

    /*@Override
     public void run() {
     //System.out.println("nowy watek");
     try {
     if (flag == 1) {
     byte[] temp = new byte[8];
     while (read.available() > 0) {
     read.read(temp);
     Write(dq.EncodeFromFile(temp));
     //Write(temp);
     }
     } else if (flag == 2) {
     byte[] temp = new byte[16];
     while (read.available() > 0) {                    
     read.read(temp);
     //Write(temp);
     Write(dq.DecodeFromFile(temp));
     }
     }
     closeStreams();
     //System.out.println("koniec");
     } catch (IOException ex) {
     Logger.getLogger(IOBinary.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
}
