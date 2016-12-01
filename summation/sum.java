// Stuart Wyse
// CSE 465 - Term Project

import java.util.ArrayList;
import java.util.Arrays;

public class sum {
	public static String answer; // global String used to check for a valid sum
	public static int count; // used in the sums function to count to number of
								// times we enter that function - before this, a
								// target of 0 would always return true

	/*
	 * Call the test cases in the assignment description. Reset answer to an
	 * empty String after each call, and set count back to 0.
	 */
	public static void main(String args[]) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		// list = [1,2,3,4]
		System.out.println(summation(list, 10));
		answer = "";
		count = 0;

		System.out.println(summation(list, 14));
		answer = "";
		count = 0;

		System.out.println(summation(list, 0));
		answer = "";
		count = 0;

		list.add(0);
		// list = [1,2,3,4,0]

		System.out.println(summation(list, 0));
		answer = "";
		count = 0;

		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(1);
		list2.add(2);
		list2.add(3);
		list2.add(-4);

		// list2 = [1,2,3,-4]
		System.out.println(summation(list2, -3));
		answer = "";
		count = 0;

		ArrayList<Integer> list3 = new ArrayList<Integer>();
		list3.add(1);
		list3.add(2);
		list3.add(8);
		list3.add(2);

		// list3 = [1,2,8,2]
		System.out.println(summation(list3, 5));
		answer = "";
		count = 0;
	}

	/*
	 * Calls the sums function and returns true or false, depending if the
	 * target sum was found.
	 */
	public static boolean summation(ArrayList<Integer> list, int target) {
		if (list.size() == 0 || list == null) {
			return false;
		}
		if (list.contains(target)) {
			return true;
		}
		String answer1 = sums(list, target, new ArrayList<Integer>());
		if (answer1 != null && answer1.length() > 0) {
			return true;
		} else
			return false;
	}

	/*
	 * Recursive function to find all sums in the arraylist. Returns a String
	 * saying if a target sum was found, otherwise the String is null.
	 */
	public static String sums(ArrayList<Integer> list, int target, ArrayList<Integer> partialList) {
		count++;
		int s = 0;
		for (int x : partialList)
			s += x;
		if (count > 1 && s == target) {
			answer = "YES";
			return answer;
		}
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Integer> remaining = new ArrayList<Integer>();
			int n = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				remaining.add(list.get(j));
			}
			ArrayList<Integer> partial = new ArrayList<Integer>(partialList);
			partial.add(n);
			sums(remaining, target, partial);
		}
		return answer;
	}
}
