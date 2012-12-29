package skip_list;

import java.util.Random;

public class TestSkipList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random random = new Random();
		SkipList<Integer, Comparator<Integer>> list = new SkipList<Integer, Comparator<Integer>>(16, new IntegerCompator());
		for(int i = 0;i < 100;i++) {
			list.add(random.nextInt());
			System.out.println(list);
		}
//		list.delete(0);
//		list.delete(10);
//		list.delete(20);
//		list.delete(30);
//		list.delete(40);
//		list.delete(50);
//		list.delete(60);
//		list.delete(70);
//		list.delete(80);
//		list.delete(90);
//		list.delete(101);
		
		list.add(10);
		list.add(-5);
		list.add(-8);
		list.add(20);
		
		System.out.println(list);
		
		System.out.println(list.get(1));
		System.out.println(list.get(10));
		System.out.println(list.get(13));
	}
	
	public static class IntegerCompator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			if(o1 == null && o2 == null) {
				return 0;
			}
			if(o1 == null) {
				return 1;
			}
			if(o2 == null) {
				return -1;
			}
			if(o1 > o2) {
				return 1;
			}else if(o1.equals(o2)) {
				return 0;
			} else {
				return -1;
			}
		}
		
	}

}
