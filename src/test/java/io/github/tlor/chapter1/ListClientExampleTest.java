package io.github.tlor.chapter1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ListClientExampleTest {

    @Test
    public void testListClientExample() {
        ListClientExample listClientExample = new ListClientExample();
        List list = listClientExample.getList();
        assertThat(list, instanceOf(ArrayList.class));
    }
}
