
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
public class SubBytes {

    /**
     * Multiplies 2 matrices together and returns a result
     *
     * @param matA first
     * @param matB second
     * @return result
     */
    int[][] binMatMult(int[][] matA, int[][] matB) {
        final int[][] ans = new int[matA.length][matB[0].length];

        for (int i = 0; i < matA.length; i++) {
            for (int j = 0; j < matB[0].length; j++) {
                for (int k = 0; k < matA[0].length; k++) {
                    ans[i][j] ^= matA[i][k] * matB[k][j];
                }
            }
        }

        return ans;
    }

    private static final Gf2Polynomial onePoly = new Gf2Polynomial(
            new int[]{1}, 2);

    private static final int[][] AFFINE_MATRIX = new int[][]{
        {1, 0, 0, 0, 1, 1, 1, 1},
        {1, 1, 0, 0, 0, 1, 1, 1},
        {1, 1, 1, 0, 0, 0, 1, 1},
        {1, 1, 1, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 0, 0, 0},
        {0, 1, 1, 1, 1, 1, 0, 0},
        {0, 0, 1, 1, 1, 1, 1, 0},
        {0, 0, 0, 1, 1, 1, 1, 1}
    };

    private static int[][] vecToMat(int[] vec) {
        int[][] mat = new int[vec.length][1];

        for (int i = 0; i < vec.length; i++) {
            mat[i][0] = vec[i];
        }

        return mat;
    }

    private static int[] matToVec(int[][] mat) {
        int[] vec = new int[mat.length];

        for (int i = 0; i < mat.length; i++) {
            vec[i] = mat[i][0];
        }

        return vec;
    }
    private final int[] sbox = new int[256];
    private final int[] invSbox = new int[256];

    private final Gf2Polynomial m;

    public SubBytes(Gf2Polynomial m) {
        this.m = m;
        buildSbox();
        buildInvSbox();
    }

    private void buildSbox() {
        // note that we start from 0; the inverse of 0 is 0
        for (int i = 0; i <= 0xFF; i++) {
            final Gf2Polynomial bPoly = Utils.toPoly((byte) i);
            final Gf2Polynomial res = onePoly.div(bPoly, m);
            final int[] coef = Utils.byteToCoefficientArray(Utils.toByte(res));

            final int[][] vertByteVec = vecToMat(Utils.arrayReversed(coef));

            int[] vec = matToVec(binMatMult(AFFINE_MATRIX, vertByteVec));
            vec = Utils.arrayReversed(vec);

            sbox[i] = (Utils.toByte(vec) ^ 99) & 0xFF;
        }
    }

    /**
     * Run only after buildSbox has completed
     */
    private void buildInvSbox() {
        for (int i = 0; i < this.sbox.length; i++) {
            this.invSbox[this.sbox[i]] = i;
        }
    }

    public int[] getSbox() {
        return Arrays.copyOf(this.sbox, this.sbox.length);
    }

    public int[] getInvSbox() {
        return Arrays.copyOf(this.invSbox, this.invSbox.length);
    }

    public void transform(byte[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) this.sbox[input[i] & 0xFF];
        }
    }

    public void reverse(byte[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) this.invSbox[input[i] & 0xFF];
        }
    }

    public static void main(String... args) {
        SubBytes subBytes
                = new SubBytes(new Gf2Polynomial(new int[]{1, 0, 0, 0, 1, 1, 0,
            1, 1}, 2));
        subBytes.buildSbox();
    }
}
