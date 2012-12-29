package skip_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import skip_list.TestSkipList.IntegerCompator;


public class TestSkipListUnit{

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void TestAdd() {
		Random r = new Random();
		Set<Integer> set = new HashSet<Integer>();
		SkipList<Integer, Comparator<Integer>> list = new SkipList<Integer, Comparator<Integer>>(16, new IntegerCompator());
		for(int i = 0;i < 10000;i++) {
			int v = r.nextInt();
			if(!set.contains(v)) {
				list.add(v);
				set.add(v);
			}
		}
		Assert.assertTrue(list.check());
		for(Integer v : set) {
			Assert.assertTrue(list.contains(v));
		}
	}
	@Test
	public void testDelete() {
		Random r = new Random();
		List<Integer> set = new ArrayList<Integer>();
		List<Integer> il = new ArrayList<Integer>();
		SkipList<Integer, Comparator<Integer>> list = new SkipList<Integer, Comparator<Integer>>(16, new IntegerCompator());
		for(int i = 0;i < 10000;i++) {
			int v = r.nextInt();
			if(!set.contains(v)) {
				list.add(v);
				set.add(v);
				if(r.nextInt() % 3 == 0) {
					il.add(v);
				}
			}
		}
		for(Integer v : il) {
			list.delete(v);
		}
		for(Integer v : il) {
			Assert.assertNull(list.get(v));
		}
	}
	@Test
	public void testGet() {
		Random r = new Random();
		List<Integer> set = new ArrayList<Integer>();
		SkipList<Integer, Comparator<Integer>> list = new SkipList<Integer, Comparator<Integer>>(16, new IntegerCompator());
		for(int i = 0;i < 10000;i++) {
			int v = r.nextInt();
			if(!set.contains(v)) {
				list.add(v);
				set.add(v);
			}
		}
		Collections.sort(set);
		for(int i = 0;i < set.size();i++) {
			Assert.assertEquals(set.get(i), list.get(i));
		}
	}
	
}
