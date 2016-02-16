package edu.frostburg.cosc620;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Kevin Raoofi
 */
public class PolynomialTest {

    public PolynomialTest() {
    }

    /**
     * Test of generateFrom method, of class Polynomial.
     */
    @Test
    public void testGenerateFrom1() {
        System.out.println("generateFrom1");
        List<String> inputs = Arrays.asList("2", "-2 0 0", "2", "2 3 -4");
        List<Polynomial> expResult = Arrays.asList(
                new Polynomial(new int[]{-2, 0, 0}),
                new Polynomial(new int[]{2, 3, -4}));
        List<Polynomial> result = Polynomial.generateFrom(inputs);
        assertEquals(expResult, result);
    }

    /**
     * Test of generateFrom method, of class Polynomial.
     */
    @Test
    public void testGenerateFrom2() {
        System.out.println("generateFrom2");
        List<String> inputs = Arrays.asList("1", "5 -2", "2", "3 1 0");
        List<Polynomial> expResult = Arrays.asList(
                new Polynomial(new int[]{5, -2}),
                new Polynomial(new int[]{3, 1, 0}));
        List<Polynomial> result = Polynomial.generateFrom(inputs);
        assertEquals(expResult, result);
    }

    /**
     * Test of generateFrom method, of class Polynomial.
     */
    @Test
    public void testGenerateFrom3() {
        System.out.println("generateFrom2");
        List<String> inputs = Arrays.asList("2", "0 5 -2", "3", "0 3 1 0");
        List<Polynomial> expResult = Arrays.asList(
                new Polynomial(new int[]{5, -2}),
                new Polynomial(new int[]{3, 1, 0}));
        List<Polynomial> result = Polynomial.generateFrom(inputs);
        assertEquals(expResult, result);
    }

    /**
     * Test of firstNonZero method, of class PolynomialOrig.
     */
    @Test
    public void testFirstNonZeroNormal() {
        System.out.println("firstNonZeroNormal");
        int[] arr = new int[]{0, 0, 0, 0, 10, 2, 3, 4};
        int expResult = 4;
        int result = Polynomial.firstNonZero(arr);
        assertEquals(expResult, result);
    }

    /**
     * Test of firstNonZero method, of class PolynomialOrig.
     */
    @Test
    public void testFirstNonZeroAllZeros() {
        System.out.println("firstNonZeroAllZeros");
        int[] arr = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        int expResult = arr.length;
        int result = Polynomial.firstNonZero(arr);
        assertEquals(expResult, result);
    }

    /**
     * Test of firstNonZero method, of class PolynomialOrig.
     */
    @Test
    public void testFirstNonZeroAllNonZeros() {
        System.out.println("firstNonZeroAllNonZeros");
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        int expResult = 0;
        int result = Polynomial.firstNonZero(arr);
        assertEquals(expResult, result);
    }

    /**
     * Test of compactArray method, of class Polynomial.
     */
    @Test
    public void testCompactArray() {
        System.out.println("compactArray");
        int[] arr = new int[]{0, 0, 10, 2};
        int[] expResult = new int[]{10, 2};
        int[] result = Polynomial.compactArray(arr);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of add method, of class Polynomial.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Polynomial o = new Polynomial(new int[]{1, 2, 3});
        Polynomial instance = new Polynomial(new int[]{3, 4, 5});
        Polynomial expResult = new Polynomial(new int[]{4, 6, 8});
        Polynomial result = instance.add(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Polynomial.
     */
    @Test
    public void testMultSingle() {
        System.out.println("multSingle");
        Polynomial o = new Polynomial(new int[]{1, 2, 3});
        Polynomial instance = new Polynomial(new int[]{2});
        Polynomial expResult = new Polynomial(new int[]{2, 4, 6});
        Polynomial result = instance.mult(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of mult method, of class Polynomial.
     */
    @Test
    public void testMultUneven() {
        System.out.println("multUneven");
        Polynomial o = new Polynomial(new int[]{5, 0, 0, 6, 7});
        Polynomial instance = new Polynomial(new int[]{12, 13, 14});
        Polynomial expResult = new Polynomial(
                new int[]{60, 65, 70, 72, 162, 175, 98});
        Polynomial result = instance.mult(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of sub method, of class Polynomial.
     */
    @Test
    public void testSub() {
        System.out.println("sub");
        Polynomial instance = new Polynomial(new int[]{3, 4, 5});
        Polynomial o = new Polynomial(new int[]{1, 2, 3});
        Polynomial expResult = new Polynomial(new int[]{2, 2, 2});
        Polynomial result = instance.sub(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of invert method, of class Polynomial.
     */
    @Test
    public void testInvert() {
        System.out.println("invert");
        Polynomial instance = new Polynomial(new int[]{1, 2, 3});
        Polynomial expResult = new Polynomial(new int[]{-1, -2, -3});
        Polynomial result = instance.invert();
        assertEquals(expResult, result);
    }

    /**
     * Test of outputFormat method, of class Polynomial.
     */
    @Test
    public void testOutputFormat() {
        System.out.println("outputFormat");
        Polynomial instance = new Polynomial(new int[]{1, 2, 3});
        String expResult = "1 2 3";
        String result = instance.outputFormat();
        assertEquals(expResult, result);
    }

    /**
     * Test of polynomialForm method, of class Polynomial.
     */
    @Test
    public void testPolynomialForm() {
        System.out.println("polynomialForm");
        Polynomial instance = new Polynomial(new int[]{1, 2, 3});
        String expResult = "1x^2 + 2x^1 + 3x^0";
        String result = instance.polynomialForm();
        assertEquals(expResult, result);
    }

}
