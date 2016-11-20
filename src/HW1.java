/*
 * Stuart Wyse
 * CSE 465 - HW 1
 * Due 9/8/16
 * 
 * This program is meant to read in a z+- file and interpret it.
 * It handles Integer and String variables, and PRINT statements.
 */

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class HW1 {
	public static HashMap<String, Object> variables = new HashMap();

	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			if(args.length > 0) {
				String fileName = args[0];
				File file = new File(fileName);
				in = new Scanner(file);
			} else {
				System.err.println("ERROR: File name missing.\n");
				System.exit(0);
			}
			
			// this counter is to show line #s of runtime errors
			int count = 0;
			
			while(in.hasNextLine()) {
				count++;
				String line = in.nextLine();
				String[] split = line.split(" ");
				String rightSide, varName;
				
				// to handle variable declarations
				if(split[1].equals("=")) {
					varName = split[0];
					rightSide = split[2];
					int value;
					
					if(rightSide.contains("\"")) {
						variables.put(varName, rightSide);
					}
					
					// check if the right side of argument is a variable or a value
					else if(isDeclared(rightSide)) {
						value = (int) variables.get(rightSide);
						variables.put(varName, value);
					} else {
						value = Integer.parseInt(rightSide);
						variables.put(varName, value);
					}
				}

				// to handle += compound assignment
				else if(split[1].equals("+=")) {
					varName = split[0];
					rightSide = split[2];
					int value;
					// check if the right side of argument is a variable or a value
					if(isDeclared(rightSide)) {
						if(variables.get(rightSide) instanceof String) {
							// if the value is a string, concatenate with the original value
							String strValue = (String) variables.get(rightSide);
							String origValue = (String) variables.get(varName);
							String newValue = origValue + strValue;
							newValue = newValue.replace("\"\"", ""); // removes the quotes from the middle of the new string
							variables.put(varName, newValue);
						}
						else if(variables.get(rightSide) instanceof Integer) {
							// if the value is an integer, add the new value to the original value
							value = (int) variables.get(rightSide);
							value += (int) variables.get(varName);
							variables.put(varName, value);
						}
					} else {
						// if the right side is a number, simply add that to the existing value
						// first, check to make sure variable has been declared already
						if(isDeclared(varName) == false) {
							System.err.println("RUNTIME ERROR: line " + count + "\n");
						} else {
							value = Integer.parseInt(rightSide);
							value += (int) variables.get(varName);
							variables.put(varName, value);
						}
					}
				}
				
				// to handle *= compound assignment
				else if(split[1].equals("*=")) {
					varName = split[0];
					rightSide = split[2];
					int value;
					// ensure variable has already been declared and is in hashmap
					if(isDeclared(varName) == false) {
						System.err.println("RUNTIME ERROR: line " + count + "\n");
					} else {
						// check if the right side of argument is a variable or a value
						if(isDeclared(rightSide)) {
							value = (int) variables.get(rightSide);
						} else value = Integer.parseInt(rightSide);
						value *= (int) variables.get(varName);
						variables.put(varName, value);
					}
				}
				
				// to handle -= compound assignment
				else if(split[1].equals("-=")) {
					varName = split[0];
					rightSide = split[2];
					int value;
					if(isDeclared(varName) == false) {
						System.err.println("RUNTIME ERROR: line " + count + "\n");
					} else {
						// check if the right side of argument is a variable or a value
						if(isDeclared(rightSide)) {
							value = (int) variables.get(rightSide);
						} else value = Integer.parseInt(rightSide);
						int oldValue = (int) variables.get(varName);
						int newValue = oldValue - value;
						variables.put(varName, newValue);
					}
				}

				// print the value of the variable for PRINT statement
				else if(split[0].equals("PRINT")) {
					rightSide = split[1];
					// ensure variable has been declared before trying to print it
					if(isDeclared(rightSide)) {
						if(variables.get(rightSide) instanceof String) {
							String strValue = (String) variables.get(rightSide);
							System.out.println(rightSide + "=" + strValue.replace("\"", ""));
						} else if(variables.get(rightSide) instanceof Integer) {
							int value = (int) variables.get(split[1]);
							System.out.println(rightSide + "=" + value);
						}
					} else System.err.println("RUNTIME ERROR: line " + count + "\n");
				}
				
				// TODO: implement FOR loop handling
				else if(split[0].equals("FOR")) {
					System.err.println("RUNTIME ERROR: line " + count + ". Program does not handle FOR loops.\n");
				}
				
				// any other statement is not valid
				else {
					System.err.println("RUNTIME ERROR: line " + count + "\n");
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// a simple method to check if a variable has already been declared and is in the hashmap
	public static boolean isDeclared(String varName) {
		return variables.get(varName) != null;
	}

}
