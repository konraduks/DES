/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

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
            tempStr += des.Encode(msg.substring(i, i + 8));
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
            tempStr += des.Decode(msg.substring(i, i + 16));
        }
        paddingDelete(tempStr.substring(tempInt / 2 - 8, tempInt / 2));
        return tempStr;
    }

    public void Stream(String key) {  // metoda I dla pliku ZAWSZE!!!
        des = new DESEngine(key);
    }

    public String EncodeFromFile(String msg) { // kodowanie pliku po 8 bajtow
        //tempStr = des.Encode(msg);
        return des.Encode(msg);
    }
    
    public String EncodeFromFileLastBlock(String msg){ // ostatni blok, sprawdzenie czy potrzebny padding
        if(msg.length() == 8){
            return des.Encode(msg);
        }
        padding(tempStr = msg, 8 - msg.length());
        return tempStr;
    }

    public String DecodeFromFile(String msg) { // dekodowanie pliku po 8 bajtow
        return des.Decode(msg);
    }
    
    public String DecodeFromFileLastBlock(String msg){ // dekodowanie ostatniego bloku, sprawdzenie czy wystepuje padding
        paddingDelete(tempStr = msg);
        return tempStr;
    }

    private void padding(String msg, int offset) { //PKCS5
        String temp = "";
        for (int i = 0; i < offset; i++) {
            temp += offset + "";
        }
        tempStr += des.Encode(msg + temp);
    }

    private void paddingDelete(String msg) { //PKCS5
        //if (msg.charAt(msg.length() - 1) > 9) {
        if (msg.charAt(msg.length() - 1) > 57) {
            //System.out.println("lol");
            return;
        }
        int possibleOffset = Integer.parseInt(msg.charAt(msg.length() - 1) + "");
        for (int i = 0; i < possibleOffset; i++) {
            if (Integer.parseInt(msg.charAt(msg.length() - 1) + "") != possibleOffset) {
                return;
            }
        }
        tempStr = tempStr.substring(0, tempStr.length() - possibleOffset);

    }
}
