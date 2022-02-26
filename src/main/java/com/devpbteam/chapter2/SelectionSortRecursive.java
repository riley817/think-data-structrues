package com.devpbteam.chapter2;

public class SelectionSortRecursive {

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void selectionSort(int[] arr, int startIdx) {
        if(startIdx < arr.length) {
            for(int i = startIdx; i < arr.length; i++) {
                if(arr[i] < arr[startIdx]) {
                    swap(arr, startIdx, i);
                }
            }
            selectionSort(arr, startIdx + 1);
        }
    }

}
