/*
 * Stuart Wyse
 * CSE 465 - HW 1 & Term Project
 * 
 * This program is meant to read in a z+- file and interpret it.
 */

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class ZPM {
	public static HashMap<String, String> variables = new HashMap<String, String>();
	public static int count = 0;

	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			if (args.length > 0) {
				String fileName = args[0];
				File file = new File(fileName);
				in = new Scanner(file);
			} else {
				System.err.println("ERROR: File name missing.\n");
				System.exit(0);
			}

			// this counter is to show line #s of runtime errors
			count = 0;

			while (in.hasNextLine()) {
				count++;
				String line = in.nextLine();
				parseStatememt(line);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Tokenize a single line of source code, decide what kind of statement it
	// is, and call the appropriate method
	public static void parseStatememt(String line) {
		String[] tokens = line.split("\\s+");
		int numTokens = tokens.length;

		if (numTokens > 4 && tokens[2].contains("\"")) {
			String[] oldTokens = tokens;
			tokens = new String[4];
			tokens[0] = oldTokens[0];
			tokens[1] = oldTokens[1];
			tokens[2] = line.substring(line.indexOf("\""), line.lastIndexOf("\"") + 1);
			tokens[3] = oldTokens[oldTokens.length - 1];

			numTokens = tokens.length;
		}

		if (numTokens == 3) {
			analyzePrint(tokens);
		} else if (numTokens == 4) {
			analyzeAssignment(tokens);
		} else {
			// If we don't have a blank line, it is a FOR loop
			if (line.length() != 0) {
				analyzeFor(line);
			}
		}
	}

	// Tokenize a PRINT statement and print appropriate info to the console
	public static void analyzePrint(String[] tokens) {
		// Print statement has 3 tokens
		String toPrint = tokens[1];
		String prefix = "";

		if (findType(toPrint) == 2) {
			if (!variables.containsKey(toPrint)) {
				runtimeError();
			} else {
				prefix = toPrint + " =";
				toPrint = variables.get(toPrint);
			}
		}

		if (findType(toPrint) == 0) {
			toPrint = removeQuotes(toPrint);
		}

		System.out.println(prefix + " " + toPrint);
	}

	// Tokenize an assignment statement and create or update the appropriate
	// variable
	public static void analyzeAssignment(String[] tokens) {
		String varName = tokens[0];
		String statement = tokens[1];
		String newValue = tokens[2];

		if (findType(newValue) == 2) {
			// Throw an error if variable doesn't exist
			if (variables.containsKey(newValue)) {
				newValue = variables.get(newValue);
			} else {
				runtimeError();
			}
		}

		int newType = findType(newValue);

		// Make sure types work together if variable is already declared
		if (variables.containsKey(varName)) {
			int oldType = findType(variables.get(varName));
			if (!statement.equals("=") && newType != oldType) {
				runtimeError();
			}
		}

		// Check to see what kind of statement it is and perform that operation
		switch (statement) {
		case "+=":
			if (newType == 0) {
				String string1 = removeQuotes(variables.get(varName));
				String string2 = removeQuotes(newValue);
				// Put quotes back so we know it's a string
				newValue = "\"" + string1 + string2 + "\"";
			} else {
				newValue = (Integer.parseInt(variables.get(varName)) + Integer.parseInt(newValue)) + "";
			}
			break;
		case "-=":
			// Can't do this w/ strings
			if (newType == 0) {
				runtimeError();
			} else {
				newValue = (Integer.parseInt(variables.get(varName)) - Integer.parseInt(newValue)) + "";
			}
			break;
		case "*=":
			// Can't do this w/ strings
			if (newType == 0) {
				runtimeError();
			} else {
				newValue = (Integer.parseInt(variables.get(varName)) * Integer.parseInt(newValue)) + "";
			}
			break;
		default:
		}
		variables.put(varName, newValue);
	}

	// Tokenize a for loop and complete the appropriate action
	public static void analyzeFor(String line) {
		line = line.trim();
		line = line.substring(4, line.length() - 7);

		line = line.trim();
		int spaceAfterNum = line.indexOf(" ");
		int loopCond = Integer.parseInt(line.substring(0, spaceAfterNum));
		line = line.substring(spaceAfterNum);

		for (int i = 0; i < loopCond; i++) {
			executeStatementList(line);
		}
	}
	
	// Determine the "type" of a string token
	// Meaning of int returned: 0 = string, 1 = int, or 2 = variable name
	public static int findType(String toDetermine) {
		// If first character is a ", it's a String
		if (toDetermine.charAt(0) == '\"') {
			// string
			return 0;
		} 
		try {
			// int
			Integer.parseInt(toDetermine);
			return 1;
		} catch (Exception e) {
			// variable name
			return 2;
		}
	}

	// Split a statement list into the different statements and execute each statement
	public static void executeStatementList(String line) {
		ArrayList<String> stmts = new ArrayList<String>();

		while (line.length() > 0) {
			line = line.trim();
			if (line.startsWith("FOR")) {
				int endfor = line.lastIndexOf("ENDFOR");
				stmts.add(line.substring(0, endfor + "ENDFOR".length()));
				// everything after the ENDFOR
				line = line.substring(endfor + "ENDFOR".length());
			} else {
				int semicolon = line.indexOf(";");
				stmts.add(line.substring(0, semicolon + 1));
				line = line.substring(semicolon + 1);
			}
		}
		
		for (String s : stmts) {
			parseStatememt(s);
		}
	}

	// Remove the the quotes from a string
	public static String removeQuotes(String str) {
		return str.substring(1, str.length() - 1);
	}

	// Print runtime error and exits the program
	public static void runtimeError() {
		System.out.println("RUNTIME ERROR: line " + count);
		System.exit(0);
	}

}
