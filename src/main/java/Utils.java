
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
public final class Utils {

    public static Gf2Polynomial[] toPoly(byte[] ba) {
        final Gf2Polynomial[] polys = new Gf2Polynomial[ba.length];
        for (int i = 0; i < ba.length; i++) {
            polys[i] = toPoly(ba[i]);
        }

        return polys;
    }

    public static Gf2Polynomial toPoly(byte b) {
        final int[] coef = byteToCoefficientArray(b);

        return new Gf2Polynomial(coef, 2);
    }

    public static byte toByte(Gf2Polynomial poly) {
        int[] coefficients = poly.getCoefficients();

        return toByte(coefficients);
    }

    public static int[] backFillCopy(int[] arr, int size) {
        final int[] narr = new int[size];

        final int offset = size - arr.length;

        for (int i = 0; i < arr.length; i++) {
            narr[i + offset] = arr[i];
        }

        return narr;
    }

    public static int[] arrayReversed(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);

        reverseArray(copy);
        return copy;
    }

    private static void reverseArray(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }

    public static byte toByte(int[] array) {
        byte result = 0;

        int[] rArray = arrayReversed(array);

        for (int i = 0; i < rArray.length; i++) {
            assert rArray[i] == 0 || rArray[i] == 1 :
                    "Coefficients include non-binary digits; is P != 2?";

            result += (byte) (rArray[i] << i);
        }

        return result;
    }

    public static int[] byteToCoefficientArray(byte poly) {
        final int[] coef = new int[8];
        for (int i = 0; i < 8; i++) {
            coef[i] = ((poly & 0xFF) >>> i) & 1;
        }

        return arrayReversed(coef);
    }

    public static void transpose(byte[][] mat) {
        for (int i = 0; i < mat.length + 1; i++) {
            for (int j = i; j < mat[0].length; j++) {
                byte temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }
    }

}
