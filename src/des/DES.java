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
public class DES extends JFrame {

    public DES() {
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        //new DES();
        String key = "133457799BBCDFF1";
        //String key = "3b3898371520f75e";
        long t1, t2;

        t1 = System.currentTimeMillis();
        KeyGenerator kgba = new KeyGenerator(key);
        Encoding eba = new Encoding(kgba);
        //for (int i = 0; i < 40000; i++) {
            eba.Encode("ABCDEFGH");
        //}
        t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

    }

}
