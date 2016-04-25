
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    static byte[] strToBa(String s) {
        if (s.length() != 32) {
            throw new IllegalArgumentException(
                    "Size of String must be exactly 32");
        }
        final byte[] copy = new byte[16];

        for (int i = 0; i < 16; i++) {
            copy[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
        }

        return copy;
    }

    static String ba2HexOutput(byte[] ba) {
        final StringBuilder sb = new StringBuilder();

        for (byte b : ba) {
            sb.append(Integer.toHexString(b & 0xFF));
        }

        return sb.toString();
    }

    public static void main(String... args) throws IOException {
        final Path input = Paths.get("input.txt");
        final Path output = Paths.get("output.txt");
        final List<String> lines = Files.readAllLines(input);

        final int[] coefficients
                = Arrays.stream(lines.get(0)
                        .split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        final Gf2Polynomial mx = new Gf2Polynomial(coefficients, 2);

        final byte[] key = strToBa(lines.get(1));
        final byte[] plaintext = strToBa(lines.get(2));
        final byte[] ciphertext = strToBa(lines.get(3));

        final Aes aes = new Aes(mx, key);

        final byte[] encrypt = aes.encrypt(plaintext);
        final byte[] decrypt = aes.decrypt(ciphertext);

        final List<String> outList = new ArrayList<>(2);

        outList.add(ba2HexOutput(encrypt));
        outList.add(ba2HexOutput(decrypt));

        Files.write(output, outList);
    }

}
