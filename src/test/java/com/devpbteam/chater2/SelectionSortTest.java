package com.devpbteam.chater2;

import org.junit.Test;
import static com.devpbteam.chapter2.SelectionSortRecursive.selectionSort;

public class SelectionSortTest {

    @Test
    public void testSelectionSort() {
        int[] arr = new int[]{5, 3, 2, 1, 6};
        selectionSort(arr, 0);
        System.out.println(arr);
    }
}
