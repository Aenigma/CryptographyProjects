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
package edu.frostburg.cosc620;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * @author Kevin Raoofi
 */
public class Playfair {

    private static final SortedSet<Character> alphaSet;

    static {
        alphaSet = new TreeSet<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') {
                // skip J; J = I here
                continue;
            }
            alphaSet.add(c);
        }
    }

    private final char[] code = new char[25];

    public Playfair(String key) {
        final LinkedHashSet<Character> kset = new LinkedHashSet<>(25);
        final SortedSet<Character> alphaRemainder = new TreeSet<>(alphaSet);
        final List<Character> codeList = new ArrayList<>();

        for (char c : key.toUpperCase()
                .replaceAll("J", "I")
                .toCharArray()) {
            kset.add(c);
        }
        alphaRemainder.removeAll(kset);

        System.out.println(kset);
        System.out.println(alphaRemainder);

        codeList.addAll(kset);
        codeList.addAll(alphaRemainder);

        for (int i = 0; i < code.length; i++) {
            code[i] = codeList.get(i);
        }
    }

    public String getCode() {
        final StringJoiner sj = new StringJoiner("\n", "[", "]");

        for (int i = 0; i < 5; i++) {
            final StringJoiner sj2 = new StringJoiner(", ", "[", "]");
            for (int j = 0; j < 5; j++) {
                sj2.add(Character.toString(code[5 * i + j]));
            }
            sj.add(sj2.toString());
        }

        return sj.toString();
    }

    void splitWords(String word) {
        final Queue<String> charStack = new ArrayDeque<>(Arrays
                .stream(word.split(""))
                .collect(Collectors.toList()));
        final Queue<String[]> splitQueue = new ArrayDeque<>();
        while (charStack.peek() != null) {
            final String first = charStack.poll();
            String second = charStack.peek();

            if (first.equals(second)) {
                second = "X";
            }

            splitQueue.add(new String[]{first, second});
        }

        System.out.println(splitQueue);
    }

    public static void main(String... args) {
        Playfair pf = new Playfair("monarchy");

        System.out.println(pf.getCode());
        pf.splitWords("balloon");
    }
}
