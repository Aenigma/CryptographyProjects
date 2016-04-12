
import java.math.BigInteger;
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
public class Aes {

    static final Gf2Polynomial RIJNDAEL_MX = new Gf2Polynomial(
            new int[]{1, 0, 0, 0, 1, 1, 0, 1, 1}, 2);

    private static final byte[] NIST_SAMPLE_KEY = new BigInteger(
            "2b7e151628aed2a6abf7158809cf4f3c", 16)
            .toByteArray();

    private final SubBytes subBytes;
    private final ShiftRows shiftRows;
    private final MixColumns mixColumns;
    private final RoundKey roundKey;

    public Aes(Gf2Polynomial mx, byte[] key) {
        this.subBytes = new SubBytes(mx);
        this.shiftRows = new ShiftRows();
        this.mixColumns = new MixColumns(mx);
        this.roundKey = new RoundKey(key, subBytes, mx);
    }

    public byte[] encrypt(byte[] plaintext) {
        final byte[] state = new byte[plaintext.length];

        {
            final byte[][] mat = Utils.stateToMat(plaintext);
            Utils.transpose(mat);
            Utils.serialize(mat, state);
        }

        roundKey.addRoundKey(state, 0);

        for (int i = 1; i < 10; i++) {
            subBytes.transform(state);
            shiftRows.transform(state);
            mixColumns.transform(state);
            roundKey.addRoundKey(state, i);

        }

        subBytes.transform(state);
        shiftRows.transform(state);
        roundKey.addRoundKey(state, 10);

        {
            final byte[][] mat = Utils.stateToMat(state);
            Utils.transpose(mat);
            Utils.serialize(mat, state);
        }

        return state;
    }

    public byte[] decrypt(byte[] cipher) {
        final byte[] state = new byte[cipher.length];

        {
            final byte[][] mat = Utils.stateToMat(cipher);
            Utils.transpose(mat);
            Utils.serialize(mat, state);
        }

        roundKey.addRoundKey(state, 10);

        for (int i = 9; i > 0; i--) {
            shiftRows.reverse(state);
            subBytes.reverse(state);
            roundKey.addRoundKey(state, i);
            mixColumns.reverse(state);

        }
        shiftRows.reverse(state);
        subBytes.reverse(state);
        roundKey.addRoundKey(state, 0);

        {
            final byte[][] mat = Utils.stateToMat(state);
            Utils.transpose(mat);
            Utils.serialize(mat, state);
        }

        return state;
    }

    public static void main(String... args) {
        Aes aes = new Aes(RIJNDAEL_MX, NIST_SAMPLE_KEY);
        byte[] plain
                = new BigInteger("3243f6a8885a308d313198a2e0370734", 16)
                .toByteArray();

        byte[] encrypt = aes.encrypt(plain);

        byte[] decrypt = aes.decrypt(encrypt);

        System.out.println(RoundKey.ba2Hex(encrypt));
        System.out.println(RoundKey.ba2Hex(decrypt));
    }

}
