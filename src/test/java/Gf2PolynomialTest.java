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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kevin Raoofi
 */
public class Gf2PolynomialTest {
    
    public Gf2PolynomialTest() {
    }

    /**
     * Test of EEA method, of class Gf2Polynomial.
     */
    @Test
    public void testEEA() {
        System.out.println("EEA");
        int a = 0;
        int b = 0;
        int[] expResult = null;
        int[] result = Gf2Polynomial.EEA(a, b);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of EEAP method, of class Gf2Polynomial.
     */
    @Test
    public void testEEAP() {
        System.out.println("EEAP");
        Gf2Polynomial b = null;
        Gf2Polynomial instance = null;
        Gf2Polynomial[] expResult = null;
        Gf2Polynomial[] result = instance.EEAP(b);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of firstNonZero method, of class Gf2Polynomial.
     */
    @Test
    public void testFirstNonZero() {
        System.out.println("firstNonZero");
        int[] arr = null;
        int expResult = 0;
        int result = Gf2Polynomial.firstNonZero(arr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compactArray method, of class Gf2Polynomial.
     */
    @Test
    public void testCompactArray() {
        System.out.println("compactArray");
        int[] arr = null;
        int[] expResult = null;
        int[] result = Gf2Polynomial.compactArray(arr);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertToZP method, of class Gf2Polynomial.
     */
    @Test
    public void testConvertToZP() {
        System.out.println("convertToZP");
        int[] arr = null;
        int p = 0;
        int[] expResult = null;
        int[] result = Gf2Polynomial.convertToZP(arr, p);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newZero method, of class Gf2Polynomial.
     */
    @Test
    public void testNewZero() {
        System.out.println("newZero");
        Gf2Polynomial instance = null;
        Gf2Polynomial expResult = null;
        Gf2Polynomial result = instance.newZero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Gf2Polynomial.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Gf2Polynomial o = null;
        Gf2Polynomial instance = null;
        Gf2Polynomial expResult = null;
        Gf2Polynomial result = instance.add(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mult method, of class Gf2Polynomial.
     */
    @Test
    public void testMult() {
        System.out.println("mult");
        Gf2Polynomial o = null;
        Gf2Polynomial instance = null;
        Gf2Polynomial expResult = null;
        Gf2Polynomial result = instance.mult(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sub method, of class Gf2Polynomial.
     */
    @Test
    public void testSub() {
        System.out.println("sub");
        Gf2Polynomial o = null;
        Gf2Polynomial instance = null;
        Gf2Polynomial expResult = null;
        Gf2Polynomial result = instance.sub(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of div method, of class Gf2Polynomial.
     */
    @Test
    public void testDiv() {
        System.out.println("div");
        Gf2Polynomial o = null;
        Gf2Polynomial instance = null;
        Gf2Polynomial expResult = null;
        Gf2Polynomial result = instance.div(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lead method, of class Gf2Polynomial.
     */
    @Test
    public void testLead() {
        System.out.println("lead");
        Gf2Polynomial instance = null;
        Gf2Polynomial expResult = null;
        Gf2Polynomial result = instance.lead();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDegree method, of class Gf2Polynomial.
     */
    @Test
    public void testGetDegree() {
        System.out.println("getDegree");
        Gf2Polynomial instance = null;
        int expResult = 0;
        int result = instance.getDegree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isZero method, of class Gf2Polynomial.
     */
    @Test
    public void testIsZero() {
        System.out.println("isZero");
        Gf2Polynomial instance = null;
        boolean expResult = false;
        boolean result = instance.isZero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of negate method, of class Gf2Polynomial.
     */
    @Test
    public void testNegate() {
        System.out.println("negate");
        Gf2Polynomial instance = null;
        Gf2Polynomial expResult = null;
        Gf2Polynomial result = instance.negate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Gf2Polynomial.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Gf2Polynomial instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Gf2Polynomial.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Gf2Polynomial instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Gf2Polynomial.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Gf2Polynomial instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
