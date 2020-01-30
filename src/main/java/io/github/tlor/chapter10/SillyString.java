package io.github.tlor.chapter10;

import java.util.Map;

public class SillyString {

    private final String innerString;

    public SillyString(String innerString) {
        this.innerString = innerString;
    }

    @Override
    public String toString() {
        return innerString;
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        int total = 0;
        for (int i = 0; i < innerString.length(); i++) {
            total += innerString.charAt(i);
        }
        return total;
    }

    public static void main(String[] args) {
        Map<SillyString, Integer> map = new MyBetterMap<SillyString, Integer>();

        map.put(new SillyString("Word1"), 1);
        map.put(new SillyString("Word2"), 2);
        Integer value = map.get(new SillyString("Word1"));
        System.out.println(value);

        for(SillyString key : map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }


}

