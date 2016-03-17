/*
 * Copyright 2016 Kevin Raoofi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.frostburg.cosc620;

import java.util.Arrays;

/**
 *
 * @author Kevin Raoofi
 */
public class Vigenere {

    /**
     * Mathematical mod implementation
     *
     * @param x the dividend
     * @param y the divisor
     * @return the modulo result of x and y
     */
    private static int mod(int x, int y) {
        return ((x % y) + y) % y;
    }

    public static int[] convertKey(String key) {
        final char[] ckey = key.toCharArray();
        final int[] ikey = new int[ckey.length];

        for (int i = 0; i < ckey.length; i++) {
            ikey[i] = ckey[i] - 'a';
        }
        return ikey;
    }

    public static String convertKey(int... key) {
        final char[] ckey = new char[key.length];

        for (int i = 0; i < ckey.length; i++) {
            ckey[i] = (char) (mod((key[i]), 26) + 'a');
        }

        return new String(ckey);
    }

    public static int[] invert(int[] key) {
        int[] invert = Arrays.copyOf(key, key.length);

        for (int i = 0; i < invert.length; i++) {
            invert[i] = mod(-invert[i], 26);
        }

        return invert;
    }

    private final int[] key;

    public Vigenere(int... key) {
        this.key = key;
    }

    public Vigenere(String key) {
        this.key = convertKey(key);
    }

    public Vigenere inverse() {
        return new Vigenere(invert(this.key));
    }

    public String encrypt(String plain) {
        final char[] arrplain = plain.toCharArray();

        for (int i = 0; i < arrplain.length; i++) {
            arrplain[i]
                    = (char) (mod(arrplain[i] - 'a' + key[i % key.length], 26)
                    + 'a');
        }

        return new String(arrplain);
    }

    public String decrypt(String plain) {
        final char[] arrplain = plain.toCharArray();

        for (int i = 0; i < arrplain.length; i++) {
            arrplain[i]
                    = (char) (mod(arrplain[i] - 'a' - key[i % key.length], 26)
                    + 'a');
        }

        return new String(arrplain);
    }

    public int[] getKey() {
        return key;
    }

    public Vigenere derive(String plain, String alt) {
        final int[] code = convertKey(this.encrypt(plain));
        final int[] altcode = convertKey(alt);

        final int[] result = new int[code.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = mod(code[i] - altcode[i], 26);
        }

        return new Vigenere(result);
    }

    public static void main(String... args) {
        
        
        Vigenere v = new Vigenere("better");
        
        System.out.println("KEY: " + Arrays.toString(convertKey("betterbette")));
        System.out.println("CODE: " + Arrays.toString(convertKey("explanation")));
        
        System.out.println(v.encrypt("explanation"));
        System.out.println(Arrays.toString(convertKey(v.encrypt("explanation"))));

//        Vigenere v = new Vigenere("leg");
//
//        String s = v.encrypt("explanation");
//        
//        System.out.println(Arrays.toString(convertKey("explanation")));
//        System.out.println(Arrays.toString(convertKey("legleglefle")));
//        System.out.println(Arrays.toString(convertKey(s)));
//
//        System.out.println(s);
//        System.out.println("===========");

//        showEncDec();
    }

    static void showEncDec() {
        final Vigenere v1 = new Vigenere(9, 0, 1, 7, 23, 15, 21, 14, 11, 11,
                2, 8, 9);
        // beokjdmsxzpmh
        final String s = v1.encrypt("sendmoremoney");

        final Vigenere v2 = v1.derive("sendmoremoney", "cashnotneeded");
        
        System.out.println(Arrays.toString(convertKey(s)));
        System.out.println(Arrays.toString(convertKey("cashnotneeded")));

        System.out.println(Arrays.toString(v2.getKey()));
        System.out.println(v2.decrypt(s));

    }
}
