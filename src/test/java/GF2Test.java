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

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Kevin Raoofi
 */
public class GF2Test {
    
    public GF2Test() {
    }

    /**
     * Test of generateArrFrom method, of class GF2.
     */
    @Test
    public void testGenerateArrFrom() {
        System.out.println("generateArrFrom");
        String line = "";
        int[] expResult = null;
        int[] result = GF2.generateArrFrom(line);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateFrom method, of class GF2.
     */
    @Test
    public void testGenerateFrom() {
        System.out.println("generateFrom");
        List<String> inputs = null;
        List<Gf2Polynomial> expResult = null;
        List<Gf2Polynomial> result = GF2.generateFrom(inputs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
