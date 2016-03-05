
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
public class GF1 {

    /**
     *
     * @param args ignored
     * @throws IOException if input.txt or output.txt cannot be, respectively,
     * read or written to.
     */
    public static void main(String... args) throws IOException {
        final Path input = Paths.get("input.txt");
        final Path output = Paths.get("output.txt");

        final Scanner sc = new Scanner(input);
        final int p = sc.nextInt();
        final int x = sc.nextInt();
        final int y = sc.nextInt();

        final FiniteField1 gf = new FiniteField1(p);
        final List<String> results = new ArrayList<>();

        results.add(Integer.toString(gf.add(x, y)));
        results.add(Integer.toString(gf.sub(x, y)));
        results.add(Integer.toString(gf.mult(x, y)));
        results.add(Integer.toString(gf.div(x, y)));

        Files.write(output, results);

    }
}

/**
 * Finite field algorithm class
 *
 * @author Kevin Raoofi
 */
class FiniteField1 {

    /**
     * Algorithm EEA (Extended Euclidean Algorithm)
     *
     * Obtained from project handout
     *
     * @param a an (> 0) integer
     * @param b another (>= 0) integer
     *
     * @return int array (u,v) satisfying u*a + v*b = gcd(a,b)
     */
    public static int[] EEA(int a, int b) {
        if (b == 0) {
            return new int[]{1, 0};
        } else {
            int q = a / b;
            int r = a % b;
            int[] R = EEA(b, r);
            return new int[]{R[1], R[0] - q * R[1]};
        }
    }

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

    /** Prime number */
    private final int p;

    /**
     * Checks to see that x and y are both in Z_p
     *
     * @param x first operand
     * @param y second operand
     */
    private void checkInputs(int x, int y) {
        if (x >= p || y >= p) {
            throw new IllegalArgumentException(
                    "x and y must belong to Z_p; or must be smaller than p");
        }
    }

    /**
     * Creates finite field with prime number, p
     *
     * If p isn't a prime number, this method will not fail; it assumes it's OK
     *
     * @param p prime number
     */
    public FiniteField1(int p) {
        this.p = p;
    }

    /**
     * Adds two numbers together and puts it into Z_p
     *
     * @param x operand1
     * @param y operand1
     * @return sum in Z_p
     */
    public int add(int x, int y) {
        checkInputs(x, y);
        return mod((x + y), p);
    }

    /**
     * Adds two numbers together and puts it into Z_p
     *
     * @param x operand1
     * @param y operand1
     * @return diff in Z_p
     */
    public int sub(int x, int y) {
        checkInputs(x, y);
        return mod((x - y), p);
    }

    /**
     * Multiplies two numbers together and puts it into Z_p
     *
     * @param x operand1
     * @param y operand1
     * @return product in Z_p
     */
    public int mult(int x, int y) {
        checkInputs(x, y);
        return mod((x * y), p);
    }

    /**
     * Divides the numbers by multiplying with the reverse modulo
     *
     * @param x operand1
     * @param y operand1
     * @return div in Z_p
     */
    public int div(int x, int y) {
        checkInputs(x, y);

        final int[] eea = EEA(y, p);

        return mod((x * eea[0]), p);
    }

}
