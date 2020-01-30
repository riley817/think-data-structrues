package io.github.tlor.chapter10;

import java.util.Arrays;

public class SillyArray {

    private final char[] array;

    public SillyArray(char[] array) {
        this.array = array;
    }

    public void set(int i, char c) {
        this.array[i] = c;
    }


    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        int total = 0;
        for(int i = 0; i < array.length; i++) {
            total += array[i];
        }
        System.out.println(total);
        return total;
    }
}
