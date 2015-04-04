/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

import KeyGenerator.KeyGenerator;
import des.ASCIItoBIN;
import java.util.Arrays;

/**
 *
 * @author Konrad
 */
public class Encoding {

    ASCIItoBIN atb;
    //String[] L, R;
    KeyGenerator KeyG;
    byte[][] Left, Right, Keys;

    private static int IP[]
            = {58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17, 9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7};

    public Encoding(KeyGenerator key) {
        KeyG = key;
        Left = new byte[17][];
        Right = new byte[17][];
        Keys = new byte[17][];
        for (int i = 1; i <= 16; i++) {
            Keys[i] = KeyG.getKey(i);
        }
    }

    public void Encode(String message) {
        byte[] msg = message.getBytes();
        Splitting(InitialPermutation(msg));

        for (int i = 1; i <= 16; i++) {
            //System.out.println("i: " + i);            
            Right[i] = XOR(Left[i - 1], Ppermutation(SboxTransformation(XORwithSubkey(Epermutation(i), i))));
            Left[i] = Right[i - 1];
            //System.out.println("Left: " + Left[i].length);
        }
        /*System.out.println("");
        for(byte b: Right[16]){
            System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " "); 
        }
        System.out.println("");
        for(byte b: Left[16]){
            System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " "); 
        }
        System.out.println("");*/
        for(int i =0; i < 4; i++){
            msg[i%4] = Right[16][i*2];
            msg[i%4] <<= 4;
            msg[i%4] += Right[16][i*2+1] ;
            msg[i+4] = Left[16][i*2];
            msg[i+4] <<= 4;
            msg[i+4] += Left[16][i*2+1] ;
        }
        FinalPermutation(msg);
        //byte[] temp = new byte[8];
        //temp[0] = (byte) (Right[16][0] << 4);
        //temp[0] = 
        //FinalPermutation(Right[16]+Left[16]);
        /*R[i] = XOR(L[i - 1], Ppermutation(SboxTransformation(XORwithSubkey(Epermutation(i), i))));
         L[i] = R[i - 1];
         //Ppermuatation(SboxTransformation(XORwithSubkey(Epermutation(i), i)));
         }
         FinalPermutation(R[16] + L[16]);*/
        //System.out.println(FinalPermutation(R[16]+L[16]));
    }

    public byte[] InitialPermutation(byte[] message) {
        byte[] msg = new byte[message.length];
        for (int i = 0; i < 64; i++) {
            //System.out.println(message[(IP[i]-1)/8]);
            //System.out.println((message[(IP[i] - 1) / 8] >> (7 - ((IP[i] - 1) % 8))) & 0x01);
            msg[i / 8] <<= 1;

            msg[i / 8] += ((message[(IP[i] - 1) / 8] >> (7 - ((IP[i] - 1) % 8))) & 0x01);
            //msg[i / 8] += (byte) getByte(message[(IP[i] - 1) / 8], (IP[i] - 1) % 8);
        }
        return msg;
    }

    private int getByte(byte Byte, int pos) {
        int temp = (Byte >> (7 - pos)) & 0x01;
        return temp;
    }

    public void Splitting(byte[] binaryMessage) {
        Left[0] = new byte[8];
        Right[0] = new byte[8];
        for (int i = 0; i < 4; i++) {
            Left[0][i * 2] = (byte) ((binaryMessage[i] >> 4) & 0xf);
            Left[0][i * 2 + 1] = (byte) (binaryMessage[i] & 0xf);
            Right[0][i * 2] = (byte) ((binaryMessage[i + 4] >> 4) & 0xf);
            Right[0][i * 2 + 1] = (byte) (binaryMessage[i + 4] & 0xf);
        }        
        //Left[0] = Arrays.copyOfRange(binaryMessage, 0, 4);
        //Right[0] = Arrays.copyOfRange(binaryMessage, 4, 8);
    }

    private static int E[]
            = {32, 1, 2, 3, 4, 5,
                4, 5, 6, 7, 8, 9,
                8, 9, 10, 11, 12, 13,
                12, 13, 14, 15, 16, 17,
                16, 17, 18, 19, 20, 21,
                20, 21, 22, 23, 24, 25,
                24, 25, 26, 27, 28, 29,
                28, 29, 30, 31, 32, 1};
    
    private byte[] Epermutation(int level) {  
        //byte[] tt = Right[0];
        byte[] temp = new byte[8];        
        for (int i = 0; i < 48; i++) {
            temp[i / 6] <<= 1;            
            temp[i / 6] += ((Right[level - 1][(E[i] - 1) / 4] >> (3 - ((E[i] - 1) % 4))) & 0x01);            
        }        
        return temp;
    }

    /*private byte[] Epermutation(int level) {  
        //byte[] tt = Right[0];
        byte[] temp = new byte[8];        
        //System.out.println(Right[level-1][0]);
        for (int i = 0; i < 48; i++) {
            temp[i / 6] <<= 1;
            //System.out.println(7 - ((E[i] - 1) % 8));
            //System.out.println(((Right[level - 1][(E[i] - 1) / 8] >> (7 - ((E[i] - 1) % 8))) & 0x01));
            //System.out.println(i + " Epermutation: " +((Right[level - 1][(E[i] -1) / 8] >> (7 - ((E[i] - 1) % 8))) /*& 0x01));
            //temp [1 / 4] += ((msg[(P[i] - 1) / 4] >> (7 - ((P[i] - 1) % 4))) & 0x01); 
            temp[i / 6] += ((Right[level - 1][(E[i] - 1) / 4] >> (3 - ((E[i] - 1) % 4))) & 0x01);
            //temp[i / 7] += getByte(Right[level - 1][(E[i] - 1) / 8], (E[i] - 1) % 8);
            //for (int y = 0; y < 6; y++) {
            //System.out.println("lol" +(E[i][y] - 1));
            //E_R += R[level - 1].charAt(E[i][y] - 1);
            //temp += message.charAt(IP[i][y] - 1);
            //}
        }        
        return temp;
    }*/

    private byte[] XORwithSubkey(byte[] E_R, int level) {
        byte[] key = Keys[level];
        byte[] XOR = new byte[8];
        //XOR = key ^ E_R;
        //System.out.println("Key: " + key.length);
        for (int i = 0; i < 8; i++) {
            XOR[i] = (byte) (key[i] ^ E_R[i]);
            //XOR += (key.charAt(i) == E_R.charAt(i)) ? 0 : 1;
            //System.out.print(XOR[i]+" ");
            //System.out.println(key[i]+" "+E_R[i]);
        }
        /*for (byte by : XOR) {
            //String.format("%8s", Integer.toBinaryString(b2 & 0xFF)).replace(' ', '0');
            System.out.print(String.format("%6s", Integer.toBinaryString(by & 0xFF)).replace(' ', '0') + " ");
            //System.out.print(Integer.toBinaryString(by).replace(' ', '0') + " ");
        }
        System.out.println("");*/
        return XOR;
    }

    private byte[] XOR(byte[] b1, byte[] b2) {
        //System.out.println(b1.length + " " + b2.length);
        //System.out.println(b1.length+ " " + b2.length);
        for (int i = 0; i < 8; i++) {
            b2[i] = (byte) (b2[i] ^ b1[i]);
        }
        //System.out.println("b2: "+b2.length);
        return b2;
    }

    int[][] SBOXES = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};

    private byte[] SboxTransformation(byte[] msg) {
        int row;
        int column;        
        for (int i = 0; i < 8; i++) {
            row = ((msg[i] >> 4) & 0x02) + (msg[i] & 0x01);
            //row += msg[i] & 0x01;
            //row = msg[i] & 0x21;
            //row = msg[i] & 0x20;
            //row <<= 1;
            //row = msg[i] & 0x01;
            column = (msg[i] >> 1) & 0xf;
            //System.out.println(row +" " + column);
            msg[i] = (byte) SBOXES[row + i * 4][column];
            //System.out.println("SBOX " + msg[i]);
            //System.out.print(String.format("%4s", Integer.toBinaryString(msg[i] & 0xFF)).replace(' ', '0') + " ");
        }
        return msg;

    }

    int[] P = {
        16, 7, 20, 21,
        29, 12, 28, 17,
        1, 15, 23, 26,
        5, 18, 31, 10,
        2, 8, 24, 14,
        32, 27, 3, 9,
        19, 13, 30, 6,
        22, 11, 4, 25};


    private byte[] Ppermutation(byte[] msg) {
        byte[] temp = new byte[8];
        //String F_P = "";
        for (int i = 0; i < 32; i++) {
            //for (int y = 0; y < 4; y++) {
            //getByte(message[(IP[i] - 1) / 8], (IP[i] - 1) % 8);
            //System.out.println(((P[i]-1) / 4) + " " + (P[i]-1 % 4));
            temp[i / 4] <<= 1;            
            temp[i / 4] += ((msg[(P[i] - 1) / 4] >> (3 - ((P[i] - 1) % 4))) & 0x01);
            //temp[i / 4] += getByte(msg[(P[i] - 1) / 4], ((P[i] - 1) % 4));
            //F_P += binaryMessage.charAt(P[i][y] - 1);
            //}
        }
        /*for (byte by : temp) {            
            System.out.print(String.format("%4s", Integer.toBinaryString(by & 0xFF)).replace(' ', '0') + " ");            
        }
        System.out.println("");*/
        return temp;
    }

    int[] IP1
            = {40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25};

    private byte[] FinalPermutation(byte[] msg) {
        //String FP = "";
        //System.out.println(binaryMessage);
        //System.out.println(binaryMessage.length());
        byte[] temp = new byte[8];
        for (int i = 0; i < 64; i++) {
            //for (int y = 0; y < 8; y++) {
            //getByte(message[(IP[i] - 1) / 8], (IP[i] - 1) % 8);
            temp[i / 8] <<= 1;
            //FP += binaryMessage.charAt(IP1[i][y] - 1);
            temp[i / 8] += ((msg[(IP1[i] - 1) / 8] >> (7 - ((IP1[i] - 1) % 8))) & 0x01);
            //}
        }
        /*for (byte by : temp) {            
            System.out.print(String.format("%8s", Integer.toBinaryString(by & 0xFF)).replace(' ', '0') + " ");            
        }
        System.out.println("");*/
        return temp;
    }
}
