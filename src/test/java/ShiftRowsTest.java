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
public class ShiftRowsTest {

    public ShiftRowsTest() {
    }

    /**
     * Test of stateToMat method, of class ShiftRows.
     */
    @Test
    public void testStateToMat() {
        System.out.println("stateToMat");
        byte[] state = new byte[]{
            0, 1, 2, 3,
            4, 5, 6, 7,
            8, 9, 10, 11,
            12, 13, 14, 15
        };
        byte[][] expResult = new byte[][]{
            {0, 4, 8, 12},
            {1, 5, 9, 13},
            {2, 6, 10, 14},
            {3, 7, 11, 15}
        };
        byte[][] result = ShiftRows.stateToMat(state);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of matToState method, of class ShiftRows.
     */
    @Test
    public void testMatToState() {
        System.out.println("matToState");
        byte[][] stateMat = new byte[][]{
            {0, 4, 8, 12},
            {1, 5, 9, 13},
            {2, 6, 10, 14},
            {3, 7, 11, 15}
        };

        byte[] expResult = new byte[]{
            0, 1, 2, 3,
            4, 5, 6, 7,
            8, 9, 10, 11,
            12, 13, 14, 15
        };
        byte[] result = ShiftRows.matToState(stateMat);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of swap method, of class ShiftRows.
     */
    @Test
    public void testSwap() {
        System.out.println("swap");
        byte[] arr = new byte[]{1, 2, 3, 4};
        int i = 1;
        int j = 2;
        ShiftRows.swap(arr, i, j);

        assertArrayEquals(new byte[]{1, 3, 2, 4}, arr);
    }

    /**
     * Test of shift method, of class ShiftRows.
     */
    @Test
    public void testShift() {
        System.out.println("shift");
        byte[] arr = new byte[]{3, 7, 11, 15};
        int n = 3;
        ShiftRows.shift(arr, n);

        System.out.println(Arrays.toString(arr));

        assertArrayEquals(new byte[]{15, 3, 7, 11}, arr);
    }

    /**
     * Test of rightShift method, of class ShiftRows.
     */
    @Test
    public void testRightShift() {
        System.out.println("rightShift");
        byte[] arr = new byte[]{3, 7, 11, 15};
        int n = 3;
        ShiftRows.rightShift(arr, n);

        System.out.println(Arrays.toString(arr));

        assertArrayEquals(new byte[]{7, 11, 15, 3}, arr);
    }

    /**
     * Test of shiftRows method, of class ShiftRows.
     */
    @Test
    public void testShiftRows() {
        System.out.println("shiftRows");
        byte[][] stateMat = new byte[][]{
            {0, 4, 8, 12},
            {1, 5, 9, 13},
            {2, 6, 10, 14},
            {3, 7, 11, 15}
        };

        ShiftRows.shiftRows(stateMat);

        System.out.println(Arrays.deepToString(stateMat));

        assertArrayEquals(new byte[][]{
            {0, 4, 8, 12},
            {5, 9, 13, 1},
            {10, 14, 2, 6},
            {15, 3, 7, 11}
        }, stateMat);
    }

    /**
     * Test of transform method, of class ShiftRows.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");

        byte[] state = new byte[]{
            0, 4, 8, 12,
            1, 5, 9, 13,
            2, 6, 10, 14,
            3, 7, 11, 15
        };

        // Note this is a transposed version of the typical AES implementation
        byte[] expected = new byte[]{
            0, 4, 8, 12,
            5, 9, 13, 1,
            10, 14, 2, 6,
            15, 3, 7, 11
        };
        ShiftRows instance = new ShiftRows();
        instance.transform(state);

        System.out.println(Arrays.toString(expected));
        System.out.println(Arrays.toString(state));

        assertArrayEquals(expected, state);
    }

    /**
     * Test of reverse method, of class ShiftRows.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");

        // Note this is a transposed version of the typical AES implementation
        byte[] state = new byte[]{
            0, 4, 8, 12,
            5, 9, 13, 1,
            10, 14, 2, 6,
            15, 3, 7, 11
        };

        byte[] expected = new byte[]{
            0, 4, 8, 12,
            1, 5, 9, 13,
            2, 6, 10, 14,
            3, 7, 11, 15
        };

        ShiftRows instance = new ShiftRows();
        instance.reverse(state);

        System.out.println(Arrays.toString(expected));
        System.out.println(Arrays.toString(state));

        assertArrayEquals(expected, state);
    }

}
