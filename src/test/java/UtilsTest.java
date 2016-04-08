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

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Kevin Raoofi
 */
public class UtilsTest {

    public UtilsTest() {
    }

    /**
     * Test of toPoly method, of class Utils.
     */
    @Test
    public void testToPoly_byte_FF() {
        System.out.println("toPoly");
        byte b = (byte) 0xFF;

        Gf2Polynomial expResult = new Gf2Polynomial(
                new int[]{1, 1, 1, 1, 1, 1, 1, 1}, 2);
        Gf2Polynomial result = Utils.toPoly(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of toPoly method, of class Utils.
     */
    @Test
    public void testToPoly_byte() {
        System.out.println("toPoly");
        byte b = 0b1010101;

        Gf2Polynomial expResult = new Gf2Polynomial(
                new int[]{1, 0, 1, 0, 1, 0, 1},
                2);
        Gf2Polynomial result = Utils.toPoly(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of toPoly method, of class Utils.
     */
    @Test
    public void testToPoly_byte2() {
        System.out.println("toPoly");
        byte b = 0b110;

        Gf2Polynomial expResult = new Gf2Polynomial(
                new int[]{1, 1, 0},
                2);
        Gf2Polynomial result = Utils.toPoly(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of byteToCoefficientArray method, of class Utils.
     */
    @Test
    public void testByteToCoefficientArray() {
        System.out.println("byteToCoefficientArray");
        byte poly = 7;
        int[] expResult = new int[]{0, 0, 0, 0, 0, 1, 1, 1};

        int[] result = Utils.byteToCoefficientArray(poly);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of toPoly method, of class Utils.
     */
    @Test
    public void testToPoly_byteArr() {

    }

    /**
     * Test of toPoly method, of class Utils.
     */
    @Test
    public void testToFromPolyByteAll() {
        for (int i = 0; i < 0x100; i++) {
            // note that you must AND with 0xFF because byte is unsigned
            final int result = Utils.toByte(Utils.toPoly((byte) i)) & 0xFF;

            assertEquals(i, result);

            // let's make sure that those two numbers are equal in byte form
            final byte expectedByte = (byte) i;
            assertEquals(expectedByte, (byte) result);
        }
    }

    /**
     * Test of toPoly method, of class Utils.
     */
    @Test
    public void testToFromByteCoefRange() {
        int[][] coeff = new int[][]{
            Utils.byteToCoefficientArray((byte) 0),
            Utils.byteToCoefficientArray((byte) 1),
            Utils.byteToCoefficientArray((byte) 2),
            Utils.byteToCoefficientArray((byte) 3),
            Utils.byteToCoefficientArray((byte) 4),
            Utils.byteToCoefficientArray((byte) 5),
            Utils.byteToCoefficientArray((byte) 6)
        };

        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 0}, coeff[0]);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0, 1}, coeff[1]);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 1, 0}, coeff[2]);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 1, 1}, coeff[3]);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 1, 0, 0}, coeff[4]);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 1, 0, 1}, coeff[5]);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 1, 1, 0}, coeff[6]);

        for (int i = 0; i < 7; i++) {
            byte b = Utils.toByte(coeff[i]);
            assertEquals(i, b);
        }

        for (int i = 0; i < 7; i++) {
            int[] newCoeff = new Gf2Polynomial(coeff[i], 2).getCoefficients();
            int[] newCoeff2 = Utils.backFillCopy(newCoeff, 8);

            System.out.println(Arrays.toString(newCoeff) + " <> "
                    + Arrays.toString(newCoeff2));

            assertArrayEquals(coeff[i], newCoeff2);
        }

    }

    /**
     * Test of toByte method, of class Utils.
     */
    @Test
    public void testToByte() {
        System.out.println("toByte");
        byte expResult = 0b1010101;
        Gf2Polynomial poly = Utils.toPoly(expResult);
        byte result = Utils.toByte(poly);
        assertEquals(expResult, result);
    }

}
