/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import Encryption.Encoding;
import KeyGenerator.KeyGenerator;
import javax.swing.JFrame;

/**
 *
 * @author Konrad
 */
public class DES extends JFrame{

    public DES(){
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        //new DES();
        String key = "133457799BBCDFF1";
        long t1, t2;
        t1 = System.currentTimeMillis();
        KeyGenerator a = new KeyGenerator(key);
        a.Key(16);
        
        //System.out.println(t2 - t1);
        
        Encoding en = new Encoding(a);
        String temp = "ABCDEFGH";
        
        //String binaryMessage = "011000010001011110111010100001100110010100100111";
        //en.SboxTransformation(binaryMessage);
        en.Encode(temp);
        t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
        //temp = binaryMessage.substring(0, 6);
        //System.out.println(temp);
        //long whichSboxRow = Integer.parseInt(temp.charAt(0)+"")*2 + Integer.parseInt(temp.charAt(5)+"");
        //temp = temp.substring(1, 4);
        //int whichColumn = Integer.parseInt(temp.substring(1, 5));
        //System.out.println(whichSboxRow + " " + whichColumn);
        //en.Encode("01234567");
        //System.out.println(en.Epermutation(1));
        //long a1 = 0b1000;
        //int bin = 0b100;
        //int bin2 = bin + 0b010;
        //System.out.println(b);
        //a.Print(16);
    }

}
