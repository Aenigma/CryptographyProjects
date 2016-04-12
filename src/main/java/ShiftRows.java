

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
public class ShiftRows {

    static byte[][] stateToMat(byte[] state) {
        if (state.length != 16) {
            throw new IllegalArgumentException("Length of state must be 16");
        }
        final byte[][] stateMat = new byte[4][4];

        for (int i = 0; i < state.length; i++) {
            stateMat[i % 4][i / 4] = state[i];
        }

        return stateMat;
    }

    static byte[] matToState(byte[][] stateMat) {
        final byte[] state = new byte[16];

        for (int i = 0; i < state.length; i++) {
            state[i] = stateMat[i % 4][i / 4];
        }
        return state;
    }

    static void swap(byte[] arr, int i, int j) {
        final byte tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }

    static void shift(byte[] arr, int n) {
        n = n % arr.length;

        for (int shiftNum = 0; shiftNum < n; shiftNum++) {
            final byte tmp = arr[0];
            for (int i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[(i + 1) % arr.length];
            }

            arr[arr.length - 1] = tmp;
        }
    }

    static void rightShift(byte[] arr, int n) {
        shift(arr, (-n % arr.length) + arr.length);
    }

    static void shiftRows(byte[][] stateMat) {
        for (int i = 0; i < stateMat.length; i++) {
            shift(stateMat[i], i);
        }
    }

    static void rightShiftRows(byte[][] stateMat) {
        for (int i = 0; i < stateMat.length; i++) {
            rightShift(stateMat[i], i);
        }
    }

    public void transform(byte[] state) {
        final byte[][] mat = stateToMat(state);
        Utils.transpose(mat);
        shiftRows(mat);
        Utils.transpose(mat);
        final byte[] nState = matToState(mat);

        System.arraycopy(nState, 0, state, 0, state.length);
    }

    public void reverse(byte[] state) {
        final byte[][] mat = stateToMat(state);
        Utils.transpose(mat);
        rightShiftRows(mat);
        Utils.transpose(mat);
        final byte[] nState = matToState(mat);

        System.arraycopy(nState, 0, state, 0, state.length);
    }
}
