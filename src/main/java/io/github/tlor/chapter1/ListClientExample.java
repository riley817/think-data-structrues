package io.github.tlor.chapter1;

import java.util.ArrayList;
import java.util.List;

public class ListClientExample {

    private List list;

    public ListClientExample() {
        this.list = new ArrayList();
    }

    public List getList() {
        return list;
    }

    public static void main(String[] args) {
        ListClientExample listClientExample = new ListClientExample();
        List list = listClientExample.getList();
        System.out.println(list);
    }
}
