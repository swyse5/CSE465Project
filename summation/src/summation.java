// Stuart Wyse
// CSE 465 - Term Project

import java.util.ArrayList;
import java.util.Arrays;

public class summation {
	public static String answer; // global String used to check for an valid sum

	public static void main(String args[]) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		// list = [1,2,3,4]
		System.out.println(summation(list, 10));
		answer = "";
		
		System.out.println(summation(list, 14));
		answer = "";

		System.out.println(summation(list, 0));
		answer = "";

		
		list.add(0);
		// list = [1,2,3,4,0]
		System.out.println(summation(list, 0));
		answer = "";

		list.add(-4);
		list.remove(4);
		list.remove(0);
		// list = [1,2,3,-4]
		System.out.println(summation(list, -3));
		answer = "";
		System.out.println(list);
		list.add(2);
		list.add(8);
		list.remove(-4);
		list.remove(3);
		// list = [1,2,2,8]
		System.out.println(summation(list, 5));
		answer = "";

	}
	
	public static boolean summation(ArrayList<Integer> list, int target) {
		if(list.size() == 0 || list == null) {
			return false;
		}
		if(list.contains(target)) {
			System.out.println("TEST");
			return true;
		}
 		String answer1 = sums(list, target, new ArrayList<Integer>());
 		//System.out.println(answer1);
		if(answer1 != null && answer1.length() > 0) {
			return true;
		} else return false;
	}
	
    public static String sums(ArrayList<Integer> list, int target, ArrayList<Integer> partialList) {
        int s = 0;
        for (int x: partialList) s += x;
        if (s == target) {
        	answer = "YES";
        }
        for(int i = 0; i < list.size(); i++) {
              ArrayList<Integer> remaining = new ArrayList<Integer>();
              int n = list.get(i);
              for(int j = i + 1; j < list.size(); j++) {
            	  remaining.add(list.get(j));
              }
              ArrayList<Integer> partial = new ArrayList<Integer>(partialList);
              partial.add(n);
              sums(remaining,target,partial);
        }
        return answer;
    }
}
