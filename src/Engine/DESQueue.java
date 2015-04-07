/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.util.Arrays;

/**
 *
 * @author Konrad
 */
public class DESQueue {

    DESEngine des;
    String tempStr;
    int tempInt;

    public DESQueue() {
        tempStr = "";
    }

    public String Encode(String msg, String key) {
        des = new DESEngine(key);
        tempInt = msg.length() - (msg.length() % 8);
        for (int i = 0; i < tempInt; i += 8) {
            //System.out.println(tempInt);
            tempStr += des.EncodeStringtoString(msg.substring(i, i + 8));
        }
        if ((tempInt = msg.length() % 8) != 0) {
            padding(msg.substring(msg.length() - tempInt, msg.length()), 8 - tempInt);
        }
        return tempStr;
    }

    public String Decode(String msg, String key) {
        des = new DESEngine(key);
        tempInt = msg.length() /*- (msg.length() % 8)*/;
        for (int i = 0; i < tempInt; i += 16) {
            tempStr += des.DecodeStringtoString(msg.substring(i, i + 16));
        }
        paddingDelete(tempStr.substring(tempInt / 2 - 8, tempInt / 2));
        return tempStr;
    }

    public void Stream(String key) {  // metoda I dla pliku ZAWSZE!!!
        des = new DESEngine(key);
    }

    public byte[] EncodeFromFile(byte[] msg) { // kodowanie pliku po 8 bajtow
        //tempStr = des.Encode(msg);
        return des.EncodeBytetoByte(msg);
    }

    public byte[] EncodeFromFileLastBlock(byte[] msg) { // ostatni blok, sprawdzenie czy potrzebny padding
        /*if (msg.length == 8) {
            return des.EncodeBytetoByte(msg);
        }*/
        //byte[] tempByte;
        //padding(tempByte = msg, 8 - msg.length);
        return des.EncodeBytetoByte(padding(msg, 8 - msg.length));
    }

    public byte[] DecodeFromFile(byte[] msg) { // dekodowanie pliku po 8 bajtow
        return des.DecodeBytetoByte(msg);
    }

    public byte[] DecodeFromFileLastBlock(byte[] msg) { // dekodowanie ostatniego bloku, sprawdzenie czy wystepuje padding
        //paddingDelete(tempStr = msg);
        return paddingDelete(des.DecodeBytetoByte(msg));
    }

    private void padding(String msg, int offset) { //PKCS5
        String temp = "";
        for (int i = 0; i < offset; i++) {
            temp += offset + "";
        }
        tempStr += des.EncodeStringtoString(msg + temp);
    }

    private byte[] padding(byte[] msg, int offset) {
        //byte[] temp = new byte[8];
        //temp = msg;
        byte [] temp = Arrays.copyOf(msg, 8);
        System.out.println(temp.length);
        for (int i = 0; i < offset; i++) {
            temp[7 - i] = (byte) (48 + offset);
        }
        return temp;
    }

    private void paddingDelete(String msg) { //PKCS5
        //if (msg.charAt(msg.length() - 1) > 9) {
        if (msg.charAt(msg.length() - 1) > 57) {
            //System.out.println("lol");
            return;
        }
        int possibleOffset = Integer.parseInt(msg.charAt(msg.length() - 1) + "");
        //System.err.println(possibleOffset);
        for (int i = 0; i < possibleOffset; i++) {
            try {
                if (Integer.parseInt(msg.charAt(msg.length() - 1 - i) + "") != possibleOffset) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
        }
        tempStr = tempStr.substring(0, tempStr.length() - possibleOffset);
    }

    private byte[] paddingDelete(byte[] msg) {
        if (msg[7] > 57) {            
            return msg;
        }
        int possibleOffset = msg[7];        
        for (int i = 7; i >= (8 - (possibleOffset - 48)); i--) {
            if(msg[i] != possibleOffset){                
                return msg;
            }
        }        
        return Arrays.copyOf(msg, 8-(possibleOffset - 48));
    }
}
