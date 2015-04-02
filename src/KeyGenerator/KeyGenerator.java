/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KeyGenerator;

/**
 *
 * @author Konrad
 */
public class KeyGenerator {

    String KPlus = "";

    private static int PC1[][] = {
        {57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4}};
    private static int PC2[][] = {
        {14, 17, 11, 24, 1, 5},
        {3, 28, 15, 6, 21, 10},
        {23, 19, 12, 4, 26, 8},
        {16, 7, 27, 20, 13, 2},
        {41, 52, 31, 37, 47, 55},
        {30, 40, 51, 45, 33, 48},
        {44, 49, 39, 56, 34, 53},
        {46, 42, 50, 36, 29, 32}};
    private static int NumberOfShifts[] = {0, 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    String[] C = new String[17];
    String[] D = new String[17];

    public KeyGenerator(String GivenKey) {
        String tempBinary = "";
        String binary;
        int[] tempInt = new int[16];
        String[][] BinaryKey = new String[8][8];

        for (int i = 0, x = 0, y = -1; i < 16; i++, x = x % 8) {
            if (x == 0) {
                y++;
            }
            tempInt[i] = Integer.parseInt(/*temp[i]+""*/GivenKey.charAt(i) + "", 16);
            binary = Integer.toBinaryString(tempInt[i]);
            while (binary.length() != 4) {
                binary = '0' + binary;
            }
            tempBinary += binary;
        }
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 7; y++) {
                KPlus += tempBinary.charAt(PC1[i][y] - 1);
            }
        }
        C[0] = KPlus.substring(0, 28);
        D[0] = KPlus.substring(28, 56);
    }

    public String Key(int NumberOfPermutation) {
        generateC(NumberOfPermutation);
        generateD(NumberOfPermutation);

        String temp = C[NumberOfPermutation] + D[NumberOfPermutation];
        String result = "";
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 6; y++) {
                result += temp.charAt(PC2[i][y] - 1);
            }
        }
        //System.out.println(result);
        return result;
    }

    private String generateC(int NumberOfPermutation) {
        if (C[NumberOfPermutation - 1] == null) {
            generateC(NumberOfPermutation - 1);
        }
        return C[NumberOfPermutation] = C[NumberOfPermutation - 1].substring(NumberOfShifts[NumberOfPermutation], C[NumberOfPermutation - 1].length()) + C[NumberOfPermutation - 1].substring(0, NumberOfShifts[NumberOfPermutation]);
    }

    private String generateD(int NumberOfPermutation) {
        if (D[NumberOfPermutation - 1] == null) {
            generateD(NumberOfPermutation - 1);
        }
        return D[NumberOfPermutation] = D[NumberOfPermutation - 1].substring(NumberOfShifts[NumberOfPermutation], D[NumberOfPermutation - 1].length()) + D[NumberOfPermutation - 1].substring(0, NumberOfShifts[NumberOfPermutation]);
    }

    public void Print(int NumberOfPermutation) {
        for (int i = 1; i <= NumberOfPermutation; i++) {
            System.out.println(C[i]);
            System.out.println(D[i]);
            System.out.println("");
        }
    }

}
