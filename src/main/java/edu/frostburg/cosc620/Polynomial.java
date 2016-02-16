package edu.frostburg.cosc620;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is the main polynomial class that provides the add, subtract, and
 * multiply functions as well as the static functions to read the polynomials
 * from the input file
 *
 * @author Seyed Kevin Raoofi
 */
class Polynomial {

    /**
     * Reads the lines in the input file and generates an ordered list of the
     * polynomials in the input
     *
     * @param inputs lines in the input file
     * @return a list of polynomials defined in the file
     */
    public static List<Polynomial> generateFrom(final List<String> inputs) {
        if (inputs.size() % 2 != 0) {
            throw new IllegalArgumentException("Size of inputs must be even");
        }

        // 1. get ever odd line to get every coefficients list
        // 2. coefficients are split on whitespace
        // 3. all strings in the split is parsed as an integer
        // 4. construct a polynomial using that array of ints
        // 5. collect polynomials as List and return
        return IntStream.range(0, inputs.size())
                .filter(i -> i % 2 != 0)
                .mapToObj(i -> inputs.get(i))
                .map(s -> s.split(" "))
                .map(sa -> Arrays.stream(sa)
                        .mapToInt(Integer::parseInt)
                        .toArray()
                )
                .map(Polynomial::new)
                .collect(Collectors.toList());
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

    /** Coefficients in polynomial */
    private final int[] coefficients;

    /**
     * Creates a Polynomial instance with the given coefficients. The degree is
     * implied by the position of the coefficient in reverse order. e.g.,
     *
     * {@code [1, 2, 3]} is considered {@code x^2 + 2x + 3}
     *
     * @param coefficients array of coefficients
     */
    public Polynomial(int[] coefficients) {
        this.coefficients = compactArray(coefficients);
    }

    /**
     * Adds 2 polynomials together
     *
     * @param o the polynomial to add
     * @return a new polynomial with the sum
     */
    public Polynomial add(Polynomial o) {
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

        return new Polynomial(results);
    }

    /**
     * Multiplies 2 polynomials together
     *
     * @param o the polynomial to multiply
     * @return a new polynomial with the product
     */
    public Polynomial mult(Polynomial o) {
        final int resultSize = this.coefficients.length
                + o.coefficients.length - 1;

        if (resultSize < 0) {
            return this;
        }

        final int[] results = new int[resultSize];

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < o.coefficients.length; j++) {
                results[i + j] += this.coefficients[i] * o.coefficients[j];
            }
        }

        return new Polynomial(results);
    }

    /**
     * Subtracts the other polynomial from this one. e.g.,
     * {@code thisPolynomial - otherPolynomial}
     *
     * @param o other polynomial to subtract from this polynomial
     * @return a new polynomial with the difference
     */
    public Polynomial sub(Polynomial o) {
        return this.add(o.invert());
    }

    /**
     * Inverts the sign of all coefficients in this polynomial
     *
     * @return a new polynomial with inverted signs
     */
    public Polynomial invert() {
        final int[] copy = Arrays.copyOf(this.coefficients,
                this.coefficients.length);

        for (int i = 0; i < copy.length; i++) {
            copy[i] = -copy[i];
        }

        return new Polynomial(copy);
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

    /**
     * Displays the polynomial with an attempt to write it in math-like form.
     * So, {@code [1, 2, 3]} will be displayed as {@code 1x^2 + 2x^1 + 3x^0}
     *
     * @return string with polynomial in math-like form
     */
    public String polynomialForm() {
        final StringJoiner sj = new StringJoiner(" + ");
        for (int i = 0; i < this.coefficients.length; i++) {
            sj.add(
                    this.coefficients[i]
                    + "x^"
                    + (this.coefficients.length - i - 1));
        }

        return sj.toString();
    }

    @Override
    public String toString() {
        return "Polynomial{" + "coefficients=" + Arrays.toString(
                coefficients)
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Arrays.hashCode(this.coefficients);
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
        final Polynomial other = (Polynomial) obj;
        if (!Arrays.equals(this.coefficients, other.coefficients)) {
            return false;
        }
        return true;
    }
}
