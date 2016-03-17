
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
public class GF2 {

    static int[] generateArrFrom(String line) {
        return Arrays.stream(line.split("\\s"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    /**
     * Reads the lines in the input file and generates an ordered list of the
     * polynomials in the input
     *
     * @param inputs lines in the input file
     * @return a list of polynomials defined in the file
     */
    static List<Gf2Polynomial> generateFrom(final List<String> inputs) {
        final int p = Integer.parseInt(inputs.get(0));

        final List<String> polyList = inputs.subList(3, inputs.size());

        // 1. get every odd line to get every coefficients list
        // 2. coefficients are split on whitespace
        // 3. all strings in the split is parsed as an integer
        // 4. construct a polynomial using that array of ints
        // 5. collect polynomials as List and return
        return IntStream.range(0, polyList.size())
                .filter(i -> i % 2 != 0)
                .mapToObj(i -> polyList.get(i))
                .map(s -> s.split("\\s"))
                .map(sa -> Arrays.stream(sa)
                        .mapToInt(Integer::parseInt)
                        .toArray()
                )
                .map(ia -> new Gf2Polynomial(ia, p))
                .collect(Collectors.toList());
    }

    public static void main(String... args) throws IOException {
        final Path input = Paths.get("input.txt");
        final Path output = Paths.get("output.txt");

        final List<String> inputs = Files.readAllLines(input);
        final List<Gf2Polynomial> polies = generateFrom(inputs);

        final int p = Integer.parseInt(inputs.get(0));
        final int[] mpoly = generateArrFrom(inputs.get(2));

        final Gf2PolynomialMPolyFacade facade
                = new Gf2PolynomialMPolyFacade(p, mpoly);

        Files.write(output, Arrays.<BinaryOperator<Gf2Polynomial>>asList(
                facade::add, facade::sub, facade::mult, facade::div)
                .stream()
                .map(reducer -> polies.stream()
                        .reduce(reducer)
                        .map(Gf2Polynomial::outputFormat)
                        .get()
                )
                .collect(Collectors.toList())
        );
    }
}

/**
 * Facade pattern to implement an object which acts as a suite of reducers
 *
 * @author Kevin Raoofi
 */
class Gf2PolynomialMPolyFacade {

    /** mpoly; keeps track of this for mult and div */
    private final Gf2Polynomial mpoly;

    /**
     * Creates a Gf2PolynomialMPolyFacade with given p and mpoly
     *
     * @param p prime number
     * @param mpoly m polynomial
     */
    public Gf2PolynomialMPolyFacade(int p, int[] mpoly) {
        this(new Gf2Polynomial(mpoly, p));
    }

    /**
     * Creates a Gf2PolynomialMPolyFacade with the given polynomial object
     *
     * @param mpoly m polynomial as a Gf2Polynomial object
     */
    public Gf2PolynomialMPolyFacade(Gf2Polynomial mpoly) {
        this.mpoly = mpoly;
    }

    /**
     * Adds two polynomials
     *
     * @param o1 first poly
     * @param o2 second poly
     * @return sum poly
     */
    public Gf2Polynomial add(Gf2Polynomial o1, Gf2Polynomial o2) {
        return o1.add(o2);
    }

    /**
     * subtracts two polynomials
     *
     * @param o1 first poly
     * @param o2 second poly
     * @return diff poly
     */
    public Gf2Polynomial sub(Gf2Polynomial o1, Gf2Polynomial o2) {
        return o1.sub(o2);
    }

    /**
     * multiplies two polynomials
     *
     * @param o1 first poly
     * @param o2 second poly
     * @return product poly
     */
    public Gf2Polynomial mult(Gf2Polynomial o1, Gf2Polynomial o2) {
        return o1.mult(o2, mpoly);
    }

    /**
     * divides two polynomials
     *
     * @param o1 first poly
     * @param o2 second poly
     * @return dividend poly
     */
    public Gf2Polynomial div(Gf2Polynomial o1, Gf2Polynomial o2) {
        return o1.div(o2, mpoly);
    }

}

/**
 * This is the main polynomial class that provides the add, subtract, and
 * multiply functions as well as the static functions to read the polynomials
 * from the input file
 *
 * @author Seyed Kevin Raoofi
 */
class Gf2Polynomial {

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
    static int[] EEA(int a, int b) {
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
     * Finds the first non-zero index
     *
     * @param arr array to look through
     * @return index of first non-zero element
     */
    static int firstNonZero(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                return i;
            }
        }
        return arr.length;
    }

    /**
     * Mathematical mod implementation
     *
     * @param x the dividend
     * @param y the divisor
     * @return the modulo result of x and y
     */
    static int mod(int x, int y) {
        return ((x % y) + y) % y;
    }

    /**
     * Values in the array are converted to Z_p and compacted
     *
     * @param arr array to normalize
     * @param p prime number
     * @return normalized copy of arr
     */
    static int[] normalize(int[] arr, int p) {
        return compactArray(convertToZP(arr, p));
    }

    /**
     * Returns a copy of the array starting from its first non-zero element to
     * the end
     *
     * @param arr array to compact
     * @return copy of the array starting from its first non-zero element to the
     * end
     */
    static int[] compactArray(int[] arr) {
        final int minPos = firstNonZero(arr);
        return Arrays.copyOfRange(arr, minPos, arr.length);
    }

    /**
     * Maps integers to Z_p by applying mathematical mod to them
     *
     * @param arr array to map
     * @param p prime number
     * @return a copy of arr with all values modded
     */
    static int[] convertToZP(int[] arr, int p) {
        return Arrays.stream(arr)
                .map(i -> mod(i, p))
                .toArray();
    }

    /** Coefficients in polynomial */
    private final int[] coefficients;

    /** Prime number, p */
    private final int p;

    /**
     * Creates a Polynomial instance with the given coefficients. The degree is
     * implied by the position of the coefficient in reverse order. e.g.,
     *
     * {@code [1, 2, 3]} is considered {@code x^2 + 2x + 3}
     *
     * @param coefficients array of coefficients
     * @param mpoly the irreducible polynomial over Z_p
     * @param p Prime number
     */
    public Gf2Polynomial(int[] coefficients, int p) {

        this.coefficients = normalize(coefficients, p);
        this.p = p;
    }

    /**
     * Creates a polynomial with a single coefficient at a specific degree.
     *
     * {@code new Gf2Polynomial(1,0,p).add(new Gf2Polynomial(1,1,p))} is the
     * same as @{code new Gf2Polynomial(new int[]{1,1}, p)}
     *
     * @param coefficient a single coefficient
     * @param degree the degree of the coefficient
     * @param p Prime number
     */
    public Gf2Polynomial(int coefficient, int degree, int p) {
        final int[] tmpco = new int[degree + 1];
        tmpco[0] = coefficient;

        this.coefficients = normalize(tmpco, p);
        this.p = p;
    }

    /**
     * EEAP (Extended Euclidean Algorithm for Polynomials) over Z_p
     *
     * @param b another polynomial over Z_p
     *
     * @return polynomials u(x) and v(x) satisfying
     * {@code u(x)*a(x) + v(x)*b(x) = gcd(a(x),b(x))}
     *
     */
    public Gf2Polynomial[] EEAP(Gf2Polynomial b) {
        if (b.isZero()) {
            final int resC = gf1div(1, this.coefficients[0]);

            Gf2Polynomial result = new Gf2Polynomial(resC, this.getDegree(), p);

            return new Gf2Polynomial[]{result, this.newZero()};
        }

        // i'm just copying the pseudocode; forgive me
        final Gf2Polynomial[] Q = this.plda(b);

        final Gf2Polynomial q = Q[0];
        final Gf2Polynomial r = Q[1];

        final Gf2Polynomial[] R = b.EEAP(r);

        final Gf2Polynomial u = R[1];

        final Gf2Polynomial v = R[0].sub(q.naiveMult(R[1]));

        return new Gf2Polynomial[]{u, v};
    }

    /**
     * Divides the numbers by multiplying with the reverse modulo
     *
     * @param x operand1
     * @param y operand1
     * @return div in Z_p
     */
    private int gf1div(int x, int y) {
        final int[] eea = EEA(y, this.p);

        return mod((x * eea[0]), this.p);
    }

    /**
     * PLDA (Polynomial Long Division Algorithm) over Z_p
     *
     * @param d a non-zero polynomial over Z_p
     *
     * @return quotient q(x) and remainder r(x) such that n(x) = q(x)*d(x) +
     * r(x) where deg(r(x)) < deg(d(x))
     */
    private Gf2Polynomial[] plda(Gf2Polynomial d) {
        // Zero polynomial
        Gf2Polynomial q = this.newZero();
        Gf2Polynomial r = this;

        // this algorithm looks at internal structure...
        while (!r.isZero() && r.getDegree() >= d.getDegree()) {

            //System.out.println("r: " + r + " d: " + d);
            final int tCoefficient
                    = gf1div(r.coefficients[0], d.coefficients[0]);

            final Gf2Polynomial t = new Gf2Polynomial(tCoefficient,
                    r.getDegree() - d.getDegree(), p);

            q = q.add(t);
            r = r.sub(t.naiveMult(d));
        }

        return new Gf2Polynomial[]{q, r};
    }

    /**
     * Creates a zero polynomial with the same p value as the current polynomial
     *
     * @return zero polynomial with the same p
     */
    public Gf2Polynomial newZero() {
        return new Gf2Polynomial(new int[]{}, p);
    }

    /**
     * Adds 2 polynomials together
     *
     * @param o the polynomial to add
     * @return a new polynomial with the sum
     */
    public Gf2Polynomial add(Gf2Polynomial o) {
        final int[] larger;
        final int[] smaller;

        if (this.coefficients.length > o.coefficients.length) {
            larger = this.coefficients;
            smaller = o.coefficients;
        } else {
            larger = o.coefficients;
            smaller = this.coefficients;
        }

        final int[] results = Arrays.copyOf(larger, larger.length);

        for (int i = 0; i < smaller.length; i++) {
            results[results.length - 1 - i] += smaller[smaller.length - 1
                    - i];
        }

        final int[] resultsP = convertToZP(results, this.p);

        return new Gf2Polynomial(resultsP, this.p);
    }

    /**
     * Naive multiplication is multiplication that does not produce a polynomial
     * within GF(P^M). All the coefficients, however, will be in Z_P. This is
     * used as an intermediary step for multiplication.
     *
     * @param o other polynomial to multiply
     * @return naive multiplication product, C'
     */
    private Gf2Polynomial naiveMult(Gf2Polynomial o) {
        final int resultSize = this.coefficients.length
                + o.coefficients.length - 1;

        if (this.isZero() || o.isZero()) {
            return newZero();
        }

        // i don't remember why i do this; it doesn't make any sense; test
        if (resultSize < 0) {
            return this;
        }

        final int[] results = new int[resultSize];

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < o.coefficients.length; j++) {
                results[i + j] += this.coefficients[i] * o.coefficients[j];
            }
        }
        final int[] resultsP = convertToZP(results, this.p);

        return new Gf2Polynomial(resultsP, this.p);
    }

    /**
     * Multiplies 2 polynomials together
     *
     * @param o the polynomial to multiply
     * @return a new polynomial with the product
     */
    public Gf2Polynomial mult(Gf2Polynomial o, Gf2Polynomial mpoly) {
        // intermediary form; possibly not in field
        final Gf2Polynomial cPrime = this.naiveMult(o);

        return cPrime.plda(new Gf2Polynomial(mpoly.coefficients, p))[1];
    }

    /**
     * Subtracts the other polynomial from this one. e.g.,
     * {@code thisPolynomial - otherPolynomial}
     *
     * @param o other polynomial to subtract from this polynomial
     * @return a new polynomial with the difference
     */
    public Gf2Polynomial sub(Gf2Polynomial o) {
        return this.add(o.negate());
    }

    /**
     * Division of polynomials over the m polynomial
     *
     * @param o other polynomial divisor
     * @param mpoly the m polynomial
     * @return the dividend polynomial
     */
    public Gf2Polynomial div(Gf2Polynomial o, Gf2Polynomial mpoly) {
        final Gf2Polynomial[] eeap = new Gf2Polynomial(mpoly.coefficients, p)
                .EEAP(o);

        return this.mult(eeap[1], mpoly);
    }

    /**
     * Returns the polynomial containing only the leading coefficient term. [1,
     * 2, 3] returns [1, 0, 0].
     *
     *
     * @return polynomial containing only the leading coefficient term
     */
    public Gf2Polynomial lead() {
        if (this.isZero()) {
            return this;
        }

        return new Gf2Polynomial(this.coefficients[0], this.getDegree(), p);
    }

    /**
     * Highest degree in the coefficient. If the polynomial is a zero
     * polynomial, returns -1 as the degree.
     *
     * @return highest degree
     */
    public int getDegree() {
        if (this.isZero()) {
            return -1;
        }

        return this.coefficients.length - 1;
    }

    /**
     * Determines if the polynomial is a zero polynomial
     *
     * @return true is zero polynomial, false otherwise
     */
    public boolean isZero() {
        return this.coefficients.length == 0;
    }

    /**
     * Negates the sign of all coefficients in this polynomial
     *
     * @return a new polynomial with inverted signs
     */
    public Gf2Polynomial negate() {
        final int[] copy = Arrays.copyOf(this.coefficients,
                this.coefficients.length);

        for (int i = 0; i < copy.length; i++) {
            copy[i] = -copy[i];
        }

        final int[] copyP = convertToZP(copy, p);

        return new Gf2Polynomial(copyP, this.p);
    }

    /**
     * String representation with coefficients separated via spaces. This is the
     * format specified for the output file per polynomial
     *
     * @return string with coefficients separated via spaces
     */
    public String outputFormat() {
        final StringJoiner sj = new StringJoiner(" ");

        for (int i : this.coefficients) {
            sj.add(Integer.toString(i));
        }

        return sj.toString();
    }

    @Override
    public String toString() {
        return "Gf2Polynomial{" + "coefficients=" + Arrays
                .toString(coefficients) + ", p=" + p
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Arrays.hashCode(this.coefficients);
        hash = 67 * hash + this.p;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gf2Polynomial other = (Gf2Polynomial) obj;
        if (!Arrays.equals(this.coefficients, other.coefficients)) {
            return false;
        }
        if (this.p != other.p) {
            return false;
        }
        return true;
    }

}
