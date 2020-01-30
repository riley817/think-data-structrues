package io.github.tlor.chapter3;

import java.util.ArrayList;

import io.github.tlor.chapter2.MyArrayListTest;
import org.junit.Before;

/**
 * @author downey
 */
public class MyLinkedListTest extends MyArrayListTest {

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);

		mylist = new MyLinkedList<Integer>();
		mylist.addAll(list);
	}
}
