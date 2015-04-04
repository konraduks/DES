/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KeyGenerator;

import java.util.Arrays;

/**
 *
 * @author Konrad
 */
public class KeyGenerator {

    String KPlus = "";

    private static int PC1[] = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4};

    private static int PC2[] = {
        14, 17, 11, 24, 1, 5,
        3, 28, 15, 6, 21, 10,
        23, 19, 12, 4, 26, 8,
        16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32};

    private static int NumberOfShifts[] = {0, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    byte[][] C = new byte[17][];
    byte[][] D = new byte[17][];
    byte[][] Key = new byte[17][];

    public KeyGenerator(String GivenKey) {
        byte[] temp = new byte[8];//GivenKey.getBytes();
        for (int i = 0; i < 16; i++) {
            temp[i / 2] <<= 4;
            temp[i / 2] += Integer.parseInt(GivenKey.charAt(i) + "", 16);
        }
        Splitting(PC1Permutation(temp));
    }

    private byte[] PC1Permutation(byte[] message) {
        byte[] temp = new byte[8];
        for (int i = 0; i < 56; i++) {
            temp[i / 7] <<= 1;
            temp[i / 7] += getByte(message[(PC1[i] - 1) / 8], (PC1[i] - 1) % 8);
        }
        return temp;
    }

    private int getByte(byte Byte, int pos) {
        int temp = (Byte >> (7 - pos)) & 0x01;
        return temp;
    }

    private void Splitting(byte[] binaryMessage) {
        C[0] = Arrays.copyOfRange(binaryMessage, 0, 4);
        D[0] = Arrays.copyOfRange(binaryMessage, 4, 8);
    }

    public byte[] getKey(int NumberOfPermutation) {
        if (Key[NumberOfPermutation] != null) {
            return Key[NumberOfPermutation];
        }
        return Key(NumberOfPermutation);
    }

    private byte[] Key(int NumberOfPermutation) {
        generateC(NumberOfPermutation);
        generateD(NumberOfPermutation);

        byte[] temp = new byte[8];
        System.arraycopy(C[NumberOfPermutation], 0, temp, 0, 4);
        System.arraycopy(D[NumberOfPermutation], 0, temp, 4, 4);
        byte[] result = new byte[8];
        /*for (byte by : temp) {
            String.format("%8s", Integer.toBinaryString(b2 & 0xFF)).replace(' ', '0');
            System.out.print(String.format("%7s", Integer.toBinaryString(by & 0xFF)).replace(' ', '0') + " ");
            System.out.print(Integer.toBinaryString(by).replace(' ', '0') + " ");
        }*/
        for (int i = 0; i < 48; i++) {
            result[i / 6] <<= 1;
            result[i / 6] += ((temp[(PC2[i] - 1) / 7] >> (6 - ((PC2[i] - 1) % 7))) & 0x01);;//getByte(temp[(PC2[i] - 1) / 7], (PC2[i] - 1) % 7);
        }
        Key[NumberOfPermutation] = result;
        return result;
    }

    private byte[] generateC(int NumberOfPermutation) {
        if (C[NumberOfPermutation - 1] == null) {
            generateC(NumberOfPermutation - 1);
        }
        return C[NumberOfPermutation] = Shifts(C[NumberOfPermutation - 1], NumberOfShifts[NumberOfPermutation]);
    }

    private byte[] generateD(int NumberOfPermutation) {
        if (D[NumberOfPermutation - 1] == null) {
            generateD(NumberOfPermutation - 1);
        }
        return D[NumberOfPermutation] = Shifts(D[NumberOfPermutation - 1], NumberOfShifts[NumberOfPermutation]);
    }

    private byte[] Shifts(byte[] array, int offset) {
        byte[] temp = new byte[4];
        for (int i = 3; i >= 0; i--) {
            temp[i] = (byte) (array[i] << offset);
            //temp[i] += array[(i + 1) % 4] & (offset*2-1);
            temp[i] += ((array[(i + 1) % 4] >> (7 - offset)) & (offset * 2 - 1));
            temp[i] &= 0x7f; //aby najstarszy bit zawsze był 0 - używamy tylko 7 bit 
        }
        return temp;
    }
}
