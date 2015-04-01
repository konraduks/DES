/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import KeyGenerator.KeyGenerator;

/**
 *
 * @author Konrad
 */
public class DES {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String key = "133457799BBCDFF1";
       KeyGenerator a = new KeyGenerator(key);
       a.Key(16);
       a.Print(16);
    }
    
}
