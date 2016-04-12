
import java.util.Arrays;

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
public class MixColumns {

    private static final Gf2Polynomial ZERO = new Gf2Polynomial(new int[]{}, 2);
    private static final Gf2Polynomial ONE = Utils.toPoly((byte) 1);
    private static final Gf2Polynomial TWO = Utils.toPoly((byte) 2);
    private static final Gf2Polynomial THREE = Utils.toPoly((byte) 3);

    private static final Gf2Polynomial I_0E = Utils.toPoly((byte) 0x0e);
    private static final Gf2Polynomial I_0B = Utils.toPoly((byte) 0x0B);
    private static final Gf2Polynomial I_0D = Utils.toPoly((byte) 0x0D);
    private static final Gf2Polynomial I_09 = Utils.toPoly((byte) 0x09);

    private static final Gf2Polynomial[][] MC_MATRIX = new Gf2Polynomial[][]{
        {TWO, THREE, ONE, ONE},
        {ONE, TWO, THREE, ONE},
        {ONE, ONE, TWO, THREE},
        {THREE, ONE, ONE, TWO}
    };

    private static final Gf2Polynomial[][] MC_MATRIX_INV
            = new Gf2Polynomial[][]{
                {I_0E, I_0B, I_0D, I_09},
                {I_09, I_0E, I_0B, I_0D},
                {I_0D, I_09, I_0E, I_0B},
                {I_0B, I_0D, I_09, I_0E}
            };

    static void serializeWords(byte[][] words, byte[] ba) {
        for (int i = 0; i < words.length; i++) {
            System.arraycopy(words[i], 0, ba, i * 4, 4);
        }
    }

    private final Gf2Polynomial mx;

    public MixColumns(Gf2Polynomial mx) {
        this.mx = mx;
    }

    void apply(byte[][] words, Gf2Polynomial[][] order) {
        final Gf2Polynomial[][][] pword
                = new Gf2Polynomial[words.length][words.length][1];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                pword[i][j][0] = Utils.toPoly(words[i][j]);
            }
        }

        final Gf2Polynomial[][][] res = new Gf2Polynomial[words.length][][];
        for (int i = 0; i < words.length; i++) {
            res[i] = matMult(order, pword[i]);
        }

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                words[i][j] = Utils.toByte(res[i][j][0]);
            }
        }

    }

    private Gf2Polynomial[][] matMult(Gf2Polynomial[][] matA,
            Gf2Polynomial[][] matB) {
        final Gf2Polynomial[][] ans
                = new Gf2Polynomial[matA.length][matB[0].length];

        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[i].length; j++) {
                ans[i][j] = ZERO;
            }
        }

        for (int i = 0; i < matA.length; i++) {
            for (int j = 0; j < matB[0].length; j++) {
                for (int k = 0; k < matA[0].length; k++) {
                    //ans[i][j] += matA[i][k] * matB[k][j];
                    ans[i][j] = ans[i][j].add(
                            matA[i][k].mult(matB[k][j], this.mx));
                }
            }
        }

        return ans;
    }

    static byte[][] stateToMat(byte[] state) {
        final byte[][] words = new byte[4][];

        final int f = state.length / 4;

        for (int i = 0; i < words.length; i++) {
            words[i] = Arrays.copyOfRange(state, f * i, f * (i + 1));
        }

        return words;
    }

    static void serialize(byte[][] words, byte[] ba) {
        for (int i = 0; i < words.length; i++) {
            System.arraycopy(words[i], 0, ba, i * 4, 4);
        }
    }

    public void transform(byte[] state) {
        final byte[][] words = stateToMat(state);

        Utils.transpose(words);

        apply(words, MC_MATRIX);
        Utils.transpose(words);
        serialize(words, state);
    }

    public void reverse(byte[] state) {
        final byte[][] words = stateToMat(state);

        Utils.transpose(words);

        apply(words, MC_MATRIX_INV);
        Utils.transpose(words);
        serialize(words, state);
    }

    public static void main(String... args) {
        new MixColumns(Aes.RIJNDAEL_MX).transform(new byte[]{
            0, 4, 8, 12,
            1, 5, 9, 13,
            2, 6, 10, 14,
            3, 7, 11, 15}
        );
    }

}
