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
import java.util.StringJoiner;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Kevin Raoofi
 */
public class AesRoundTest {

    private final SubBytes subBytes;
    private final ShiftRows shiftRows;
    private final MixColumns mixColumns;
    private final RoundKey roundKey;

    public AesRoundTest() {
        this.subBytes = new SubBytes(Aes.RIJNDAEL_MX);
        this.shiftRows = new ShiftRows();
        this.mixColumns = new MixColumns(Aes.RIJNDAEL_MX);
        this.roundKey = new RoundKey();
    }

    static void displayState(byte[] state) {
        StringJoiner top = new StringJoiner(" ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                top.add(Integer.toHexString(state[4 * i + j] & 0xFF));
            }
            System.out.println(top);
            top = new StringJoiner(" ");
        }
        System.out.println();
    }

    @Test
    public void subBytes() {
        System.out.println("subBytes");
        final byte[] before = new byte[]{
            (byte) 0x19, (byte) 0xa0, (byte) 0x9a, (byte) 0xe9,
            (byte) 0x3d, (byte) 0xf4, (byte) 0xc6, (byte) 0xf8,
            (byte) 0xe3, (byte) 0xe2, (byte) 0x8d, (byte) 0x48,
            (byte) 0xbe, (byte) 0x2b, (byte) 0x2a, (byte) 0x08
        };

        final byte[] after = new byte[]{
            (byte) 0xd4, (byte) 0xe0, (byte) 0xb8, (byte) 0x1e,
            (byte) 0x27, (byte) 0xbf, (byte) 0xb4, (byte) 0x41,
            (byte) 0x11, (byte) 0x98, (byte) 0x5d, (byte) 0x52,
            (byte) 0xae, (byte) 0xf1, (byte) 0xe5, (byte) 0x30
        };

        subBytes.transform(before);

        System.out.println(Arrays.toString(before));
        System.out.println(Arrays.toString(after));

        assertArrayEquals(after, before);
    }

    @Test
    public void shiftRows() {
        System.out.println("shiftRows");
        final byte[] before = new byte[]{
            (byte) 0xd4, (byte) 0xe0, (byte) 0xb8, (byte) 0x1e,
            (byte) 0x27, (byte) 0xbf, (byte) 0xb4, (byte) 0x41,
            (byte) 0x11, (byte) 0x98, (byte) 0x5d, (byte) 0x52,
            (byte) 0xae, (byte) 0xf1, (byte) 0xe5, (byte) 0x30
        };

        final byte[] after = new byte[]{
            (byte) 0xd4, (byte) 0xe0, (byte) 0xb8, (byte) 0x1e,
            (byte) 0xbf, (byte) 0xb4, (byte) 0x41, (byte) 0x27,
            (byte) 0x5d, (byte) 0x52, (byte) 0x11, (byte) 0x98,
            (byte) 0x30, (byte) 0xae, (byte) 0xf1, (byte) 0xe5
        };

        shiftRows.transform(before);

        displayState(after);
        displayState(before);

        assertArrayEquals(after, before);
    }

    @Test
    public void mixColumns() {
        System.out.println("mixColumns");
        final byte[] before = new byte[]{
            (byte) 0xd4, (byte) 0xe0, (byte) 0xb8, (byte) 0x1e,
            (byte) 0xbf, (byte) 0xb4, (byte) 0x41, (byte) 0x27,
            (byte) 0x5d, (byte) 0x52, (byte) 0x11, (byte) 0x98,
            (byte) 0x30, (byte) 0xae, (byte) 0xf1, (byte) 0xe5
        };

        final byte[] before2 = new byte[]{
            (byte) 0xd4, (byte) 0xe0, (byte) 0xb8, (byte) 0x1e,
            (byte) 0xbf, (byte) 0xb4, (byte) 0x41, (byte) 0x27,
            (byte) 0x5d, (byte) 0x52, (byte) 0x11, (byte) 0x98,
            (byte) 0x30, (byte) 0xae, (byte) 0xf1, (byte) 0xe5
        };

        final byte[] after = new byte[]{
            (byte) 0x04, (byte) 0xe0, (byte) 0x48, (byte) 0x28,
            (byte) 0x66, (byte) 0xcb, (byte) 0xf8, (byte) 0x06,
            (byte) 0x81, (byte) 0x19, (byte) 0xd3, (byte) 0x26,
            (byte) 0xe5, (byte) 0x9a, (byte) 0x7a, (byte) 0x4c
        };

        mixColumns.transform(before);

        displayState(before);
        displayState(after);

        assertArrayEquals(after, before);

        mixColumns.reverse(before);

        assertArrayEquals(before2, before);
    }
}
