package com.devpbteam.chapter2;

public class SelectionSortRecursive {

    /**
     * i와 j의 위치값을 바꾼다.
     */
    public static void swapElements(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * start로 부터 시작하는 최소값의 위차를 찾고(start 포함)
     * 배열의 마지막 위치로 갑니다.
     */
    public static int indexLowest(int[] arr, int start) {
        int lowIndex = start;
        for(int i = start; i < arr.length; i++) {
            if(arr[i] < arr[lowIndex]) {
                lowIndex = i;
            }
        }
        return lowIndex;
    }

    /**
     * 선택 정렬을 사용하여 요소를 정렬합니다.
     */
    public static void selectionSort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            int j = indexLowest(arr, i);
            swapElements(arr, i, j);
        }
    }
}
