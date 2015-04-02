/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

/**
 *
 * @author Konrad
 */
public class ASCIItoBIN {    

    public String ASCIItoBIN(String str) {        
        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }            
        }        
        //System.out.println(binary);
        //System.out.println("'" + str + "' to binary: " + binary);
        return binary.toString();
    }

}
