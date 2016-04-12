
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringJoiner;

/*
 * Copyright 2016 Kevin Raoofi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 *
 * @author Kevin Raoofi
 */
public class RoundKey {

    private static final Gf2Polynomial TWO = Utils.toPoly((byte) 2);
    /** Round constants */
    final byte[] rc = new byte[11];
    //final byte[][] keys = new byte[44][4];
    final byte[][][] keys = new byte[11][4][4];

    final SubBytes sb;

    public RoundKey(byte[] initialKey, SubBytes sb, Gf2Polynomial mx) {
        this.sb = sb;
        populateRoundConstants(mx);

        populateKeys(initialKey);
    }

    private void populateRoundConstants(Gf2Polynomial mx) {

        rc[1] = 1;

        for (int i = 2; i < rc.length; i++) {
            rc[i] = Utils.toByte(
                    TWO.mult(Utils.toPoly(rc[i - 1]), mx)
            );
        }
    }

    private byte[] funcG(int round, byte[] prev) {
        final byte[] word = Arrays.copyOf(prev, prev.length);

        ShiftRows.shift(word, 1);
        sb.transform(word);
        word[0] ^= this.rc[round];

        return word;
    }

    /**
     * modifies arr1 with arr2 xor
     *
     * @param arr1
     * @param arr2
     */
    static void xorArr(byte[] arr1, byte[] arr2) {

        if (arr1.length != arr2.length) {
            throw new IllegalArgumentException("arr length must be equal");
        }

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] ^= arr2[i];
        }
    }

    static String ba2Hex(byte[] ba) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (byte b : ba) {
            sj.add(Integer.toHexString(b & 0xFF));
        }
        return sj.toString();
    }

    private void populateKeys(byte[] initial) {
        final byte[][] tKeys = new byte[44][4];
        final byte[][] words = Utils.stateToMat(initial);

        int i;
        for (i = 0; i < 4; i++) {
            // we can copy the first 4
            tKeys[i] = Arrays.copyOf(words[i], words[i].length);
        }

        for (; i < 44; i += 4) {
            // let's pre-populate with the previous key values
            for (int j = i; j < i + 4; j++) {
                tKeys[j] = Arrays.copyOf(tKeys[j - 4], tKeys[j - 4].length);
            }

            xorArr(tKeys[i], funcG(i / 4, tKeys[i - 1]));
            xorArr(tKeys[i + 1], tKeys[i]);
            xorArr(tKeys[i + 2], tKeys[i + 1]);
            xorArr(tKeys[i + 3], tKeys[i + 2]);
        }

        // we will now go and transpose each chunk and store it
        for (i = 0; i < 44; i += 4) {
            for (int j = 0; j < 4; j++) {
                keys[i / 4][j] = Arrays.copyOf(
                        tKeys[i + j],
                        tKeys[i + j].length);
            }
            Utils.transpose(keys[i / 4]);
        }

    }

    public byte[] getRoundKey(int round) {
        byte[][] roundKeyMat = this.keys[round];
        byte[] keySerialized = new byte[16];
        MixColumns.serialize(roundKeyMat, keySerialized);

        return keySerialized;
    }

    public static void main(String... args) {
        byte[] key = new BigInteger(
                "2b7e151628aed2a6abf7158809cf4f3c", 16)
                .toByteArray();
        RoundKey roundKey = new RoundKey(key, new SubBytes(Aes.RIJNDAEL_MX),
                Aes.RIJNDAEL_MX);

        for (int j = 0; j < roundKey.keys.length; j++) {
            for (int k = 0; k < roundKey.keys[j].length; k++) {
                for (int l = 0; l < roundKey.keys[j][k].length; l++) {
                    System.out.print(Integer.toHexString(roundKey.keys[j][k][l]
                            & 0xFF));
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}
