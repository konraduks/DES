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

    public String EncodeFromFile(String msg) {
        return null;
    }

    public String DecodeFromFile(String msg) {
        return null;
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
            System.out.println("lol");
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
